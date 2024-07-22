package com.bioxx.tfc.Items.Tools;

import static com.bioxx.tfc.Core.Player.SkillStats.SkillRank.Novice;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.WorldGen.Generators.WorldGenOre;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class ItemProPick extends ItemTerra {

    private final Map<String, ProspectResult> results = new HashMap<>();
    private Random random;

    static final double FOUR_THIRDS_PI = (4.0D / 3) * Math.PI;

    public ItemProPick() {
        super();
        maxStackSize = 1;
        setCreativeTab(TFCTabs.TFC_TOOLS);
        this.setWeight(EnumWeight.LIGHT);
        this.setSize(EnumSize.SMALL);
    }

    @Override
    public void registerIcons(IIconRegister registerer) {
        this.itemIcon = registerer.registerIcon(
            Reference.MOD_ID + ":"
                + "tools/"
                + this.getUnlocalizedName()
                    .replace("item.", ""));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (pass == 1 && nbt != null && nbt.hasKey("broken")) return TFC_Textures.brokenItem;
        else return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
    }

    public void switchMode(EntityPlayer player) {
        ItemStack itemStack = player.getCurrentEquippedItem();
        int mode = getMode(itemStack);
        int maxMode;
        switch (this.getUnlocalizedName()) {
            case "item.Bronze ProPick":
            case "item.Black Bronze ProPick":
            case "item.Bismuth Bronze ProPick":
                maxMode = 2;
                break;
            case "item.Wrought Iron ProPick":
                maxMode = 3;
                break;
            case "item.Steel ProPick":
                maxMode = 4;
                break;
            case "item.Black Steel ProPick":
                maxMode = 5;
                break;
            case "item.Blue Steel ProPick":
            case "item.Red Steel ProPick":
                maxMode = 6;
                break;
            default:
                maxMode = 1;
                break; // Copper and unknown
        }
        mode++;
        if (mode > maxMode) {
            mode = 1;
        }
        NBTTagCompound nbt = itemStack.getTagCompound();
        nbt.setInteger("prospect_mode", mode);
        if (player.getEntityWorld().isRemote) { // TODO: Use an icon instead of a chat message.
            TFC_Core.sendInfoMessage(
                player,
                new ChatComponentText(
                    "Prospectors pick radius now: " + getProspectingRadius(player.getCurrentEquippedItem())));
        }
    }

    private int getMode(ItemStack itemStack) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            itemStack.setTagCompound(nbt);
        }
        if (!nbt.hasKey("prospect_mode")) {
            nbt.setInteger("prospect_mode", 3);
        }
        return nbt.getInteger("prospect_mode");
    }

    private int getProspectingRadius(ItemStack itemStack) {
        int mode = this.getMode(itemStack);
        switch (mode) {
            case 2:
                return 16;
            case 3:
                return 20;
            case 4:
                return 24;
            case 5:
                return 30;
            case 6:
                return 40;
            default:
                return 10;
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        if (!world.isRemote) {
            // Negated the old condition and exiting the method here instead.
            if (block == TFCBlocks.toolRack) return true;

            // Getting the metadata only when we actually need it.
            int meta = world.getBlockMetadata(x, y, z);

            SkillRank rank = TFC_Core.getSkillStats(player)
                .getSkillRank(Global.SKILL_PROSPECTING);

            // If an ore block is targeted directly, it'll tell you what it is.
            if ((block == TFCBlocks.ore || block == TFCBlocks.ore2 || block == TFCBlocks.ore3)
                && world.getTileEntity(x, y, z) instanceof TEOre) {
                TEOre te = (TEOre) world.getTileEntity(x, y, z);
                if (block == TFCBlocks.ore && rank == SkillRank.Master) meta = ((BlockOre) block).getOreGrade(te, meta);
                if (block == TFCBlocks.ore2) meta = meta + Global.ORE_METAL.length;
                if (block == TFCBlocks.ore3) meta = meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length;
                tellResult(player, new ItemStack(TFCItems.oreChunk, 1, meta));
                return true;
            } else if (!TFC_Core.isGround(block)) { // Exclude ground blocks to help with performance
                for (OreSpawnData osd : WorldGenOre.oreList.values()) { // TODO: MAYBE
                    if (osd != null && block == osd.block) {
                        tellResult(player, new ItemStack(block));
                        return true;
                    }
                }
            }

            random = new Random((long) x * z + y);
            int chance = 70 + rank.ordinal() * 10;

            results.clear();
            if (random.nextInt(100) > chance) {
                tellNothingFound(player);
                return true;
            }

            results.clear();

            // Check all blocks in a radius centered on the targeted block.
            int radius = getProspectingRadius(itemStack);
            int radiusSquared = radius * radius;
            // TerraFirmaCraft.LOG.info("Prospecting radius: {}", radius);
            for (int i = -radius; i <= radius; i++) {
                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        if (i * i + j * j + k * k > radiusSquared) {
                            continue;
                        }
                        int blockX = x + i;
                        int blockY = y + j;
                        int blockZ = z + k;

                        block = world.getBlock(blockX, blockY, blockZ);
                        meta = world.getBlockMetadata(blockX, blockY, blockZ);
                        ItemStack ore = null;
                        if (block == TFCBlocks.ore && world.getTileEntity(blockX, blockY, blockZ) instanceof TEOre) {
                            TEOre te = (TEOre) world.getTileEntity(blockX, blockY, blockZ);
                            if (rank == SkillRank.Master)
                                ore = new ItemStack(TFCItems.oreChunk, 1, ((BlockOre) block).getOreGrade(te, meta));
                            else ore = new ItemStack(TFCItems.oreChunk, 1, meta);
                        } else if (block == TFCBlocks.ore2)
                            ore = new ItemStack(TFCItems.oreChunk, 1, meta + Global.ORE_METAL.length);
                        else if (block == TFCBlocks.ore3) {
                            ore = new ItemStack(
                                TFCItems.oreChunk,
                                1,
                                meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length);
                        } else if (!TFC_Core.isGround(block)) { // Exclude ground blocks to help with performance
                            for (OreSpawnData osd : WorldGenOre.oreList.values()) {
                                if (osd != null && block == osd.block) {
                                    ore = new ItemStack(block);
                                    break;
                                }
                            }
                        } else continue;

                        if (ore != null) {
                            String oreName = ore.getDisplayName();

                            if (results.containsKey(oreName)) results.get(oreName).count++;
                            else results.put(oreName, new ProspectResult(ore, 1));
                        }
                    }
                }
            }

            // Tell the player what was found.
            if (results.isEmpty()) {
                tellNothingFound(player);
            } else {
                tellResult(player, radius);
            }

            results.clear();
            random = null;

            // Damage the item on prospecting use.
            itemStack.damageItem(1, player);
            if (itemStack.getItemDamage() >= itemStack.getMaxDamage()) player.destroyCurrentEquippedItem();
        }

        return true;
    }

    /*
     * Tells the player nothing was found.
     */
    private void tellNothingFound(EntityPlayer player) {
        TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.ProPick.FoundNothing"));
    }

    /*
     * Tells the player what block of ore they found, when directly targeting an ore block.
     */
    private void tellResult(EntityPlayer player, ItemStack ore) {
        String oreName = ore.getUnlocalizedName() + ".name";
        TFC_Core.sendInfoMessage(
            player,
            new ChatComponentTranslation("gui.ProPick.Found").appendText(" ")
                .appendSibling(new ChatComponentTranslation(oreName)));

    }

    /*
     * Tells the player what ore has been found, randomly picked off the HashMap.
     */
    private void tellResult(EntityPlayer player, int radius) {
        TFC_Core.getSkillStats(player)
            .increaseSkill(Global.SKILL_PROSPECTING, 1);
        ProspectResult[] resultsArray = results.values()
            .toArray(new ProspectResult[0]);

        SkillRank rank = TFC_Core.getSkillStats(player)
            .getSkillRank(Global.SKILL_PROSPECTING);
        if (rank.ordinal() == Novice.ordinal() || resultsArray.length == 1) {
            int index = random.nextInt(results.size());
            tellSingleResult(resultsArray[index], player, radius);
        } else if (rank.ordinal() == SkillRank.Adept.ordinal() || resultsArray.length == 2) {
            int index1 = random.nextInt(results.size());
            tellSingleResult(resultsArray[index1], player, radius);

            int index2 = random.nextInt(results.size());
            if (index2 == index1) index2 = (index2 + 1) % resultsArray.length;
            tellSingleResult(resultsArray[index2], player, radius);
        } else {
            for (ProspectResult result : resultsArray) {
                tellSingleResult(result, player, radius);
            }
        }
    }

    private void tellSingleResult(ProspectResult result, EntityPlayer player, int radius) {
        String oreName = result.itemStack.getUnlocalizedName() + ".name";

        String quantityMsg = getQuantityString(radius, result);

        SkillRank rank = TFC_Core.getSkillStats(player)
            .getSkillRank(Global.SKILL_PROSPECTING);

        if (rank == Novice) {
            TFC_Core.sendInfoMessage(
                player,
                new ChatComponentTranslation(quantityMsg).appendText(" ")
                    .appendSibling(new ChatComponentTranslation(oreName)));
        } else {
            int approximateCount = getApproximateCount(result, rank);
            TFC_Core.sendInfoMessage(
                player,
                new ChatComponentTranslation(quantityMsg).appendText(" (<" + approximateCount + ") ")
                    .appendSibling(new ChatComponentTranslation(oreName)));
        }
    }

    private static int getApproximateCount(ProspectResult result, SkillRank rank) {
        float roundingFactor;
        switch (rank) {
            case Adept:
                roundingFactor = 100.0f;
                break;
            case Expert:
                roundingFactor = 50.0f;
                break;
            case Master:
                roundingFactor = 20.0f;
                break;
            default:
                roundingFactor = 250.0f;
                break;
        }

        return (int) (MathHelper.ceiling_float_int(result.count / roundingFactor) * roundingFactor);
    }

    /*
     * Gets a string representation of the quantity of ore found.
     */
    private static String getQuantityString(int radius, ProspectResult result) {
        String quantityMsg;

        double proportion = result.count / (FOUR_THIRDS_PI * radius * radius * radius);
        if (proportion < 0.001) {
            quantityMsg = "gui.ProPick.FoundTraces";
        } else if (proportion < 0.005) {
            quantityMsg = "gui.ProPick.FoundSmall";
        } else if (proportion < 0.015) {
            quantityMsg = "gui.ProPick.FoundMedium";
        } else if (proportion < 0.10) {
            quantityMsg = "gui.ProPick.FoundLarge";
        } else {
            quantityMsg = "gui.ProPick.FoundVeryLarge";
        }
        return quantityMsg;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    private static class ProspectResult {

        public ItemStack itemStack;
        public int count;

        public ProspectResult(ItemStack itemStack, int count) {
            this.itemStack = itemStack;
            this.count = count;
        }
    }

    @Override
    public EnumItemReach getReach(ItemStack is) {
        return EnumItemReach.SHORT;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(stack)));
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        float digSpeed = super.getDigSpeed(stack, block, meta);

        if (ForgeHooks.isToolEffective(stack, block, meta)) {
            return digSpeed + (digSpeed * AnvilManager.getDurabilityBuff(stack));
        }
        return digSpeed;
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
        ItemTerra.addSizeInformation(is, arraylist);
        ItemTerraTool.addSmithingBonusInformation(is, arraylist);
    }
}

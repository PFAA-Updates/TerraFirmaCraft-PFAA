package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;

import org.lwjgl.input.Keyboard;

import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.KeyPressPacket;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyBindingHandler {

    // public static KeyBinding Key_Calendar = new KeyBinding("key.Calendar", Keyboard.KEY_N/*49*/, Reference.ModName);
    public static KeyBinding keyToolMode = new KeyBinding("key.ToolMode", Keyboard.KEY_M/* 50 */, Reference.MOD_NAME);
    public static KeyBinding keyLockTool = new KeyBinding("key.LockTool", Keyboard.KEY_L/* 38 */, Reference.MOD_NAME);

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerInfo playerInfo = PlayerManagerTFC.getInstance()
            .getClientPlayer();
        EntityClientPlayerMP player = FMLClientHandler.instance()
            .getClient().thePlayer;

        if (FMLClientHandler.instance()
            .getClient().inGameHasFocus
            && FMLClientHandler.instance()
                .getClient().thePlayer.getCurrentEquippedItem() != null
            && FMLClientHandler.instance()
                .getClient().currentScreen == null) {
            if (keyToolMode.isPressed()) {
                Item item = player.getCurrentEquippedItem()
                    .getItem();
                if (item instanceof ItemChisel) {
                    playerInfo.switchChiselMode();

                    // Sync chisel mode with server
                    AbstractPacket pkt = new KeyPressPacket(playerInfo.chiselMode);
                    TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
                } else if (item instanceof ItemCustomHoe) {
                    playerInfo.switchHoeMode(player);
                } else if (item instanceof ItemProPick) {
                    ((ItemProPick) item).switchMode(player);

                    // Sync prospectors pick mode with server
                    AbstractPacket pkt = new KeyPressPacket(playerInfo.chiselMode);
                    TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
                }
            } else if (keyLockTool.isPressed() && playerInfo != null) {
                if (playerInfo.lockX == -9999999) {
                    playerInfo.lockX = BlockDetailed.lockX;
                    playerInfo.lockY = BlockDetailed.lockY;
                    playerInfo.lockZ = BlockDetailed.lockZ;
                } else {
                    playerInfo.lockX = -9999999;
                    playerInfo.lockY = -9999999;
                    playerInfo.lockZ = -9999999;
                }
            }
        }
    }
}

package com.bioxx.tfc.api.Constant;

import java.util.Arrays;

import com.bioxx.tfc.api.Metal;
import com.google.common.collect.ObjectArrays;

@SuppressWarnings("PMD")
public class Global {

    /* FruitTree Meta Names, also used for fruit items and FloraManager */
    public static final String[] FRUIT_META_NAMES = new String[] { "Red Apple", "Banana", "Orange", "Green Apple",
        "Lemon", "Olive", "Cherry", "Peach", "Plum" };

    /*
     * Flower Meta Names
     * The first 10 flowers are from vanilla
     */
    public static final String[] FLOWER_META_NAMES = new String[] { "flower_rose", "flower_blue_orchid",
        "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white",
        "flower_tulip_pink", "flower_oxeye_daisy", "flower_dandelion", "flower_nasturtium", "flower_meads_milkweed",
        "flower_tropical_milkweed", "flower_butterfly_milkweed", "flower_calendula" };

    /*
     * Fungi Meta Names
     * The first 2 are vanilla mushrooms
     */
    public static final String[] FUNGI_META_NAMES = new String[] { "mushroom_brown", "mushroom_red" };

    /* Powder */
    public static final String[] POWDER = { "Flux", "Kaolinite Powder", "Graphite Powder", "Sulfur Powder",
        "Saltpeter Powder", "Hematite Powder", "Lapis Lazuli Powder", "Limonite Powder", "Malachite Powder", "Salt" };

    /* Dyes, used for carpets and small vessels */
    /* Colors MUST be in the same order as in EntitySheep.fleeceColorTable! */
    public static final String[] DYE_NAMES = { "dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow",
        "dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen",
        "dyeRed", "dyeBlack" };

    /* Stone Types */
    public static final String[] STONE_IGNEOUS_INTRUSIVE = { "Granite", "Diorite", "Gabbro" };
    public static final String[] STONE_SEDIMENTARY = { "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate",
        "Dolomite", "Chert", "Chalk" };
    public static final String[] STONE_IGNEOUS_EXTRUSIVE = { "Rhyolite", "Basalt", "Andesite", "Dacite" };
    public static final String[] STONE_METAMORPHIC = { "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble" };

    // Used for loose rocks and other places where the stone list is combined
    public static final int STONE_IGNEOUS_INTRUSIVE_START = 0;
    public static final int STONE_SEDIMENTARY_START = STONE_IGNEOUS_INTRUSIVE_START + STONE_IGNEOUS_INTRUSIVE.length;
    public static final int STONE_IGNEOUS_EXTRUSIVE_START = STONE_SEDIMENTARY_START + STONE_SEDIMENTARY.length;
    public static final int STONE_METAMORPHIC_START = STONE_IGNEOUS_EXTRUSIVE_START + STONE_IGNEOUS_EXTRUSIVE.length;
    public static final String[] STONE_ALL = ObjectArrays.concat(
        ObjectArrays.concat(STONE_IGNEOUS_INTRUSIVE, STONE_SEDIMENTARY, String.class),
        ObjectArrays.concat(STONE_IGNEOUS_EXTRUSIVE, STONE_METAMORPHIC, String.class),
        String.class);

    // Stones that can be turned into flux
    public static final int[] STONE_FLUX_INDEX = {
        Arrays.asList(STONE_ALL)
            .indexOf("Limestone"),
        Arrays.asList(STONE_ALL)
            .indexOf("Dolomite"),
        Arrays.asList(STONE_ALL)
            .indexOf("Chalk"),
        Arrays.asList(STONE_ALL)
            .indexOf("Marble")
    };

    /* Ore Types */
    public static final String[] ORE_METAL_NAMES = { "Native Copper", "Native Gold", "Native Platinum", "Hematite",
        "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", "Malachite", "Magnetite", "Limonite",
        "Sphalerite", "Tetrahedrite", "Bituminous Coal", "Lignite" };
    public static final int BITUMINOUS_COAL = 14;
    public static final int LIGNITE = 15;

    public static final String[] ORE_MINERAL_NAMES = { "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite",
        "Kimberlite", "Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite",
        "Saltpeter", "Serpentine", "Sylvite" };
    public static final int KAOLINITE = 0;
    public static final int GYPSUM = 1;
    public static final int SATINSPAR = 2;
    public static final int SELENITE = 3;
    public static final int GRAPHITE = 4;
    public static final int KIMBERLITE = 5;
    public static final int PETRIFIED_WOOD = 6;
    public static final int SULFUR = 7;
    public static final int JET = 8;
    public static final int MICROCLINE = 9;
    public static final int PITCHEBLENDE = 10;
    public static final int CINNABAR = 11;
    public static final int CRYOLITE = 12;
    public static final int SALTPETER = 13;
    public static final int SERPENTINE = 14;
    public static final int SYLVITE = 15;
    public static final String[] ORE_MINERAL2_NAMES = { "Borax", "Olivine", "Lapis Lazuli" };

    public static final String[] WOOD_NAMES = { "Oak", "Aspen", "Birch", "Chestnut", "Douglas Fir", "Hickory", "Maple",
        "Ash", "Pine", "Sequoia", "Spruce", "Sycamore", "White Cedar", "White Elm", "Willow", "Kapok", "Acacia" };

    public static final String SKILL_GENSMITH = "skill.gensmith";
    public static final String SKILL_TOOLSMITH = "skill.toolsmith";
    public static final String SKILL_WEAPONSMITH = "skill.weaponsmith";
    public static final String SKILL_ARMORSMITH = "skill.armorsmith";
    public static final String SKILL_AGRICULTURE = "skill.agriculture";
    public static final String SKILL_COOKING = "skill.cooking";
    public static final String SKILL_PROSPECTING = "skill.prospecting";
    public static final String SKILL_BUTCHERING = "skill.butchering";

    public static Metal BISMUTH;
    public static Metal BISMUTH_BRONZE;
    public static Metal BLACK_BRONZE;
    public static Metal BLACK_STEEL;
    public static Metal BLUE_STEEL;
    public static Metal BRASS;
    public static Metal BRONZE;
    public static Metal COPPER;
    public static Metal GOLD;
    public static Metal WROUGHT_IRON;
    public static Metal LEAD;
    public static Metal NICKEL;
    public static Metal PIG_IRON;
    public static Metal PLATINUM;
    public static Metal RED_STEEL;
    public static Metal ROSE_GOLD;
    public static Metal SILVER;
    public static Metal STEEL;
    public static Metal STERLING_SILVER;
    public static Metal TIN;
    public static Metal ZINC;
    public static Metal WEAK_STEEL;
    public static Metal HIGH_CARBON_BLACK_STEEL;
    public static Metal WEAK_RED_STEEL;
    public static Metal HIGH_CARBON_RED_STEEL;
    public static Metal WEAK_BLUE_STEEL;
    public static Metal HIGH_CARBON_BLUE_STEEL;
    public static Metal UNKNOWN;

    /**
     * Switch to TFCOptions.foodDecayRate
     */
    // TODO: make sure this change does not break mod compatibility
    //@Deprecated
    //public static double foodDecayRate = TFCOptions.foodDecayRate;

    public static final float FOOD_MAX_WEIGHT = 160;
    public static final float FOOD_MIN_DROP_WEIGHT = 0.1f;

    public static final int SEA_LEVEL = 144;

    public static final int HOT_LIQUID_TEMP = 385; // Water's boiling point is 373.2 K

    public static boolean isCoal(int meta) {
        return meta == BITUMINOUS_COAL || meta == LIGNITE;
    }
}

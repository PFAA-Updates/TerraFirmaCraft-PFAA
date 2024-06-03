package com.bioxx.tfc.Core.Config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.bioxx.tfc.Reference;

import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class TFC_ConfigGUI extends GuiConfig {

    public TFC_ConfigGUI(GuiScreen parent) {
        super(parent, getConfigElements(), Reference.MOD_ID, false, false, Reference.MOD_NAME);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> root = new ArrayList<>();
        root.add(
            new DummyCategoryElement(
                "TFCOptions.cfg",
                "config.gui.TFCConfig",
                getAllFrom(TFC_ConfigFiles.getGeneralConfig())));
        root.add(
            new DummyCategoryElement(
                "TFCCrafting.cfg",
                "config.gui.TFCCrafting",
                getAllFrom(TFC_ConfigFiles.getCraftingConfig())));
        root.add(
            new DummyCategoryElement("TFCOre.cfg", "config.gui.TFCOre", getAllFrom(TFC_ConfigFiles.getOresConfig())));
        return root;
    }

    private static List<IConfigElement> getAllFrom(Configuration generalConfig) {
        List<IConfigElement> root = new ArrayList<>();
        for (String catName : generalConfig.getCategoryNames()) {
            // getCategoryNames includes sub categories, which we don't want.
            if (catName.contains(Configuration.CATEGORY_SPLITTER)) { continue; }
            root.add(new ConfigElement(generalConfig.getCategory(catName)));
        }
        return root;
    }
}

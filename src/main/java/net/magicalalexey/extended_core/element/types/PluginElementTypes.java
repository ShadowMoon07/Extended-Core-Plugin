package net.magicalalexey.extended_core.element.types;

import net.magicalalexey.extended_core.ui.modgui.ArmorTrimGUI;
import net.magicalalexey.extended_core.ui.modgui.TrimMaterialGUI;
import net.magicalalexey.extended_core.ui.modgui.ConfigGUI;
import net.magicalalexey.extended_core.ui.modgui.BlockstatesGUI;
import net.mcreator.element.ModElementType;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CONFIG;
    public static ModElementType<?> ARMORTRIM;
    public static ModElementType<?> TRIMMATERIAL;
    public static ModElementType<?> BLOCKSTATES;

    public static void load(){
        CONFIG = register(
                new ModElementType<>("config", (Character) null, ConfigGUI::new, Config.class)
        );
        ARMORTRIM = register(
                new ModElementType<>("armortrim", (Character) null, ArmorTrimGUI::new, ArmorTrim.class)
        );

        TRIMMATERIAL = register(
                new ModElementType<>("trimmaterial", (Character) null, TrimMaterialGUI::new, TrimMaterial.class)
        );
        BLOCKSTATES = register(
                new ModElementType<>("blockstates", (Character) null, BlockstatesGUI::new, Blockstates.class)
        );
    }
}

package net.magicalalexey.extended_core.element.types;

import net.magicalalexey.extended_core.ui.modgui.*;
import net.mcreator.element.ModElementType;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CONFIG;
    public static ModElementType<?> ARMORTRIM;
    public static ModElementType<?> TRIMMATERIAL;
    public static ModElementType<?> BLOCKSTATES;
    public static ModElementType<?> PARTICLEMODEL;
    public static ModElementType<?> MIXIN;
    public static ModElementType<?> DYEABLE_ITEM;
    public static ModElementType<?> DYEABLE_ARMOR;

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
        PARTICLEMODEL = register(
                new ModElementType<>("particlemodel", (Character) null, ParticleModelGUI::new, ParticleModel.class)
        );
        MIXIN = register(
                new ModElementType<>("mixin", (Character) null, MixinGUI::new, Mixin.class)
        );
        DYEABLE_ITEM = register(
                new ModElementType<>("dyeable_item", (Character) null, DyeableItemGUI::new, DyeableItem.class)
        );
        DYEABLE_ARMOR = register(
                new ModElementType<>("dyeable_armor", (Character) null, DyeableArmorGUI::new, DyeableArmor.class)
        );
    }
}

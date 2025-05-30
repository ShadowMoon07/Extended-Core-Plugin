package net.magicalalexey.extended_core.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

import java.awt.*;

public class TrimMaterial extends GeneratableElement {

    public String name;
    public MItemBlock item;
    public Color color1;
    public Color color2;
    public Color color3;
    public Color color4;
    public Color color5;
    public Color color6;
    public Color color7;
    public Color color8;
    public TrimMaterial(ModElement element) {
        super(element);
    }

    public String getHexColor() {
        return String.format("#%02X%02X%02X", color4.getRed(), color4.getGreen(), color4.getBlue());
    }

}

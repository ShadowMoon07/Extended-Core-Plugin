package net.magicalalexey.extended_core.ui.modgui;

import net.magicalalexey.extended_core.element.types.TrimMaterial;
import net.mcreator.generator.GeneratorUtils;
import net.mcreator.io.FileIO;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JColor;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class TrimMaterialGUI extends ModElementGUI<TrimMaterial> {
    private final ValidationGroup page1group = new ValidationGroup();
    private final VTextField name;
    private final MCItemHolder item;
    private final JColor color1;
    private final JColor color2;
    private final JColor color3;
    private final JColor color4;
    private final JColor color5;
    private final JColor color6;
    private final JColor color7;
    private final JColor color8;

    public TrimMaterialGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.name = new VTextField(17);
        this.item = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.color1 = new JColor(this.mcreator, false, false);
        this.color2 = new JColor(this.mcreator, false, false);
        this.color3 = new JColor(this.mcreator, false, false);
        this.color4 = new JColor(this.mcreator, false, false);
        this.color5 = new JColor(this.mcreator, false, false);
        this.color6 = new JColor(this.mcreator, false, false);
        this.color7 = new JColor(this.mcreator, false, false);
        this.color8 = new JColor(this.mcreator, false, false);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 2, 0));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/material_name"), L10N.label("elementgui.trimmaterial.name", new Object[0])));
        mainPanel.add(name);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/material"), L10N.label("elementgui.trimmaterial.item", new Object[0])));
        mainPanel.add(item);

        JPanel palettePanel = new JPanel(new GridLayout(1, 8, 0, 0));
        palettePanel.setOpaque(false);
        color1.setPreferredSize(new Dimension(60, 30));
        color2.setPreferredSize(new Dimension(60, 30));
        color3.setPreferredSize(new Dimension(60, 30));
        color4.setPreferredSize(new Dimension(60, 30));
        color5.setPreferredSize(new Dimension(60, 30));
        color6.setPreferredSize(new Dimension(60, 30));
        color7.setPreferredSize(new Dimension(60, 30));
        color8.setPreferredSize(new Dimension(60, 30));
        palettePanel.add(color1);
        palettePanel.add(color2);
        palettePanel.add(color3);
        palettePanel.add(color4);
        palettePanel.add(color5);
        palettePanel.add(color6);
        palettePanel.add(color7);
        palettePanel.add(color8);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/palette"), L10N.label("elementgui.trimmaterial.palette", new Object[0])));
        mainPanel.add(palettePanel);

        item.setValidator(new MCItemHolderValidator(item));
        page1group.addValidationElement(item);

        name.enableRealtimeValidation();
        name.setValidator(new TextFieldValidator(this.name, "The material must have a name"));
        page1group.addValidationElement(name);

        if (!this.isEditingMode()) {
            name.setText(this.modElement.getName());
        }

        pane1.add("Center", PanelUtils.totalCenterInPanel(mainPanel));
        addPage(pane1);
    }

    public void reloadDataLists() {
        super.reloadDataLists();

    }

    protected void afterGeneratableElementStored() {
        BufferedImage palette = new BufferedImage(8, 1, 2);
        Graphics2D layerStackGraphics2D = palette.createGraphics();
        layerStackGraphics2D.setColor(color1.getColor());
        layerStackGraphics2D.drawRect(0, 0, 1, 1);
        layerStackGraphics2D.setColor(color2.getColor());
        layerStackGraphics2D.drawRect(1, 0, 1, 1);
        layerStackGraphics2D.setColor(color3.getColor());
        layerStackGraphics2D.drawRect(2, 0, 1, 1);
        layerStackGraphics2D.setColor(color4.getColor());
        layerStackGraphics2D.drawRect(3, 0, 1, 1);
        layerStackGraphics2D.setColor(color5.getColor());
        layerStackGraphics2D.drawRect(4, 0, 1, 1);
        layerStackGraphics2D.setColor(color6.getColor());
        layerStackGraphics2D.drawRect(5, 0, 1, 1);
        layerStackGraphics2D.setColor(color7.getColor());
        layerStackGraphics2D.drawRect(6, 0, 1, 1);
        layerStackGraphics2D.setColor(color8.getColor());
        layerStackGraphics2D.drawRect(7, 0, 1, 1);
        layerStackGraphics2D.dispose();
        try {
            File file = new File(GeneratorUtils.getResourceRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration()), "assets/minecraft/textures/trims/color_palettes/" + modElement.getRegistryName() + ".png");
            ImageIO.write(palette, ".png", file);
            FileIO.writeImageToPNGFile(ImageUtils.toBufferedImage(palette), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(TrimMaterial material) {
        name.setText(material.name);
        item.setBlock(material.item);
        color1.setColor(material.color1);
        color2.setColor(material.color2);
        color3.setColor(material.color3);
        color4.setColor(material.color4);
        color5.setColor(material.color5);
        color6.setColor(material.color6);
        color7.setColor(material.color7);
        color8.setColor(material.color8);
    }

    public TrimMaterial getElementFromGUI() {
        TrimMaterial material = new TrimMaterial(this.modElement);
        material.name = name.getText();
        material.item = item.getBlock();
        material.color1 = color1.getColor();
        material.color2 = color2.getColor();
        material.color3 = color3.getColor();
        material.color4 = color4.getColor();
        material.color5 = color5.getColor();
        material.color6 = color6.getColor();
        material.color7 = color7.getColor();
        material.color8 = color8.getColor();
        return material;
    }

    @Override
    @Nullable
    public URI contextURL() throws URISyntaxException {
        return null;
    }

}

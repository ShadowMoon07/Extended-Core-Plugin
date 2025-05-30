package net.magicalalexey.extended_core.ui.modgui;

import net.magicalalexey.extended_core.element.types.ArmorTrim;
import net.magicalalexey.extended_core.parts.WTextureComboBoxRenderer;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.generator.GeneratorUtils;
import net.mcreator.io.FileIO;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.minecraft.MinecraftImageGenerator;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VComboBox;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.MCItemHolderValidator;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ArmorTrimGUI extends ModElementGUI<ArmorTrim> {
    private final ValidationGroup page1group = new ValidationGroup();

    private final VTextField name;
    private final MCItemHolder item;
    private final VComboBox<String> armorTextureFile;
    private final JLabel clo1;
    private final JLabel clo2;

    public ArmorTrimGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.name = new VTextField(17);
        this.item = new MCItemHolder(this.mcreator, ElementUtil::loadBlocksAndItems);
        this.armorTextureFile = new SearchableComboBox();
        this.clo1 = new JLabel();
        this.clo2 = new JLabel();
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 0, 2));
        mainPanel.setOpaque(false);

        this.armorTextureFile.setRenderer(new WTextureComboBoxRenderer((element) -> {
            File[] armorTextures = this.mcreator.getFolderManager().getArmorTextureFilesForName(element);
            return armorTextures[0].isFile() && armorTextures[1].isFile() ? new ImageIcon(armorTextures[0].getAbsolutePath()) : null;
        }));
        ComponentUtils.deriveFont(this.armorTextureFile, 16.0F);
        this.armorTextureFile.addActionListener((e) -> {
            this.updateArmorTexturePreview();
        });
        this.armorTextureFile.setValidator(() -> {
            return this.armorTextureFile.getSelectedItem() != null && !((String)this.armorTextureFile.getSelectedItem()).isEmpty() ? Validator.ValidationResult.PASSED : new Validator.ValidationResult(Validator.ValidationResultType.ERROR, L10N.t("elementgui.armor.armor_needs_texture", new Object[0]));
        });
        this.clo1.setPreferredSize(new Dimension(320, 160));
        this.clo2.setPreferredSize(new Dimension(320, 160));

        JPanel clop = new JPanel();
        clop.add(this.clo1);
        clop.add(this.clo2);
        clop.setOpaque(false);

        JPanel merger = new JPanel(new BorderLayout(35, 35));
        merger.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/trim_name"), L10N.label("elementgui.armortrim.name", new Object[0])));
        mainPanel.add(name);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/smithing_template"), L10N.label("elementgui.armortrim.smithing_template", new Object[0])));
        mainPanel.add(item);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("armortrim/armor_layer_texture"), L10N.label("elementgui.armortrim.layer_texture", new Object[0])));
        mainPanel.add(this.armorTextureFile);

        item.setValidator(new MCItemHolderValidator(item));
        page1group.addValidationElement(item);
        page1group.addValidationElement(armorTextureFile);

        name.enableRealtimeValidation();
        name.setValidator(new TextFieldValidator(this.name, "The trim must have a name"));
        page1group.addValidationElement(name);

        if (!this.isEditingMode()) {
            name.setText(this.modElement.getName());
        }

        merger.add("Center", mainPanel);
        merger.add("South", clop);

        pane1.add("Center", PanelUtils.totalCenterInPanel(merger));
        addPage(pane1);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        List<File> armors = this.mcreator.getFolderManager().getTexturesList(TextureType.ARMOR);
        List<String> armorPart1s = new ArrayList();
        Iterator var3 = armors.iterator();

        while(var3.hasNext()) {
            File texture = (File)var3.next();
            if (texture.getName().endsWith("_layer_1.png")) {
                armorPart1s.add(texture.getName().replace("_layer_1.png", ""));
            }
        }

        ComboBoxUtil.updateComboBoxContents(this.armorTextureFile, ListUtils.merge(Collections.singleton(""), armorPart1s));
    }

    protected void afterGeneratableElementStored() {
        if (mcreator.getGenerator().getGeneratorConfiguration().getGeneratorFlavor() != GeneratorFlavor.FABRIC) {
            FileIO.copyFile(new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/models/armor/" + armorTextureFile.getSelectedItem() + "_layer_1.png"),
                    new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/trims/models/armor/" + modElement.getRegistryName() + ".png"));
            FileIO.copyFile(new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/models/armor/" + armorTextureFile.getSelectedItem() + "_layer_2.png"),
                    new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/trims/models/armor/" + modElement.getRegistryName() + "_leggings.png"));
        }
        else {
            FileIO.copyFile(new File(GeneratorUtils.getResourceRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration()), "assets/minecraft/textures/models/armor/" + armorTextureFile.getSelectedItem() + "_layer_1.png"),
                    new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/trims/models/armor/" + modElement.getRegistryName() + ".png"));
            FileIO.copyFile(new File(GeneratorUtils.getResourceRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration()), "assets/minecraft/textures/models/armor/" + armorTextureFile.getSelectedItem() + "_layer_2.png"),
                    new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"), "textures/trims/models/armor/" + modElement.getRegistryName() + "_leggings.png"));
        }
    }

    private void updateArmorTexturePreview() {
        File[] armorTextures = this.mcreator.getFolderManager().getArmorTextureFilesForName((String)this.armorTextureFile.getSelectedItem());
        if (armorTextures[0].isFile() && armorTextures[1].isFile()) {
            ImageIcon bg1 = new ImageIcon(ImageUtils.resize((new ImageIcon(armorTextures[0].getAbsolutePath())).getImage(), 320, 160));
            ImageIcon bg2 = new ImageIcon(ImageUtils.resize((new ImageIcon(armorTextures[1].getAbsolutePath())).getImage(), 320, 160));
            ImageIcon front1 = new ImageIcon(MinecraftImageGenerator.Preview.generateArmorPreviewFrame1());
            ImageIcon front2 = new ImageIcon(MinecraftImageGenerator.Preview.generateArmorPreviewFrame2());
            this.clo1.setIcon(ImageUtils.drawOver(bg1, front1));
            this.clo2.setIcon(ImageUtils.drawOver(bg2, front2));
        } else {
            this.clo1.setIcon(new ImageIcon(MinecraftImageGenerator.Preview.generateArmorPreviewFrame1()));
            this.clo2.setIcon(new ImageIcon(MinecraftImageGenerator.Preview.generateArmorPreviewFrame2()));
        }

    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(ArmorTrim trim) {
        name.setText(trim.name);
        item.setBlock(trim.item);
        armorTextureFile.setSelectedItem(trim.armorTextureFile);
        this.updateArmorTexturePreview();
    }

    public ArmorTrim getElementFromGUI() {
        ArmorTrim trim = new ArmorTrim(this.modElement);
        trim.name = name.getText();
        trim.item = item.getBlock();
        trim.armorTextureFile = (String) armorTextureFile.getSelectedItem();
        return trim;
    }

    @Override
    @Nullable
    public URI contextURL() throws URISyntaxException {
        return null;
    }

}

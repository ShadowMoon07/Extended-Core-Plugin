package net.magicalalexey.extended_core;

import freemarker.template.Template;
import net.magicalalexey.extended_core.element.types.Mixin;
import net.magicalalexey.extended_core.ui.modgui.MixinGUI;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.element.ModElementType;
import net.mcreator.generator.Generator;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.generator.template.InlineTemplatesHandler;
import net.mcreator.generator.template.base.BaseDataModelProvider;
import net.mcreator.io.FileIO;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.magicalalexey.extended_core.element.types.PluginElementTypes;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static net.mcreator.element.ModElementTypeLoader.register;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Configuration Files");
	public static final BlocklyEditorType CONFIG_EDITOR = new BlocklyEditorType("config", "cfg", "config_start");

	public Launcher(Plugin plugin) {
		super(plugin);
		addListener(MCreatorLoadedEvent.class, event -> {
			Generator currentGenerator = event.getMCreator().getGenerator();
			if (currentGenerator != null) {
				if (currentGenerator.getGeneratorConfiguration().getGeneratorFlavor() == GeneratorFlavor.FORGE) {
					Set<String> fileNames = PluginLoader.INSTANCE.getResourcesInPackage(currentGenerator.getGeneratorName() + ".workspacebase");
					Map<String, Object> dataModel = (new BaseDataModelProvider(event.getMCreator().getWorkspace().getGenerator())).provide();
					Iterator var4 = fileNames.iterator();

					while (var4.hasNext()) {
						String file = (String) var4.next();
						if (file.contains("build.gradle")) {
							InputStream stream = PluginLoader.INSTANCE.getResourceAsStream(file);
							File generatorFile = new File(event.getMCreator().getWorkspace().getWorkspaceFolder(), file.replace(currentGenerator.getGeneratorName() + "/workspacebase", ""));
							try {
								String contents = IOUtils.toString(stream, StandardCharsets.UTF_8);
								Template freemarkerTemplate = InlineTemplatesHandler.getTemplate(contents);
								StringWriter stringWriter = new StringWriter();
								freemarkerTemplate.process(dataModel, stringWriter, InlineTemplatesHandler.getConfiguration().getObjectWrapper());
								FileIO.writeStringToFile(stringWriter.getBuffer().toString(), generatorFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});

		addListener(PreGeneratorsLoadingEvent.class, event -> register(new ModElementType<>("mixin", 'M', MixinGUI::new, Mixin.class)));
		addListener(PreGeneratorsLoadingEvent.class, e -> {
			PluginElementTypes.load();
			BlocklyLoader.INSTANCE.addBlockLoader(CONFIG_EDITOR);
		});

		LOG.info("Extended core plugin was loaded");

	}

}
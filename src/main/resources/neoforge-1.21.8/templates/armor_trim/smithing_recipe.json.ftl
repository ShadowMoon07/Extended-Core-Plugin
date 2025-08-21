<#include "../transformer.ftl">
{
  "type": "minecraft:smithing_trim",
  "template": ${mappedMCItemToRegistryName(data.item)},
  "base": "#minecraft:trimmable_armor",
  "addition": "#minecraft:trim_materials",
  "pattern": "${modid}:${data.getModElement().getRegistryName()}"
}
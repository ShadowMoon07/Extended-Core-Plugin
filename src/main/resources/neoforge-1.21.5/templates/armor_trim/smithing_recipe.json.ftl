<#include "../transformer.ftl">
{
  "type": "minecraft:smithing_trim",
  "addition": {
    "tag": "minecraft:trim_materials"
  },
  "base": {
    "tag": "minecraft:trimmable_armor"
  },
  "template": {
    "item": ${mappedMCItemToRegistryName(data.item)}
  }
}
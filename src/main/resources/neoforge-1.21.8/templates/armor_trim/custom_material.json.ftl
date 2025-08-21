<#include "../transformer.ftl">
{
  "asset_name": "${data.getModElement().getRegistryName()}",
  "description": {
    "color": "${data.getHexColor()}",
    "translate": "trim_material.${modid}.${data.getModElement().getRegistryName()}"
  },
  "ingredient": ${mappedMCItemToRegistryName(data.item)},
  "item_model_index": 0.0
}
<#include "../transformer.ftl">
{
  "replace": false,
  "values": [
    <#list trimmaterials as material>
        ${mappedMCItemToRegistryName(material.item)}<#sep>,
    </#list>
  ]
}
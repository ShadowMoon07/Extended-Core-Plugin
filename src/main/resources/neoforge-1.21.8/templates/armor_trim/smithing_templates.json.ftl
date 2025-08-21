<#include "../transformer.ftl">
{
  "replace": false,
  "values": [
    <#list armortrims as trim>
        ${mappedMCItemToRegistryName(trim.item)}<#sep>,
    </#list>
  ]
}
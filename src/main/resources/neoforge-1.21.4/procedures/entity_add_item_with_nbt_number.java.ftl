<#include "mcitems.ftl">
if (${input$entity} instanceof Player _player) {
	ItemStack _setstack = ${mappedMCItemToItemStackCode(input$item, 1)};
	_setstack.setCount(${opt.toInt(input$amount)});
	CustomData.update(DataComponents.CUSTOM_DATA, _setstack, tag -> tag.putDouble(${input$tagName}, ${input$tagValue}));
	ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
}
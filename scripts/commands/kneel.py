import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	
	#commandString='object/tangible/survey_tool/shared_survey_tool_mineral.iff';
	commandString='object/tangible/survey_tool/shared_survey_tool_lumber.iff';
	#commandString='object/tangible/survey_tool/shared_survey_tool_inorganic.iff';
	#commandString='object/tangible/survey_tool/shared_survey_tool_gas.iff';
	#commandString='object/tangible/survey_tool/shared_survey_tool_solar.iff';
	#commandString='object/tangible/crafting/station/shared_generic_tool.iff';
	object = core.objectService.createObject(commandString, actor.getPlanet())
	if not object:
		return
		
	inventory = actor.getSlottedObject('inventory')
	
	if inventory:
		inventory.add(object)
	
	#core.objectService.loadResources()
	#core.objectService.loadResources2()
	
	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return

	actor.setPosture(1)
	actor.setSpeedMultiplierBase(0)
	actor.setTurnRadius(0)
	return
	
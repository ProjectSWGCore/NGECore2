import sys

def setup():
	return

def run(core, actor, target, commandString):

	if actor.getClient() and actor.getClient().isGM() == False:
		return

	if not commandString.startswith('object/tangible') and not commandString.startswith('object/weapon'):
		return

	object = core.objectService.createObject(commandString, actor.getPlanet())

	#object.setCustomizationVariable('/private/index_color_blade', 0x02)
	#object.setCustomizationVariable('private/alternate_shader_blade', 0x02)

	if not object:
		return

	inventory = actor.getSlottedObject('inventory')

	if inventory:
		inventory.add(object)

	return
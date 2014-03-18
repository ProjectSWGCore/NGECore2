import sys

def setup():
	return

def run(core, actor, target, commandString):

	if actor.getClient() and actor.getClient().isGM() == False:
		return

	if not commandString.startswith('object/tangible') and not commandString.startswith('object/weapon'):
		return

	object = core.objectService.createObject(commandString, actor.getPlanet())

	if not object:
		return

	inventory = actor.getSlottedObject('inventory')

	if inventory:
		inventory.add(object)

	return
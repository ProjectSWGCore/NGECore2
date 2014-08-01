import sys

def setup():
	return

def run(core, actor, target, commandString):

	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
	if len(commandArgs) > 1:
		arg2 = commandArgs[1]

	if actor.getClient() and actor.getClient().isGM() == False:
		return
		
	if not commandString.startswith('object/tangible') and not commandString.startswith('object/weapon'):
		return
		
	
	if  len(commandArgs) == 2:
		object = core.objectService.createObject((arg1), actor.getPlanet(), (arg2))
		
	if  len(commandArgs) == 1:
		object = core.objectService.createObject((arg1), actor.getPlanet())
	

	if not object:
		return

	inventory = actor.getSlottedObject('inventory')

	if inventory:
		inventory.add(object)

	return
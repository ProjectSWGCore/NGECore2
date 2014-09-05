import sys

def setup():
	return

def run(core, actor, target, commandString):
	parsedMsg = commandString.split(' ', 3)
	objService = core.objectService
	containerID = long(parsedMsg[1])
	container = objService.getObject(containerID)
	
	if target and container and target.getContainer():
		oldContainer = target.getContainer()
		
		if container == oldContainer:
			print 'Error: New container is same as old container.'
			return;
	
	if oldContainer == actor.getSlottedObject('appearance_inventory'):
		core.equipmentService.unequipAppearance(actor, target)
	
	oldContainer.transferTo(actor, container, target)
	
	if container == actor.getSlottedObject('appearance_inventory'):
		core.equipmentService.equipAppearance(actor, target)		
	
	return

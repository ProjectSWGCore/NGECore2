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
		
		if oldContainer == actor:
			core.equipmentService.unequip(actor, target)
		elif container == actor:
			core.equipmentService.equip(actor, target)
		else:
			oldContainer.transferTo(actor, container, target)
		
	return

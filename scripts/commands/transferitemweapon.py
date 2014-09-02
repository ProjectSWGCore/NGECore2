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

		canEquip = core.equipmentService.canEquip(actor, target)
		
		if canEquip[0] is False and container == actor:
			actor.sendSystemMessage(canEquip[1], 0)
			return

		replacedObject = None
		slotName = None
		replacedObjects = []	
		slotNames = None
		
		if actor == container:
			slotNames = container.getSlotNamesForObject(target)
			
			for slotName in slotNames:
				object = container.getSlottedObject(slotName)
				
				if not object in replacedObjects and not object == None:
					replacedObjects.append(object)
				
				if object != None:
					container.transferTo(actor, container, object)	   
		
		oldContainer.transferTo(actor, container, target)  
		
	return
	
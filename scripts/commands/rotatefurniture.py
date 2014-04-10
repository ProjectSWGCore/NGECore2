import sys
from engine.resources.scene import Point3D

def setup():
	return
	
def run(core, actor, target, commandString):
	tarObj = core.objectService.getObject(actor.getTargetId())
	
	container = actor.getContainer()
	parsedMsg = commandString.split(' ', 2)
	
	if core.housingService.getPermissions(actor, container): # i should probably relook into my permissions system... it kinda sucks
		if parsedMsg[0] == "pitch":
			core.simulationService.transform(tarObj, float(parsedMsg[1]), Point3D(1, 0, 0)) # this is messed up ???
			return
		elif parsedMsg[0] == "yaw":
			core.simulationService.transform(tarObj, float(parsedMsg[1]), Point3D(0, 1, 0)) # this is correct
			return
		elif parsedMsg[0] == "roll":
			core.simulationService.transform(tarObj, float(parsedMsg[1]), Point3D(0, 0, 1)) # this is messed up ???
			return
			
	elif container.getTemplate() == "object/cell/shared_cell.iff":
		actor.sendSystemMessage("You do not have permission to access that container!", 0)
		return
    
	return
	
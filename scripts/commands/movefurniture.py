import sys
from engine.resources.scene import Point3D

def setup():
	return
	
def run(core, actor, target, commandString):
	tarObj = core.objectService.getObject(actor.getTargetId())
	container = actor.getContainer()
	parsedMsg = commandString.split(' ', 2)
	
	if core.housingService.getPermissions(actor, container): # i should probably relook into my permissions system... it kinda sucks
		if parsedMsg[0] == "up":
			core.simulationService.transform(tarObj, Point3D(0, float(parsedMsg[1]) * 0.01, 0))
			return
		elif parsedMsg[0] == "down":
			core.simulationService.transform(tarObj, Point3D(0, float(parsedMsg[1]) * -0.01, 0))
			return
		elif parsedMsg[0] == "forward":
			core.simulationService.transform(tarObj, Point3D(0, 0, float(parsedMsg[1]) * 0.01))
			return
		elif parsedMsg[0] == "back":
			core.simulationService.transform(tarObj, Point3D(0, 0, float(parsedMsg[1]) * -0.01))
			return
		elif parsedMsg[0] == "right":
			core.simulationService.transform(tarObj, Point3D(float(parsedMsg[1]) * 0.01, 0, 0))
			return
		elif parsedMsg[0] == "left":
			core.simulationService.transform(tarObj, Point3D(float(parsedMsg[1]) * -0.01, 0, 0))
			return
			
	elif container.getTemplate() == "object/cell/shared_cell.iff":
		actor.sendSystemMessage("You do not have permission to access that container!", 0)
		return
    
	return
	
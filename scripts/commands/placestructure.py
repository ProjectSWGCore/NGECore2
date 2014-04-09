import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	cmdArgs = commandString.split(" ")
	
	deedId = long(cmdArgs[0])
	deed = core.objectService.getObject(deedId)
	
	positionX = float(cmdArgs[1])
	positionZ = float(cmdArgs[2])
	#positionY = core.terrainService.getHeight(actor.getPlanetId(), positionX, positionZ) + 2
	
	rotation = float(cmdArgs[3])
	
	
	#structureTemplate = deed.getAttachment("structureTemplate")
	
	core.housingService.placeStructure(actor, deed, positionX, positionZ, rotation)
	
	#building = core.objectService.createObject(structureTemplate, actor.getPlanet(), positionX, positionZ, positionY);
	#core.simulationService.add(building, building.getPosition().x, building.getPosition().z);
	
	return
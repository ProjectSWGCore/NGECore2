import sys

def setup():
	return

def run(core, actor, target, commandString):
	cmdArgs = commandString.split(" ")
	
	deedId = long(cmdArgs[0])
	deed = core.objectService.getObject(deedId)
	
	if deed == actor:
		deed = actor.getUseTarget()
	
	if deed.getTemplate().startswith('object/tangible/deed/harvester_deed') is True or deed.getTemplate().startswith('object/tangible/deed/generator_deed') is True:
		core.harvesterService.handlePlaceStructureCommand(actor, target, commandString)
		return
	
	positionX = float(cmdArgs[1])
	positionZ = float(cmdArgs[2])
	rotation = float(cmdArgs[3])

	if 'cityhall' in deed.getTemplate():
		core.playerCityService.handlePlaceCity(actor, deed, positionX, positionZ, rotation)
		return

	core.housingService.placeStructure(actor, deed, positionX, positionZ, rotation)
	
	return

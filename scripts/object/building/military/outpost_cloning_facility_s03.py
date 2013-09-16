import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(3)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-0.134662), float(0.125265), float(-5.80915), float(-0.0238176), float(0.998716)) 

	return
	
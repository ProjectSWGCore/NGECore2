import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(10)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-0.42), float(-0.38), float(-0.4), float(1), float(0)) 

	return
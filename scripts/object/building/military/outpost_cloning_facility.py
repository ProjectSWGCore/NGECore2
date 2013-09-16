import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(3)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(0.0387871), float(0.125265), float(-6), float(-0.0595427), float(0.998226)) 

	return

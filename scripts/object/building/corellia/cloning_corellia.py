import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(2)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(1), float(-0.05), float(2.5), float(0), float(1))
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(1), float(-0.05), float(-1.35), float(1), float(0)) 

	return
	
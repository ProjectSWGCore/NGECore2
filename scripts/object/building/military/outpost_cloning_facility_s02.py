import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(6)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-5.5), float(0.125265), float(-3.43841), float(0.699512), float(0.714621)) 

	return
	
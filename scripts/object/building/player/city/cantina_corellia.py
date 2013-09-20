import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(4)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/terminal_player_structure.iff', object.getPlanet().getName(), cell.getObjectID(), float(-13.28604), float(-2.110639), float(-6.443224), float(0.707107), float(0.707107))
		stcSvc.spawnObject('object/tangible/terminal/terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-13.57625), float(-2.5), float(3.1), float(0.707107), float(0.707107)) 

	return
	
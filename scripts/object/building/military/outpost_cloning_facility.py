import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(3)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(0.0387871), float(0.125265), float(-6), float(-0.0595427), float(0.998226)) 
	
	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(3.2408, 0.125266, -3.59732, 0.707641, -0.706572, 5))
		
	object.setAttachment('spawnPoints', spawnPoints)

	return

import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(3)

	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-0.134662), float(0.125265), float(-5.80915), float(-0.0238176), float(0.998716)) 

	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(4.42891, 0.125266, -3.717, 0.712528, -0.701643, 5))
	spawnPoints.add(SpawnPoint(4.45469, 0.125266, 1.18026, 0.72696, -0.68668, 4))
	spawnPoints.add(SpawnPoint(4.73, 0.125266, 3.02662, 0.717716, -0.696336, 4))
		
	object.setAttachment('spawnPoints', spawnPoints)
	
	return
	
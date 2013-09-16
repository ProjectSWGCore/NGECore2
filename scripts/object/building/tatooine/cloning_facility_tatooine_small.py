import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(10)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-0.42), float(-0.38), float(-0.4), float(1), float(0)) 
	
	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(0.445015, 0.271775, 1.39199, 0.707038, 0.707176, 9))
		
	object.setAttachment('spawnPoints', spawnPoints)

	return
	
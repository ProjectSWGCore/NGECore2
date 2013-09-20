import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(6)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_cloning.iff', object.getPlanet().getName(), cell.getObjectID(), float(-5.5), float(0.125265), float(-3.43841), float(0.699512), float(0.714621)) 

	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(4.44727, 0.125266, -3.80136, -0.706468, 0.707745, 5))
	spawnPoints.add(SpawnPoint(4.65395, 0.125266, 1.49466, 0.714548, -0.699587, 4))
	spawnPoints.add(SpawnPoint(-3.99569, 0.125266, -3.51553, 0.70407, 0.710131, 6))
		
	object.setAttachment('spawnPoints', spawnPoints)

	return
	
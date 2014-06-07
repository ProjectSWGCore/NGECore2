import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(-5.5), float(0.13), float(-3.44), float(0.70), float(0.71), 6)

	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(4.45, 0.13, -3.80, -0.71, 0.71, 5))
	spawnPoints.add(SpawnPoint(4.65, 0.13, 1.50, 0.71, -0.70, 4))
	spawnPoints.add(SpawnPoint(-3.99, 0.13, -3.52, 0.70, 0.71, 6))

	object.setAttachment('spawnPoints', spawnPoints)

	return
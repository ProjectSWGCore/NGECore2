import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(0.04), float(0.16), float(-6), float(-0.06), float(0.99), 3)
		
	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(3.24, 0.16, -3.60, 0.71, -0.71, 5))
		
	object.setAttachment('spawnPoints', spawnPoints)

	return

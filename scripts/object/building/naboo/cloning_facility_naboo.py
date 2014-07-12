import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(1), float(-0.05), float(2.5), float(0), float(1), 2)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(1), float(-0.05), float(-1.35), float(1), float(0), 2)

	objSvc.createChildObject(object, 'object/mobile/shared_21b_surgical_droid.iff', float(-3.2), float(0.1), float(0.6), float(0), float(0), 2)

	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(-16.66, -4.29, -10.50, 0.71, 0.70, 4))
	spawnPoints.add(SpawnPoint(-16.60, -4.29, -14.17, 0.72, 0.69, 4))
	spawnPoints.add(SpawnPoint(16.58, -4.29, -14.21, 0.71, -0.70, 4))
	spawnPoints.add(SpawnPoint(16.69, -4.29, -10.54, -0.70, 0.72, 4))
	spawnPoints.add(SpawnPoint(6.33, -4.79, 0.61, 0.99, 0.01, 4))
	spawnPoints.add(SpawnPoint(1.58, -4.79, 0.58, 0.99, 0.01, 4))
	spawnPoints.add(SpawnPoint(-2.84, -4.79, 0.66, -0.04, 0.99, 4))
		
	object.setAttachment('spawnPoints', spawnPoints)
	
	core.mapService.addLocation(object.getPlanet(), 'Cloning Facility', object.getPosition().x, object.getPosition().z, 5, 0, 0)
	return
	
import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(1), float(-0.05), float(2.5), float(0), float(1), 2)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(1), float(-0.05), float(-1.35), float(1), float(0), 2)
	
	objSvc.createChildObject(object, 'object/mobile/shared_21b_surgical_droid.iff', float(-3.2), float(0.1), float(0.6), float(0), float(0), 2)

	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(-16.6514, -4.29167, -10.4976, 0.709453, 0.704753, 4))
	spawnPoints.add(SpawnPoint(-16.5965, -4.29167, -14.1652, 0.721346, 0.692575, 4))
	spawnPoints.add(SpawnPoint(16.5771, -4.29167, -14.2091, 0.71195, -0.702231, 4))
	spawnPoints.add(SpawnPoint(16.6876, -4.29167, -10.5391, -0.6972, 0.716877, 4))
	spawnPoints.add(SpawnPoint(6.32992, -4.79167, 0.613332, 0.999992, 0.00405023, 4))
	spawnPoints.add(SpawnPoint(1.58067, -4.79167, 0.582775, 0.989691, 0.0143216, 4))
	spawnPoints.add(SpawnPoint(-2.84125, -4.79167, 0.657588, -0.0356012, 0.999366, 4))
		
	object.setAttachment('spawnPoints', spawnPoints)
	
	core.mapService.addLocation(object.getPlanet(), 'Cloning Facility', object.getPosition().x, object.getPosition().z, 5, 0, 0)
	return
	
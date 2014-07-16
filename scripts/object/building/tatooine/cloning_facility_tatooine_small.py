import sys
from java.util import Vector
from resources.common import SpawnPoint

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_cloning.iff', float(-0.42), float(-0.38), float(-0.4), float(1), float(0), 10)
		
	objSvc.createChildObject(object, 'object/mobile/shared_21b_surgical_droid.iff', float(3.8), float(0.3), float(5.1), float(0), float(0), 1)
	
	spawnPoints = Vector()
	spawnPoints.add(SpawnPoint(0.445015, 0.271775, 1.39199, 0.707038, 0.707176, 9))
		
	object.setAttachment('spawnPoints', spawnPoints)
	
	core.mapService.addLocation(object.getPlanet(), 'Cloning Facility', object.getPosition().x, object.getPosition().z, 5, 0, 0)

	return
	
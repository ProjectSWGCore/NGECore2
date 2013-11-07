import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(4)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722))
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(3.1), float(0.6), float(49), float(0), float(1)) 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(-2.7), float(0.6), float(49), float(0), float(1)) 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722))
	core.mapService.addLocation(object.getPlanet(), 'Starport', object.getPosition().x, object.getPosition().z, 15, 0, 0)
	return
	
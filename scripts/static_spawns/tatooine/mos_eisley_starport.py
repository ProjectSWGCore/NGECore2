# Project SWG:   Mos Eisley Starport interior:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService
	
	
	starport = core.objectService.getObject(long(1106368))

	stcSvc.spawnObject('chassisbroker', 'tatooine', starport.getCellByCellNumber(long(4)), float(2.10), float(0.64), float(67.82), float(0.054), float(0), float(0.999), float(0))	
	
	commoner1 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(7)), float(37.47), float(0.64), float(35.08), float(0.998), float(0), float(-0.108), float(0)) 
	commoner2 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(7)), float(36.44), float(0.64), float(37.15), float(0.690), float(0), float(0.723), float(0)) 
	commoner3 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(7)), float(39.47), float(0.64), float(36), float(0.790), float(0), float(-0.614), float(0)) 
	commoner4 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(7)), float(52.8), float(0.64), float(47.47), float(0.723), float(0), float(-0.691), float(0)) 
	commoner5 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(7)), float(42.48), float(-0.52), float(32.32), float(0.115), float(0), float(0.993), float(0))
	commoner6 = stcSvc.spawnObject('aurilliancontact', 'tatooine', starport.getCellByCellNumber(long(7)), float(54.38), float(-0.52), float(42.25), float(0.631), float(0), float(0.776), float(0))  
	
	jawa1 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3654.96), float(5), float(-4766.49), float(0.439), float(0), float(0.898), float(0))	
	jawa2 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3655.17), float(5), float(-4768.71), float(0.902), float(0), float(-0.430), float(0))	
	jawa3 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3665.62), float(5), float(-4799.25), float(0.806), float(0), float(-0.591), float(0))	

	r3 = stcSvc.spawnObject('r3unit', 'tatooine', starport.getCellByCellNumber(long(9)), float(-27.97), float(1.64), float(51.45), float(0.627), float(0), float(0.779), float(0)) 
	commoner5 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(9)), float(-40.18), float(1.64), float(61.97), float(-0.125), float(0), float(0.992), float(0)) 
	commoner6 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(9)), float(-40.75), float(1.64), float(59.72), float(-0.989), float(0), float(0.151), float(0)) 
	
	bartenderdroid = stcSvc.spawnObject('bartenderdroid', 'tatooine', starport.getCellByCellNumber(long(12)), float(-58.55), float(5.64), float(39.89), float(0.639), float(0), float(0.769), float(0))  
	commoner7 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(12)), float(-53.40), float(2.64), float(41.74), float(0.995), float(0), float(0.099), float(0))
	commoner8 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(12)), float(-48.43), float(2.64), float(29.67), float(-0.063), float(0), float(0.998), float(0))

	java4 = stcSvc.spawnObject('staticjawa', 'tatooine', starport.getCellByCellNumber(long(14)), float(-56.65), float(-0.975), float(9.13), float(1), float(0), float(0), float(0))  
	
	commoner9 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(4)), float(-9.52), float(0.64), float(61.47), float(0.610), float(0), float(0.792), float(0)) 
	commoner10 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(4)), float(-1.59), float(0.64), float(57.24), float(0.946), float(0), float(-0.323), float(0)) 
	commoner11 = stcSvc.spawnObject('commoner', 'tatooine', starport.getCellByCellNumber(long(4)), float(10.51), float(0.64), float(68.06), float(-0.560), float(0), float(-0.998), float(0))
	
	patrolpoints = Vector()
	patrolpoints.add(Point3D(float(3579.67), float(5), float(-4778.32)))
	patrolpoints.add(Point3D(float(3595.03), float(5), float(-4824.75)))
	patrolpoints.add(Point3D(float(3654.56), float(5), float(-4799.99)))
	patrolpoints.add(Point3D(float(3665.62), float(5), float(-4799.25)))

	aiSvc.setPatrol(jawa3, patrolpoints)
	return
	

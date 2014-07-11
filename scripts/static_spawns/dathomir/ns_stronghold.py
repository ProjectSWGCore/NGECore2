import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D


def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService

	# N Entrance Guards
	ns_sentry1 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4100), float(127), float(-100), float(0.55), float(0.83))
	ns_sentry2 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4097), float(127), float(-101), float(0.55), float(0.83))
	ns_sentry3 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4106), float(127), float(-103), float(0.55), float(0.83))
	ns_sentry4 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4097), float(127), float(-104), float(0.55), float(0.83))
	ns_sentry5 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4095), float(127), float(-105), float(0.55), float(0.83))
	
	patrolpoints1 = Vector()
	patrolpoints1.add(Point3D(float(-4062), float(126), float(-92)))
	patrolpoints1.add(Point3D(float(-4019), float(124), float(-95)))
	patrolpoints1.add(Point3D(float(-4003), float(123), float(-74)))
	patrolpoints1.add(Point3D(float(-3972), float(126), float(-76)))
	patrolpoints1.add(Point3D(float(-4003), float(123), float(-74)))
	patrolpoints1.add(Point3D(float(-4019), float(124), float(-95)))
	patrolpoints1.add(Point3D(float(-4100), float(127), float(-100)))
	
	aiSvc.setPatrol(ns_sentry1, patrolpoints1)	
	aiSvc.setPatrol(ns_sentry2, patrolpoints1)	
	aiSvc.setPatrol(ns_sentry3, patrolpoints1)	
	aiSvc.setPatrol(ns_sentry4, patrolpoints1)	
	aiSvc.setPatrol(ns_sentry5, patrolpoints1)
	
	# SW Entrance Guards
	ns_sentry6 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4009.7249), float(119.7003), float(-238.9914), float(-0.6984), float(0.7157))
	ns_sentry7 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4008.7249), float(119.7003), float(-238.9914), float(-0.6984), float(0.7157))
	ns_sentry8 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4007.7249), float(119.7003), float(-238.9914), float(-0.6984), float(0.7157))
	ns_sentry9 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4006.7249), float(119.7003), float(-238.9914), float(-0.6984), float(0.7157))
	ns_sentry10 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4005.7249), float(119.7003), float(-238.9914), float(-0.6984), float(0.7157))
		
	patrolpoints2 = Vector()
	patrolpoints2.add(Point3D(float(-4023.24), float(118.67), float(-242.87)))
	patrolpoints2.add(Point3D(float(-4065.97), float(124.01), float(-278.84)))
	patrolpoints2.add(Point3D(float(-4045.83), float(123.62), float(-299.27)))
	patrolpoints2.add(Point3D(float(-4065.97), float(124.01), float(-278.84)))
	patrolpoints2.add(Point3D(float(-4023.24), float(118.67), float(-242.87)))
	patrolpoints2.add(Point3D(float(-4009.72), float(119.70), float(-238.99)))
	
	aiSvc.setPatrol(ns_sentry6, patrolpoints2)	
	aiSvc.setPatrol(ns_sentry7, patrolpoints2)	
	aiSvc.setPatrol(ns_sentry8, patrolpoints2)	
	aiSvc.setPatrol(ns_sentry9, patrolpoints2)	
	aiSvc.setPatrol(ns_sentry10, patrolpoints2)
	
	#NW Entrance Guards
	ns_sentry11 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4131.4868), float(125.9164), float(-127.0344), float(0.0496), float(0.9988))
	ns_sentry12 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4131.4868), float(125.9164), float(-127.0344), float(0.0496), float(0.9988))
	ns_sentry13 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4131.4868), float(125.9164), float(-127.0344), float(0.0496), float(0.9988))
	ns_sentry14 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4131.4868), float(125.9164), float(-127.0344), float(0.0496), float(0.9988))
	ns_sentry15 = stcSvc.spawnObject('nightsister_sentry', 'dathomir', long(0), float(-4131.4868), float(125.9164), float(-127.0344), float(0.0496), float(0.9988))
	
	patrolpoints3 = Vector()
	patrolpoints3.add(Point3D(float(-4126.84), float(121.32), float(-93.56)))
	patrolpoints3.add(Point3D(float(-4129.24), float(120.81), float(-23.27)))
	patrolpoints3.add(Point3D(float(-4126.84), float(121.32), float(-93.56)))
	patrolpoints3.add(Point3D(float(-4131.4868), float(125.9164), float(-127.0344)))
	
	aiSvc.setPatrol(ns_sentry11, patrolpoints3)	
	aiSvc.setPatrol(ns_sentry12, patrolpoints3)	
	aiSvc.setPatrol(ns_sentry13, patrolpoints3)	
	aiSvc.setPatrol(ns_sentry14, patrolpoints3)	
	aiSvc.setPatrol(ns_sentry15, patrolpoints3)
	
	# Quest NPCs etc.
	ns_elder =  stcSvc.spawnObject('nightsister_elder', 'dathomir', long(0), float(-3975), float(131), float(-158), float(0.992), float(-0.002)) 
	baritha =  stcSvc.spawnObject('baritha', 'dathomir', long(0), float(-3976), float(131), float(-172), float(0.992), float(-0.124))
	fath_hray =  stcSvc.spawnObject('fath_hray', 'dathomir', long(0), float(-4118), float(105), float(-156), float(0.992), float(-0.124))
	spell_leandra =  stcSvc.spawnObject('leandra', 'dathomir', long(0), float(-4085), float(105), float(-210), float(0.992), float(-0.124))
	gate_guardian =  stcSvc.spawnObject('gate_guardian', 'dathomir', long(0), float(-3999), float(105), float(-65), float(0.992), float(-0.124))
	gate_keeper =  stcSvc.spawnObject('gate_keeper', 'dathomir', long(189375), float(0.5690), float(0.7599), float(-1.5566), float(-0.0166), float(0.9999))
	ns_vendor = stcSvc.spawnObject('ns_vendor', 'dathomir', long(189379), float(0.6680), float(0.7599), float(-53.5354), float(-0.0587), float(0.9983))
	satra = stcSvc.spawnObject('satra', 'dathomir', long(189378), float(-13.5346), float(0.7599), float(-12.9636), float(0.9879), float(-0.1549))
	rancor_tamer =  stcSvc.spawnObject('st_rancor_tamer', 'dathomir', long(0), float(-4156), float(105), float(-83), float(0.992), float(-0.124))	
	diax = stcSvc.spawnObject('diax', 'dathomir', long(189384), float(-19.0310), float(7.2190), float(-27.2504), float(0.6629), float(0.7487))
	kais = stcSvc.spawnObject('kais', 'dathomir', long(189382), float(13.62), float(7.21), float(-21.23), float(0.915), float(-0.402))
	gethzerion = stcSvc.spawnObject('gethzerion', 'dathomir', long(189383), float(0.109), float(7.21), float(-4.17), float(0.992), float(-0.119))
	
	pirate1 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3964.2300), float(131.6703), float(-201.5431), float(0.1860), float(0.9825))
	pirate2 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3957.6968), float(131.5686), float(-190.7077), float(0.9335), float(-0.3585))
	aiSvc.setLoiter(pirate1, float(6), float(10))
	aiSvc.setLoiter(pirate2, float(6), float(10))

	pirate3 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3935.2058), float(130.9562), float(-198.1271), float(0.9974), float(-0.0719))
	pirate4 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3934.0264), float(131.3799), float(-212.0725), float(-0.1989), float(0.9800))
	aiSvc.setLoiter(pirate3, float(6), float(10))
	aiSvc.setLoiter(pirate4, float(6), float(10))
	
	pirate5 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3951.2546), float(123.8093), float(-258.2355), float(0.9539), float(-0.3003))
	pirate6 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3963.6440), float(122.6283), float(-270.6916), float(0.2454), float(0.9694))
	aiSvc.setLoiter(pirate5, float(6), float(10))
	aiSvc.setLoiter(pirate6, float(6), float(10))

	pirate7 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3959.8911), float(123.3668), float(-254.1581), float(0.3242), float(0.9460))
	pirate8 = stcSvc.spawnObject('ns_pirate', 'dathomir', long(0), float(-3950.1411), float(125.6901), float(-244.8228), float(0.9176), float(-0.3975))
	aiSvc.setLoiter(pirate7, float(6), float(10))
	aiSvc.setLoiter(pirate8, float(6), float(10))
	
	return	

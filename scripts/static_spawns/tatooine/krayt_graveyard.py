import sys
# Project SWG:   Krayt Graveyard:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# Canyons, 1 canyon will always spawn together with 1 juveline
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(7227.5), float(33.2), float(4495.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(7178), float(24.5), float(4429.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(7035.5), float(22.8), float(4337.3), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(6872.9), float(41.7), float(4246.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(6564.1), float(87.7), float(4484.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(7523.5), float(4.5), float(4655.5), float(0), float(0), float(0), float(0), 1800)	
	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(7233.2), float(30.1), float(4487.1), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(7172.2), float(22.5), float(4441.1), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(7043.2), float(22.1), float(4344.1), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(6877.8), float(45.5), float(4237.0), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(6575.9), float(92.5), float(4487.7), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(7513.2), float(4.1), float(4656.1), float(0), float(0), float(0), float(0), 1500)	
	
	# giant canyon
	
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(7515.5), float(8.5), float(4556.5), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(7417.5), float(7.7), float(4597.5), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(7300.0), float(23.3), float(4462.0), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(7140.5), float(57.0), float(4322.5), float(0), float(0), float(0), float(0), 1800)	

	# grand
	
	stcSvc.spawnObject('grand_krayt_dragon', 'tatooine', long(0), float(7555.5), float(15.2), float(4488.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('grand_krayt_dragon', 'tatooine', long(0), float(7429.9), float(7.2), float(4477.3), float(0), float(0), float(0), float(0), 1800)	
	
	# ancient
	
	stcSvc.spawnObject('krayt_dragon_ancient', 'tatooine', long(0), float(6836.5), float(25.2), float(4321.8), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('krayt_dragon_ancient', 'tatooine', long(0), float(7491.3), float(8.2), float(4484.8), float(0), float(0), float(0), float(0), 1800)	

	# adolescent
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(7525.1), float(28.2), float(4387.5), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(7269.8), float(62.4), float(4352.8), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(7077.5), float(20.0), float(4447.3), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(7003.0), float(86.2), float(4200.4), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(6726.8), float(19.5), float(4288.6), float(0), float(0), float(0), float(0), 1500)	
	
	
	return
	

import sys
# Project SWG:   Tusken ZElaots:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService

	# Outside of Tusken camp
	zealot = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4005), float(5), float(-4846), float(0), float(0), float(0), float(0), 45)	
	zealot1 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4009), float(5), float(-4851), float(0), float(0), float(0), float(0), 45)	
	zealot2 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4009), float(5), float(-4852), float(0), float(0), float(0), float(0), 45)	

	# Outside of Tusken camp pt2
	
	zealot3 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4051), float(5), float(-4859), float(0), float(0), float(0), float(0), 45)	
	zealot4 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4050), float(5), float(-4859), float(0), float(0), float(0), float(0), 45)	
	zealot5 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4050), float(5), float(-4858), float(0), float(0), float(0), float(0), 45)	
	
	# inside camp pt1 (behind the camp)
	
	zealot5 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4076), float(5), float(-4812), float(0), float(0), float(0), float(0), 45)	
	zealot6 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4075), float(5), float(-4812), float(0), float(0), float(0), float(0), 45)	
	zealot7 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4074), float(5), float(-4812), float(0), float(0), float(0), float(0), 45)	
	
	# inside camp p2 (coming from starport)
	
	zealot8 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(3991), float(5), float(-4794), float(0), float(0), float(0), float(0), 45)	
	zealot9 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(3995), float(5), float(-4788), float(0), float(0), float(0), float(0), 45)	
	zealot10 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(3993), float(5), float(-4794), float(0), float(0), float(0), float(0), 45)	
	zealot11 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(3992), float(5), float(-4812), float(0), float(0), float(0), float(0), 45)	
	zealot12 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(3997), float(5), float(-4812), float(0), float(0), float(0), float(0), 45)	
	zealot12 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4010), float(5), float(-4811), float(0), float(0), float(0), float(0), 45)	
	zealot13 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4014), float(5), float(-4811), float(0), float(0), float(0), float(0), 45)	
	zealot14 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4010), float(5), float(-4795), float(0), float(0), float(0), float(0), 45)	
	zealot15 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4013), float(5), float(-4793), float(0), float(0), float(0), float(0), 45)	
	zealot16 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4005), float(5), float(-4783), float(0), float(0), float(0), float(0), 45)	
	
	# inside camp pt3 (2nd from starport
	
	zealot17 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4009), float(5), float(-4758), float(0), float(0), float(0), float(0), 45)	
	zealot18 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4010), float(5), float(-4759), float(0), float(0), float(0), float(0), 45)	
	zealot19 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4028), float(5), float(-4767), float(0), float(0), float(0), float(0), 45)	
	zealot20 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4020), float(5), float(-4766), float(0), float(0), float(0), float(0), 45)	
	zealot21 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4038), float(5), float(-4736), float(0), float(0), float(0), float(0), 45)	
	zealot22 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4033), float(5), float(-4743), float(0), float(0), float(0), float(0), 45)	
	zealot23 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4048), float(5), float(-4767), float(0), float(0), float(0), float(0), 45)	
	zealot24 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4039), float(5), float(-4767), float(0), float(0), float(0), float(0), 45)	
	zealot25 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4056), float(5), float(-4787), float(0), float(0), float(0), float(0), 45)	
	zealot26 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4047), float(5), float(-4787), float(0), float(0), float(0), float(0), 45)	
	zealot27 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4044), float(5), float(-4807), float(0), float(0), float(0), float(0), 45)	
	zealot28 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4049), float(5), float(-4807), float(0), float(0), float(0), float(0), 45)	
	zealot29 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4065), float(5), float(-4774), float(0), float(0), float(0), float(0), 45)	
	zealot30 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4063), float(5), float(-4767), float(0), float(0), float(0), float(0), 45)	
	zealot31 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4074), float(5), float(-4755), float(0), float(0), float(0), float(0), 45)	
	zealot32 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4065), float(5), float(-4755), float(0), float(0), float(0), float(0), 45)	
	zealot33 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4065), float(5), float(-4743), float(0), float(0), float(0), float(0), 45)	
	zealot34 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4070), float(5), float(-4743), float(0), float(0), float(0), float(0), 45)	
	zealot35 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4094), float(5), float(-4747), float(0), float(0), float(0), float(0), 45)	
	zealot36 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4102), float(5), float(-4753), float(0), float(0), float(0), float(0), 45)	
	zealot37 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4093), float(5), float(-4771), float(0), float(0), float(0), float(0), 45)	
	zealot38 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4080), float(5), float(-4766), float(0), float(0), float(0), float(0), 45)	
	zealot39 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4083), float(5), float(-4787), float(0), float(0), float(0), float(0), 45)	
	zealot40 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4094), float(5), float(-4783), float(0), float(0), float(0), float(0), 45)	
	zealot41 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4084), float(5), float(-4801), float(0), float(0), float(0), float(0), 45)	
	
	# inside camp pt4 terrace of the building
	
	zealot42 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4081), float(8), float(-4752), float(0), float(0), float(0), float(0), 45)	
	zealot43 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4078), float(8), float(-4759), float(0), float(0), float(0), float(0), 45)	
	zealot44 = stcSvc.spawnObject('tusken_zealot', 'tatooine', long(0), float(4086), float(8), float(-4762), float(0), float(0), float(0), float(0), 45)	
	
	return	

import sys
# Project SWG:   Moseisley Tusken soldiers:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Tusken Solders
	soldiers = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4168), float(8), float(-4735), float(0), float(0))
	soldiers1 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4170), float(8), float(-4731), float(0), float(0))
	soldiers11 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4172), float(8), float(-4727), float(0), float(0))
	soldiers12 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4174), float(8), float(-4723), float(0), float(0))
	soldiers13 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4176), float(8), float(-4719), float(0), float(0))
	soldiers14 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4178), float(8), float(-4739), float(0), float(0))
	soldiers15 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4176), float(8), float(-4743), float(0), float(0))
	soldiers16 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4174), float(8), float(-4747), float(0), float(0))
	soldiers17 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4172), float(8), float(-4751), float(0), float(0))
	soldiers18 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4170), float(8), float(-4756), float(0), float(0))
	
	soldiers2 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4162), float(8), float(-4735), float(0), float(0))
	soldiers21 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4164), float(8), float(-4731), float(0), float(0))
	soldiers22 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4166), float(8), float(-4727), float(0), float(0))
	soldiers23 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4168), float(8), float(-4723), float(0), float(0))
	soldiers24 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4170), float(8), float(-4719), float(0), float(0))
	soldiers25 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4168), float(8), float(-4739), float(0), float(0))
	soldiers26 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4166), float(8), float(-4743), float(0), float(0))
	soldiers27 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4164), float(8), float(-4747), float(0), float(0))
	soldiers28 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4162), float(8), float(-4751), float(0), float(0))
	soldiers29 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4160), float(8), float(-4756), float(0), float(0))
	
		
	soldiers3 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4158), float(8), float(-4735), float(0), float(0))
	soldiers31 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4160), float(8), float(-4731), float(0), float(0))
	soldiers32 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4162), float(8), float(-4727), float(0), float(0))
	soldiers33 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4164), float(8), float(-4723), float(0), float(0))
	soldiers34 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4166), float(8), float(-4719), float(0), float(0))
	soldiers35 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4164), float(8), float(-4739), float(0), float(0))
	soldiers36 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4162), float(8), float(-4743), float(0), float(0))
	soldiers37 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4160), float(8), float(-4747), float(0), float(0))
	soldiers38 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4158), float(8), float(-4751), float(0), float(0))
	soldiers39 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4156), float(8), float(-4756), float(0), float(0))
						
	soldiers4 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4154), float(8), float(-4735), float(0), float(0))
	soldiers41 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4156), float(8), float(-4731), float(0), float(0))
	soldiers42 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4158), float(8), float(-4727), float(0), float(0))
	soldiers43 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4160), float(8), float(-4723), float(0), float(0))
	soldiers44 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4158), float(8), float(-4719), float(0), float(0))
	soldiers45 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4156), float(8), float(-4739), float(0), float(0))
	soldiers46 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4154), float(8), float(-4743), float(0), float(0))
	soldiers47 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4152), float(8), float(-4747), float(0), float(0))
	soldiers48 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4156), float(8), float(-4751), float(0), float(0))
	soldiers49 = stcSvc.spawnObject('tusken_soldier', 'tatooine', long(0), float(4158), float(8), float(-4756), float(0), float(0))
	
	
	
	return
	

import sys
# Project SWG:   Jabba TP Alkharan Camp:  Static Spawns
# (C)2014 ProjectSWG

#pael  dewall_paez

#spy wayfar_spy

from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	#temp until buildouts are fixed
	stcSvc.spawnObject('object/static/item/shared_lair_wooden_tent.iff', 'tatooine', long(0), float(-5525.815), float(39.7917), float(-6625.21), float(0.983671), float(0.0816295), float(0.153112), float(0.0478033))
	stcSvc.spawnObject('object/static/item/shared_lair_wooden_tent.iff', 'tatooine', long(0), float(-5517.64), float(43.2684), float(-6635.27), float(0.731855), float(0.157236), float(0.658869), float(0.0745461))
	stcSvc.spawnObject('object/static/item/shared_lair_wooden_tent_destroyed.iff', 'tatooine', long(0), float(-5516.212), float(42.2543), float(-6604.4), float(0.832558), float(0.150685), float(0.532377), float(0.0267597))
	stcSvc.spawnObject('object/static/item/shared_lair_wooden_tent.iff', 'tatooine', long(0), float(-5491.42), float(45.0825), float(-6608.95), float(0.499163), float(-0.143837), float(0.853988), float(-0.0291871))
	
	
	return

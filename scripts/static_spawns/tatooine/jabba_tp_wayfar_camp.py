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
	
	
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5467.4), float(46.7), float(-6601.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5468.5), float(46), float(-6583.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5488.4), float(46), float(-6590.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5503.4), float(46.7), float(-6589.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5524.5), float(46.4), float(-6586.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5539.6), float(40.1), float(-6602.4), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5553.3), float(38.5), float(-6613.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5553.4), float(42), float(-6634.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5556.7), float(47.5), float(-6650.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5538.1), float(47.9), float(-6661.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5525.2), float(45.2), float(-6642.3), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5508.7), float(41.9), float(-6627.1), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5478.3), float(47.2), float(-6624.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5486.8), float(48.2), float(-6645.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5503.6), float(46.5), float(-6660.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5517.6), float(48), float(-6654.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5487.3), float(45), float(-6618.9), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('wayfar_spy', 'tatooine', long(0), float(-5464.6), float(48.7), float(-6621.8), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('dewall_paez', 'tatooine', long(0), float(-5516.1), float(40.1), float(-6615.1), float(0), float(0), float(0), float(0), 45)
	
	return

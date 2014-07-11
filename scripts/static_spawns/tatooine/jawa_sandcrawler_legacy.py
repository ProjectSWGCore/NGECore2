import sys
# Project SWG:   Jawa Sandcrawler - Legacy Quest Area:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	#Outside
	fa2po = stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid.iff', 'tatooine', long(0), float(-3805.7), float(30.4), float(-4721.6), float(0), float(0))
	fa2po.setCustomName2('FA-2PO')	
	fa2po.setOptionsBitmask(256)
	
	
	#tusken point 1
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3691), float(4.5), float(-4781), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3675), float(2), float(-4790), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3669), float(2.1), float(-4780), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3687), float(3), float(-4794), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3674), float(0.5), float(-4804), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3696), float(3.6), float(-4803), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3681), float(3.8), float(-4777), float(0), float(0), float(0), float(0), 45)
		
	#tusken point 2
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3529), float(0), float(-4638), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3523), float(0), float(-4646), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3510), float(0), float(-4641), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3511), float(0), float(-4629), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3522), float(0), float(-4623), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3532), float(0), float(-4623), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_outrider', 'tatooine', long(0), float(-3523), float(0), float(-4632), float(0), float(0), float(0), float(0), 45)
	return
	

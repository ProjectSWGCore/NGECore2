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
	
	
	return
	

import sys
# Project SWG:   Wayfar:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	
	#Junkdealer
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-5242), float(75), float(-6495), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-5129), float(75), float(-6584), float(0), float(0))
	return
	

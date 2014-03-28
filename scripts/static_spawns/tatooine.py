import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	#this script is no longer for NPC spawning usage.  - Levarris
	
	#stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', long(26582), float(-14.3), float(2.0), float(47.4), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(3525), float(4), float(-4801), float(0.70), float(0.71))
	
	return
	

import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	
	# Objects
	
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', long(0), 'tatooine', long(0), float(3525), float(4), float(-4801), float(0.70), float(0), float(0.71), float(0))
	
	# Buildings
	
	# Example
	#building = stcSvc.spawnObject('object/building/myThatchedHut.iff', 'tatooine', long(0), float(3525), float(4), float(-4804), float(0.70), float(0.71))
	#stcSvc.spawnObject('object/mobile/shared_my_npc.iff', 'tatooine', building.getCellByCellNumber(7), float(3525), float(4), float(-4804), float(0.70), float(0.71))	
	
	# NPCs
	
	respecEisley = stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', long(0), 'tatooine', long(0), float(3533.14), float(5), float(-4788.86), float(-0.3327), float(0.9288))
	respecEisley.setOptionsBitmask(264)
	respecEisley.setCustomName('a Profession Counselor')
	
	junkDealer1 = stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', long(0), 'tatooine', long(0), float(3525), float(4), float(-4804), float(0.70), float(0.71))
	junkDealer1.setOptionsBitmask(264)
	#stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', long(26582), float(-14.3), float(2.0), float(47.4), float(0.70), float(0.71))
	
	return
	

import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	
	# Objects
	
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(3525), float(4), float(-4801), float(0.70), float(0.71))
	
	# Buildings
	
	# Example
	#building = stcSvc.spawnObject('object/building/myThatchedHut.iff', 'tatooine', long(0), float(3525), float(4), float(-4804), float(0.70), float(0.71))
	#stcSvc.spawnObject('object/mobile/shared_my_npc.iff', 'tatooine', building.getCellByCellNumber(7), float(3525), float(4), float(-4804), float(0.70), float(0.71))	
	
	# NPCs
	
	stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', 'tatooine', long(0), float(3533.14), float(5), float(-4788.86), float(-0.3327), float(0.9288))
	
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3525), float(4), float(-4804), float(0.70), float(0.71))

	#stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', long(26582), float(-14.3), float(2.0), float(47.4), float(0.70), float(0.71))
		
	impRecruiter = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_f.iff', 'tatooine', long(1280132), float(-6), float(1), float(9.2), float(0.70), float(0.71))	
	impRecruiter.setOptionsBitmask(264)
	
	rebRecruiter = stcSvc.spawnObject('object/mobile/shared_dressed_rebel_recruiter_human_female_01.iff', 'tatooine', long(1082887), float(-30.5), float(-0.5), float(6.2), float(0.70), float(0.71))	
	rebRecruiter.setOptionsBitmask(264)
	
	
	return
	

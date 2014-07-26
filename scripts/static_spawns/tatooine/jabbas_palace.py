import sys
# Project SWG:   Jabba's Palace:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	building = core.objectService.getObject(long(-466404040703346823))
	
	#reelo baruk
	stcSvc.spawnObject('object/mobile/shared_reelo_baruk.iff', 'tatooine', building.getCellByCellNumber(1), float(-3.5), float(0.2), float(113.5), float(0), float(0), float(0), float(0))
	
	#Entry Ambient Sounds		#TODO: Fix object/soundobject spawning
	#stcSvc.spawnObject('object/soundobject/shared_soundobject_jabba_palace_entrance.iff', 'tatooine', building.getCellByCellNumber(1), float(-3.5), float(0.2), float(113.5), float(0), float(0), float(0), float(0))
	
	#ree yees
	stcSvc.spawnObject('object/mobile/shared_dressed_gran_thug_male_01.iff', 'tatooine', building.getCellByCellNumber(1), float(5.8), float(0.2), float(115.7), float(-0.71), float(0), float(0.7), float(0))

	#ephantmon
	stcSvc.spawnObject('object/mobile/shared_ephant_mon.iff', 'tatooine', building.getCellByCellNumber(4), float(-6.1), float(5.8), float(86.1), float(0), float(0), float(0), float(0))

	#porcellus
	stcSvc.spawnObject('object/mobile/shared_dressed_porcellus.iff', 'tatooine', building.getCellByCellNumber(12), float(-44.0), float(3.0), float(63.4), float(0), float(0), float(0), float(0))

	#barada
	stcSvc.spawnObject('object/mobile/shared_barada.iff', 'tatooine', building.getCellByCellNumber(34), float(31.2), float(0.2), float(-1.0), float(-0.17), float(0), float(0.98), float(0))

	#Bib Fortuna
	stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', building.getCellByCellNumber(22), float(-11.1), float(2.0), float(49.5), float(0.71), float(0), float(0.70), float(0))
	
	#Jabba
	stcSvc.spawnObject('object/mobile/shared_jabba_the_hutt.iff', 'tatooine', building.getCellByCellNumber(22), float(-17.0), float(2.8), float(44.9), float(0.71), float(0), float(0.70), float(0))
	
	#oola
	stcSvc.spawnObject('object/mobile/shared_oola.iff', 'tatooine', building.getCellByCellNumber(22), float(-10.3), float(2.0), float(43.9), float(-0.71), float(0), float(0.70), float(0))

	#Ambient Sound
	#stcSvc.spawnObject('object/soundobject/shared_soundobject_jabba_audience_chamber.iff', 'tatooine', building.getCellByCellNumber(22), float(-10.3), float(2.0), float(43.9), float(-0.9), float(0), float(0.42), float(0))

	#Boba Fett
	stcSvc.spawnObject('object/mobile/shared_boba_fett.iff', 'tatooine', building.getCellByCellNumber(22), float(-1.0), float(3.0), float(33.1), float(0), float(0), float(0), float(0))

	#EV9D9
	stcSvc.spawnObject('object/mobile/shared_ev_9d9.iff', 'tatooine', building.getCellByCellNumber(14), float(23.1), float(0.2), float(87.8), float(0), float(0), float(1.0), float(0))
	
	#malakili 
	stcSvc.spawnObject('object/mobile/shared_malakili.iff', 'tatooine', building.getCellByCellNumber(37), float(17.3), float(-11.0), float(43.8), float(0), float(0), float(0), float(0))

	#Jabba Rancor
	stcSvc.spawnObject('object/mobile/shared_rancor_static.iff', 'tatooine', building.getCellByCellNumber(38), float(0), float(-11.0), float(43.8), float(-0.9), float(0), float(0.42), float(0))
	
	#Max Rebo	
	stcSvc.spawnObject('object/mobile/shared_max_rebo.iff', 'tatooine', building.getCellByCellNumber(22), float(-1.4), float(3.0), float(26.9), float(-0.9), float(0), float(0.42), float(0))

	#Music
	#stcSvc.spawnObject('object/soundobject/shared_soundobject_jabba_max_rebo_band.iff', 'tatooine', building.getCellByCellNumber(22), float(-1.4), float(3.0), float(26.9), float(-0.9), float(0), float(0.42), float(0))

	#Droopy Mccool
	stcSvc.spawnObject('object/mobile/shared_droopy_mccool.iff', 'tatooine', building.getCellByCellNumber(22), float(-3.9), float(3.0), float(26.1), float(-0.9), float(0), float(0.42), float(0))
	
	#Sy Snootles
	stcSvc.spawnObject('object/mobile/shared_sy_snootles.iff', 'tatooine', building.getCellByCellNumber(22), float(-1.4), float(3.0), float(29.8),  float(-0.1), float(0), float(0.42), float(0))

	# G5P0
	stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', building.getCellByCellNumber(22), float(-14.3), float(2.0), float(47.4), float(0.71), float(0), float(0.70), float(0))

	#outside Palace
	
	smuggler = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_jabba_henchman.iff', 'tatooine', long(0), float(-5863), float(90), float(-6198.2), float(0.71), float(0), float(0.70), float(0)) #still need to find correct template
	smuggler.setCustomName('Smuggler Pilot')
	smuggler.setOptionsBitmask(256)
	return
	

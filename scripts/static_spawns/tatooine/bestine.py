import sys
# Project SWG:   Bestine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# City hall
	indigoSiyan = stcSvc.spawnObject('object/mobile/shared_dressed_indigo_siyan.iff', 'tatooine', long(926483), float(24.7), float(3.2), float(-30.7), float(-0.06), float(0.99))
	#indigoSiyan.setCustomName2('Indigo Siyan')
	#indigoSiyan.setOptionsBitmask(256)
	
	keanna = stcSvc.spawnObject('object/mobile/shared_dressed_keanna_likyna.iff', 'tatooine', long(926480), float(-18.7), float(3.2), float(20.6), float(-0.97), float(0.2))
	#keanna.setCustomName2('Keanna Li\'kyna')
	#keanna.setOptionsBitmask(256)
	
	oberhaur = stcSvc.spawnObject('object/mobile/shared_space_imperial_tier2_tatooine_oberhaur.iff', 'tatooine', long(926480), float(-21.9), float(3.2), float(26.9), float(0.99), float(0.01))
	#oberhaur.setCustomName2('Commander Oberhaur')
	#oberhaur.setOptionsBitmask(256)
	
	seanTrenwell = stcSvc.spawnObject('object/mobile/shared_dressed_sean_trenwell.iff', 'tatooine', long(926483), float(19.4), float(3.2), float(-36), float(-0.06), float(0.99))
	#seanTrenwell.setCustomName2('Sean Trenwell')
	#seanTrenwell.setOptionsBitmask(256)
	
	talmont = stcSvc.spawnObject('object/mobile/shared_prefect_talmont.iff', 'tatooine', long(926475), float(-1.9), float(3.1), float(-10.3), float(0.99), float(0))
	#talmont.setCustomName2('Prefect Talmont')
	#talmont.setOptionsBitmask(256)
	
	tourAryon = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_tour_aryon.iff', 'tatooine', long(926479), float(-36.8), float(1.3), float(0.3), float(0.8), float(0.59))
	#tourAryon.setCustomName2('Tour Aryon')
	#tourAryon.setOptionsBitmask(256)
	
	victorVisalis = stcSvc.spawnObject('object/mobile/shared_dressed_victor_visalis.iff', 'tatooine', long(926480), float(-26.7), float(3.2), float(20.8), float(0.96), float(0.24))
	#victorVisalis.setCustomName2('Victor Visalis')
	#victorVisalis.setOptionsBitmask(256)
	
	wilhalmSkrim = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_wilhalm_skrim.iff', 'tatooine', long(926482), float(28.9), float(1.3), float(-6.0), float(0.23), float(0.97))
	#wilhalmSkrim.setCustomName2('Wilhalm Skrim')
	#wilhalmSkrim.setOptionsBitmask(256)
	
	
	#Miscellaneous Building Interiors
	akalColzet = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_trainer_space_01.iff', 'tatooine', long(1212359), float(0.7), float(1.8), float(-14), float(0), float(0.99))
	#akalColzet.setCustomName2('lt. Akal Colzet')
	#akalColzet.setOptionsBitmask(256)
	
	fariousGletch = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_farious_gletch.iff', 'tatooine', long(1278989), float(2.0), float(-0.4), float(-5.7), float(-0.15), float(0.98))
	#fariousGletch.setCustomName2('Farious Gletch')
	#fariousGletch.setOptionsBitmask(256)
	
	pfilbeeJhorn = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_pfilbee_jhorn.iff', 'tatooine', long(1279923), float(5.1), float(0.1), float(-3.8), float(-0.7), float(0.71))
	#pfilbeeJhorn.setCustomName2('Pfilbee Jhorn')
	#pfilbeeJhorn.setOptionsBitmask(256)
	
	#Hotel interior
	gilBurtin = stcSvc.spawnObject('object/mobile/shared_dressed_industrialist_trainer_01.iff', 'tatooine', long(1223850), float(20.3), float(1.6), float(12.8), float(0.99), float(0))
	#gilBurtin.setCustomName2('Gil Burtin')
	#gilBurtin.setOptionsBitmask(256)
	
	# Outside
	barak = stcSvc.spawnObject('object/mobile/shared_smuggler_broker_barak.iff', 'tatooine', long(0), float(-1049), float(5.0), float(-3537), float(0.97), float(0.23))
	#barak.setCustomName2('Barak')
	#barak.setOptionsBitmask(256)
	
	barrezz = stcSvc.spawnObject('object/mobile/shared_dressed_dressed_legacy_barrezz.iff', 'tatooine', long(0), float(-1146.8), float(98.0), float(-3892.1), float(0.75), float(0.65))
	#barrezz.setCustomName2('Commander Barrezz')
	#barrezz.setOptionsBitmask(256)
	
	jasha = stcSvc.spawnObject('object/mobile/shared_dressed_bestinejobs_jasha.iff', 'tatooine', long(0), float(-1128), float(98.0), float(-3900), float(-0.42), float(0.90))
	#jasha.setCustomName2('Captain Jasha')
	#jasha.setOptionsBitmask(256)

	dkrn = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_general_m.iff', 'tatooine', long(0), float(-1160), float(5.0), float(-3525), float(0.90), float(-0.41))
	#dkrn.setCustomName2('Commander D\'krn')
	#dkrn.setOptionsBitmask(256)
	
	gunham = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_lieutenant_m.iff', 'tatooine', long(0), float(-1125), float(12.2), float(-3620), float(0.9), float(0.42))
	#gunham.setCustomName2('Commander Gunham')
	#gunham.setOptionsBitmask(256)
	
	kormundThrylle = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_kormund_thrylle.iff', 'tatooine', long(0), float(-1043), float(10.0), float(-3530), float(0.99), float(0.06))
	#kormundThrylle.setCustomName2('Kormund Thrylle')
	#kormundThrylle.setOptionsBitmask(256)
	
	calebKnolar = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_major_cold_m.iff', 'tatooine', long(0), float(-1149), float(98.0), float(-3903), float(0.42), float(0.91))
	#calebKnolar.setCustomName2('Major Caleb Knolar')
	#calebKnolar.setOptionsBitmask(256)
	
	#Junk Dealers
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1287), float(12), float(-3655), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1259), float(12), float(-3673), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1026), float(10), float(-3557), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1260), float(12), float(-3573), float(0), float(1))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1126), float(12), float(-3674), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(-1118), float(12), float(-3687), float(0), float(0))
	return
	

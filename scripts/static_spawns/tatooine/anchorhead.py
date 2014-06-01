import sys
# Project SWG:   Anchorhead:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# Cantina Interior
	borraSetas = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_female_03.iff', 'tatooine', long(1213345), float(9.8), float(0.4), float(-1.2), float(-0.65), float(0.75))
	#borraSetas.setCustomName2('Borra Setas')
	#borraSetas.setOptionsBitmask(256)
		
	# Outside
	aaphKoden = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_aaph_koden.iff', 'tatooine', long(0), float(129), float(5.0), float(-5399), float(-0.67), float(0.73))
	aaphKoden.setCustomName2('Aaph Koden')
	aaphKoden.setOptionsBitmask(256)
	
	Alger = stcSvc.spawnObject('object/mobile/shared_smuggler_fence_alger.iff', 'tatooine', long(0), float(107), float(5.0), float(-5315), float(0.96), float(0.26))
	Alger.setCustomName2('Alger')
	Alger.setOptionsBitmask(256)
	
	carhlaBastra = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_bastra.iff', 'tatooine', long(0), float(128), float(5.0), float(-5428), float(-0.26), float(0.96))
	carhlaBastra.setCustomName2('Carh\'la Bastra')
	carhlaBastra.setOptionsBitmask(256)
	
	cuanTalon = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_cuan.iff', 'tatooine', long(0), float(-161.7), float(65.0), float(-5322.8), float(0), float(0))
	cuanTalon.setCustomName2('Cuan Talon')
	cuanTalon.setOptionsBitmask(256)
	
	dromaOrdo = stcSvc.spawnObject('object/mobile/shared_dressed_anchorjobs_ordo.iff', 'tatooine', long(0), float(110), float(52.0), float(-5431), float(0.36), float(0.93))
	dromaOrdo.setCustomName2('Droma Ordo')
	dromaOrdo.setOptionsBitmask(256)
	
	Sorna = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_tosche_sorna.iff', 'tatooine', long(0), float(-135), float(52.0), float(-5331), float(0.36), float(0.93))
	Sorna.setCustomName2('Sorna')
	Sorna.setOptionsBitmask(256)
	
	#Junk Dealer
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(110), float(52), float(-5428), float(0.71), float(0.71))

	return
	

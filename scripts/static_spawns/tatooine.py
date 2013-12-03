import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
		
	stcSvc.spawnObject('object/mobile/shared_lifeday_saun_dann.iff', 'tatooine', long(0), float(-5037.00), float(75), float(-6561), float(-0.75), float(0)) # Life Day
	stcSvc.spawnObject('object/tangible/holiday/life_day/shared_main_lifeday_tree.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6541.00), float(0.999132931232), float(-0.0416347384453)) # Lifeday Tree
	stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan.iff', 'tatooine', long(0), float(-5046.00), float(75), float(-6560), float(-0.75), float(0)) # LD Figrin
	bandtat1 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5040.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 1
	bandtat1.setCustomName2('Doikk Nats')
	bandtat2 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(-0.75), float(0)) # LD band 2
	bandtat2.setCustomName2('Tech Mor')
	bandtat3 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5048.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 3
	bandtat3.setCustomName2('Nalan Cheel')
	bandtat4 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(-0.75), float(0)) # LD band 4
	bandtat4.setCustomName2('Sunil Eide')
	stcSvc.spawnObject('object/tangible/instrument/shared_nalargon.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(0.999132931232), float(-0.0416347384453)) # Drums
	stcSvc.spawnObject('object/tangible/instrument/shared_ommni_box.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(0.999132931232), float(-0.0416347384453)) # Box
	stcSvc.spawnObject('object/tangible/holiday/life_day/shared_life_day_tree.iff', 'tatooine', long(0), float(-5083.00), float(75), float(-6619.00), float(0.999132931232), float(-0.0416347384453)) # small tree

	return
	
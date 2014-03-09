import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
# Theed Spawns
	# OutSide
	loruna = stcSvc.spawnObject('object/mobile/shared_loruna_scathe.iff', 'naboo', long(0), float(-5149), float(6), float(4286), float(0.34), float(0.93))
	loruna.setCustomName2('Loruna Scathe')
	
	pooja = stcSvc.spawnObject('object/mobile/shared_dressed_pooja_naberrie.iff', 'naboo', long(0), float(-5479.1), float(14), float(4467.3), float(-0.96), float(0.25))
	pooja.setCustomName2('Pooja Naberrie')
	
	vaikannaSilverlight = stcSvc.spawnObject('object/mobile/shared_dressed_herald_naboo_01.iff', 'naboo', long(0), float(-5484), float(10), float(4424), float(-0.93), float(0.34))
	vaikannaSilverlight.setCustomName2('Vaik\'anna Silverlight')
	
	hannaSkiyah = stcSvc.spawnObject('object/mobile/shared_dressed_herald_noble_twk_female_01.iff', 'naboo', long(0), float(-5480), float(-0.5), float(4398), float(0.44), float(0.89))
	hannaSkiyah.setCustomName2('Hanna S\'kiyah')
# Keren Spawns
	# Cantina interior
	lergoBrazee = stcSvc.spawnObject('object/mobile/shared_dressed_lergo_brazee.iff', 'naboo', long(5), float(2.8), float(-0.9), float(-5.3), float(0.25), float(0.96))
	lergoBrazee.setCustomName2('Lergo Brazee')
	
	# Starport interior
	gavynSykes = stcSvc.spawnObject('object/mobile/shared_dressed_gavyn_sykes.iff', 'naboo', long(2125382), float(9.3), float(0.6), float(66.6), float(-0.90), float(0.42))
	gavynSykes.setCustomName2('Capt. Gavyn Sykes')
	
	#Miscellaneous Building Interiors
	kritusMorven = stcSvc.spawnObject('object/mobile/shared_dressed_kritus_morven.iff', 'naboo', long(1685077), float(-3.9), float(-4.9), float(-7.5), float(-0.57), float(0.81))
	kritusMorven.setCustomName2('Kritus Morven')
	
	Raev = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_clone_relics_major_raev.iff', 'naboo', long(1393881), float(-10), float(1.7), float(-9.5), float(0.38), float(0.92))
	Raev.setCustomName2('Major Raev')
	
	# Outside
	brennis = stcSvc.spawnObject('object/mobile/shared_dressed_brennis_doore.iff', 'naboo', long(0), float(1740), float(12), float(2657), float(-0.99), float(0))
	brennis.setCustomName2('Brennis Doore')
	
	demitri = stcSvc.spawnObject('object/mobile/shared_dressed_herald_servant_naboo_human_male.iff', 'naboo', long(0), float(1673), float(12), float(2582), float(0.31), float(0.94))
	demitri.setCustomName2('Demitri Firewatcher')
	
	ogden = stcSvc.spawnObject('object/mobile/shared_smuggler_broker_ogden.iff', 'naboo', long(0), float(1907), float(12), float(2352), float(0.97), float(0.22))
	ogden.setCustomName2('Ogden')
	
# Kaadara Spawns
	#Miscellaneous Building Interiors
	barnSinkko = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_trainer_space_03.iff', 'naboo', long(1741494), float(-1.3), float(1.8), float(-14), float(-0.34), float(0.93))
	barnSinkko.setCustomName2('Lt. Barn Sinkko')
	
	# OutSide
	dakk = stcSvc.spawnObject('object/mobile/shared_dressed_acklay_dakk.iff', 'naboo', long(0), float(5167), float(-192), float(6674), float(-0.99), float(0.04))
	dakk.setCustomName2('Dakk')
	
	panaka = stcSvc.spawnObject('object/mobile/shared_panaka.iff', 'naboo', long(0), float(5196.6), float(-192), float(6712.8), float(0.99), float(0.04))
	
# Dejaa Peak Spawns

	# OutSide
	arvenWendik = stcSvc.spawnObject('object/mobile/shared_dressed_arven_wendik.iff', 'naboo', long(0), float(4710), float(330), float(-1432), float(-0.70), float(0.70))
	arvenWendik.setCustomName2('Arven Wendik')
	
	damaliaKorde = stcSvc.spawnObject('object/mobile/shared_dressed_damalia_korde.iff', 'naboo', long(0), float(5139), float(346.5), float(-1530.9), float(0.34), float(0.93))
	damaliaKorde.setCustomName2('Damalia Korde')
	
	hermanPate = stcSvc.spawnObject('object/mobile/shared_naboo_herman_pate.iff', 'naboo', long(0), float(4871.1), float(360.6), float(-1442.1), float(0), float(1))
	hermanPate.setCustomName2('Herman Pate')
	
	kimaNazith = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_03.iff', 'naboo', long(0), float(4972), float(360), float(-1583), float(-0.95), float(0.28))
	kimaNazith.setCustomName2('Kima Nazith')
	
	kippyMartin = stcSvc.spawnObject('object/mobile/shared_naboo_kippy_martin.iff', 'naboo', long(0), float(4324), float(295.1), float(-1250), float(0.55), float(0.82))
	kippyMartin.setCustomName2('Kippy Martin')
	
	ruab = stcSvc.spawnObject('object/mobile/shared_smuggler_fence_ruab.iff', 'naboo', long(0), float(4998), float(360), float(-1485), float(-0.34), float(0.93))
	ruab.setCustomName2('Ruab')
	
	tanoaVills = stcSvc.spawnObject('object/mobile/shared_naboo_tanoa_vills.iff', 'naboo', long(0), float(4740), float(330), float(-1322), float(-0.96), float(0.27))
	tanoaVills.setCustomName2('Tanoa Vills')
	
	vanceGroten = stcSvc.spawnObject('object/mobile/shared_naboo_vance_groten.iff', 'naboo', long(0), float(5144), float(346.5), float(-1530), float(-0.17), float(0.98))
	vanceGroten.setCustomName2('Vance Groten')
	
	walker = stcSvc.spawnObject('object/mobile/shared_naboo_walker_luskeske.iff', 'naboo', long(0), float(4994), float(360), float(-1506), float(-0.97), float(0.21))
	walker.setCustomName2('Walker Luskeske')
	
	zanier = stcSvc.spawnObject('object/mobile/shared_naboo_professor_hudmasse.iff', 'naboo', long(0), float(4690), float(330.2), float(-1391), float(0.52), float(0.84))
	zanier.setCustomName2('Professor Zanier Hudmasse')
	
# Moenia Spawns
	# Cantina interior
	borvo = stcSvc.spawnObject('object/mobile/shared_borvo.iff', 'naboo', long(121), float(-29), float(-0.5), float(7.9), float(0.44), float(0.89))
	borvo.setCustomName2('Borvo The Hutt')
	
	# OutSide
	c3tc = stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_silver.iff', 'naboo', long(0), float(4723), float(3.8), float(-4935), float(-0.99), float(0))
	c3tc.setCustomName2('C-3TC')

	dilvin = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_male_03.iff', 'naboo', long(0), float(4893), float(3.8), float(-4998), float(0.96), float(0.27))
	dilvin.setCustomName2('Dilvin Lormurojo')
	
	ebenn = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_male_02.iff', 'naboo', long(0), float(4869), float(3.8), float(-4875), float(-0.70), float(0.70))
	ebenn.setCustomName2('Ebenn Q3 Baobab')

	v3fx = stcSvc.spawnObject('object/mobile/shared_space_rebel_tier1_naboo_v3fx.iff', 'naboo', long(0), float(4764.2), float(6.1), float(-4795), float(0.99), float(0.08))
	v3fx.setCustomName2('V3-FX')
	
	roninLightrunner = stcSvc.spawnObject('object/mobile/shared_dressed_herald_naboo_02.iff', 'naboo', long(0), float(4810), float(6.3), float(-4977), float(0), float(1))
	roninLightrunner.setCustomName2('Ronin Lightrunner')
	
	dagorel = stcSvc.spawnObject('object/mobile/shared_dressed_gendra.iff', 'naboo', long(0), float(4471), float(4), float(-4714), float(-0.64), float(0.76))
	dagorel.setCustomName2('Dagorel')
	
# Emperor's Retreat interior
	kaja = stcSvc.spawnObject('object/mobile/shared_kaja_orzee.iff', 'naboo', long(1418872), float(1.9), float(0.2), float(-13.4), float(0), float(1))
	kaja.setCustomName2('Kaja Or\'Zee')
	
	ltVelso = stcSvc.spawnObject('object/mobile/shared_dressed_corvette_imperial_velso.iff', 'naboo', long(1418874), float(23.6), float(0.2), float(-19), float(-0.93), float(0.34))
	ltVelso.setCustomName2('Lt. Velso')
	
	royalGuard1 = stcSvc.spawnObject('object/mobile/shared_royal_guard.iff', 'naboo', long(1418874), float(17), float(0.2), float(-31), float(0), float(1))
	royalGuard2 = stcSvc.spawnObject('object/mobile/shared_royal_guard.iff', 'naboo', long(1418874), float(9), float(0.2), float(-31), float(0), float(1))
		
	vrke = stcSvc.spawnObject('object/mobile/shared_space_imperial_tier3_naboo_vrke.iff', 'naboo', long(1418875), float(24.3), float(0.2), float(-39.6), float(0), float(1))
	vrke.setCustomName2('Inquisitor Vrke')
	
	loamRedge = stcSvc.spawnObject('object/mobile/shared_loam_redge.iff', 'naboo', long(1418875), float(18.6), float(0.2), float(-42), float(0.34), float(0.93))
	loamRedge.setCustomName2('Loam Redge')
	
	hethrir = stcSvc.spawnObject('object/mobile/shared_lord_hethrir.iff', 'naboo', long(1418876), float(4.6), float(0.2), float(-41.3), float(0), float(1))
	hethrir.setCustomName2('Lord Hethrir')
		
	jaceYiaso = stcSvc.spawnObject('object/mobile/shared_space_imperial_tier4_naboo_inquisitor.iff', 'naboo', long(1418884), float(-44), float(0.2), float(-31.9), float(0), float(1))
	jaceYiaso.setCustomName2('Grand Inquisitor Ja\'ce Yiaso')
	
	vader = stcSvc.spawnObject('object/mobile/shared_darth_vader.iff', 'naboo', long(1418884), float(-57), float(0.2), float(-24), float(0.70), float(0.70))
	
	# Outside Emperor's Retreat
	veers = stcSvc.spawnObject('object/mobile/naboo_npc/shared_veers.iff', 'naboo', long(0), float(2368), float(291), float(-3921), float(0.64), float(0.76))
	veers.setCustomName2('Colonel Veers')
	
	thrawn = stcSvc.spawnObject('object/mobile/naboo_npc/shared_thrawn.iff', 'naboo', long(0), float(2369), float(291), float(-3922), float(0.34), float(0.93))
	thrawn.setCustomName2('Captain Thrawn')
	
	fazoll = stcSvc.spawnObject('object/mobile/shared_space_imperial_tier2_naboo.iff', 'naboo', long(0), float(2444), float(292), float(-3896), float(-0.996194), float(0.087155))
	fazoll.setCustomName2('Inquisitor Fa\'Zoll')
	
	return
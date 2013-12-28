
import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
#Nym's Stronghold -Fingies

	#Outside
	
	nymtech = stcSvc.spawnObject('object/mobile/shared_dressed_nym_technician_2.iff', 'lok', long(0), float(372.87), float(11.99), float(4984.83), float(0.97), float(-0.21))
	nymtech.setCustomName2('Nym\'s Fuel Technician')
	disgrunt1 = stcSvc.spawnObject('object/mobile/shared_nym_themepark_d_townsperson_lead.iff', 'lok', long(0), float(473.28), float(23.00), float(4945.03), float(0.043), float(0.99))
	disgrunt1.setCustomName2('a disgruntled townsperson')
	guard1 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_nikto_m.iff', 'lok', long(0), float(472.82), float(22.57), float(4920.00), float(0), float(0))
	guard1.setCustomName2('Nym\'s Guard')
	guard2 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_hum_m.iff', 'lok', long(0), float(480.74), float(22.64), float(4920.02), float(0), float(0))
	guard2.setCustomName2('Nym\'s Guard')
	
	#Nym's Palace - Interior
	
	brawl1 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_brawler_hum_f.iff', 'lok', long(6595511), float(5.7), float(1.3), float(-6.1), float(0.90), float(-0.42))
	brawl1.setCustomName2('Nym\'s Brawler')
	surv1 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_surveyer_rod_m.iff', 'lok', long(6595511), float(4.5), float(1.3), float(-4.1), float(0.078), float(-0.99))
	surv1.setCustomName2('Nym\'s Surveyor')
	bodyg1 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_bodyguard_hum_m.iff', 'lok', long(6595511), float(7.8), float(1.3), float(-6.8), float(0.782), float(0.622))
	bodyg1.setCustomName2('Nym\'s Bodyguard')
	mako = stcSvc.spawnObject('object/mobile/shared_nym_themepark_mako_ghast.iff', 'lok', long(6595511), float(5.5), float(4.1), float(-13.9), float(0.309), float(0.951))
	mako.setCustomName2('Mako Ghast')
	bodyg2 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_bodyguard_rod_m.iff', 'lok', long(6595511), float(0.3), float(1.3), float(3.2), float(0.707), float(0.707))
	bodyg2.setCustomName2('Nym\'s Bodyguard')
	bodyg3 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_bodyguard_rod_m.iff', 'lok', long(6595511), float(-1.6), float(1.3), float(3.0), float(0.707), float(-0.707))
	bodyg3.setCustomName2('Nym\'s Bodyguard')
	surv2 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_surveyer_rod_m.iff', 'lok', long(6595511), float(-9.1), float(1.3), float(-5.9), float(1), float(0))
	surv2.setCustomName2('Nym\'s Surveyor')
	brawl2 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_brawler_tran_m.iff', 'lok', long(6595511), float(-9.1), float(1.3), float(-3.1), float(0.0087), float(0.999))
	brawl2.setCustomName2('Nym\'s Brawler')
	guard3 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_hum_m.iff', 'lok', long(6595511), float(-19.7), float(2.3), float(-17.6), float(0.999), float(0.0087))
	guard3.setCustomName2('Nym\'s Guard')
	creep1 = stcSvc.spawnObject('object/mobile/shared_slicer_kelson_sharphorn.iff', 'lok', long(6595511), float(-4.2), float(2.3), float(7.6), float(0.0174), float(0.9998))
	creep1.setCustomName2('Kelson Sharphorn')
	guard4 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_hum_m.iff', 'lok', long(6595511), float(24.8), float(2.3), float(7.9), float(0.0174), float(0.9998))
	guard4.setCustomName2('Nym\'s Guard')
	guard5 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_hum_m.iff', 'lok', long(6595511), float(3.7), float(4.1), float(-9.5), float(0.9998), float(0.0174))
	guard5.setCustomName2('Nym\'s Guard')
	guard6 = stcSvc.spawnObject('object/mobile/shared_dressed_nym_guard_weak_rod_m.iff', 'lok', long(6595511), float(-3.7), float(4.1), float(-9.5), float(1), float(0))
	guard6.setCustomName2('Nym\'s Guard')
	
	

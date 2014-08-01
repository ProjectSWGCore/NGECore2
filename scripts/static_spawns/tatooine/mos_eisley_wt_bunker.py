import sys
# Project SWG:   WT Bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State
from resources.datatables import Posture

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	

	#Outside
	wt_guard = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3749.7), float(16.8), float(-4200.5), float(0), float(0), float(0), float(0), 45)
	wt_guard1 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3761.2), float(16.1), float(-4188.3), float(0), float(0), float(0), float(0), 45)
	wt_guard2 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3740.9), float(15.6), float(-4188.3), float(0), float(0), float(0), float(0), 45)
	wt_guard3 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3731.1), float(16.0), float(-4184.3), float(0), float(0), float(0), float(0), 45)
	wt_guard4 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3724.7), float(16.2), float(-4184.3), float(0), float(0), float(0), float(0), 45)
	wt_guard5 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3709.9), float(17.1), float(-4192.1), float(0), float(0), float(0), float(0), 45)
	wt_guard6 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3718.3), float(16.2), float(-4193.1), float(0), float(0), float(0), float(0), 45)
	wt_guard7 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', long(0), float(3707), float(17.7), float(-4187.6), float(0), float(0), float(0), float(0), 45)
	
	
	#inside
	wt_bunker = core.objectService.getObject(long(-466404036409557111))
	
	#1st room
	wt_reciptionist = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_wh_receptionist.iff', 'tatooine', wt_bunker.getCellByCellNumber(5), float(-0.3), float(-12), float(34.4), float(0), float(0), float(0), float(0))
	wt_reciptionist.setOptionsBitmask(256)
	wt_reciptionist.setCustomName('White Thranta Reciptionist')
	
	wt_guard8 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', wt_bunker.getCellByCellNumber(5), float(8.9), float(-12), float(50.8), float(0), float(0), float(0), float(0), 45)
	wt_guard9 = stcSvc.spawnObject('white_thranta_security_guard', 'tatooine', wt_bunker.getCellByCellNumber(5), float(-1.7), float(-12), float(51.1), float(0), float(0), float(0), float(0), 45)
	
	wt_technican = stcSvc.spawnObject('white_thranta_technican', 'tatooine', wt_bunker.getCellByCellNumber(5), float(6.9), float(-12), float(39.5), float(0), float(0), float(0), float(0), 45)
	wt_technican.setOptionsBitmask(128)
	
	wt_technican1 = stcSvc.spawnObject('white_thranta_technican', 'tatooine', wt_bunker.getCellByCellNumber(10), float(8.5), float(-16), float(133.7), float(0), float(0), float(0), float(0), 45)
	wt_technican2 = stcSvc.spawnObject('white_thranta_technican', 'tatooine', wt_bunker.getCellByCellNumber(10), float(-2.0), float(-16), float(133.7), float(0), float(0), float(0), float(0), 45)
	wt_technican3 = stcSvc.spawnObject('white_thranta_technican', 'tatooine', wt_bunker.getCellByCellNumber(10), float(6.6), float(-16), float(100.5), float(0), float(0), float(0), float(0), 45)
	wt_technican4 = stcSvc.spawnObject('white_thranta_technican', 'tatooine', wt_bunker.getCellByCellNumber(26), float(-74.9), float(-28), float(56.9), float(0), float(0), float(0), float(0), 45)
	
	wt_officer = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(7), float(11), float(-16), float(79), float(0), float(0), float(0), float(0), 45)
	wt_officer1 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(18), float(-7.8), float(-12), float(91.3), float(0), float(0), float(0), float(0), 45)
	wt_officer2 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(20), float(-34.5), float(-12), float(93.1), float(0), float(0), float(0), float(0), 45)
	wt_officer3 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(20), float(-33.1), float(-12), float(114.2), float(0), float(0), float(0), float(0), 45)
	wt_officer4 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(20), float(-36.7), float(-12), float(119.7), float(0), float(0), float(0), float(0), 45)
	wt_officer5 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(10), float(25.1), float(-16), float(119.8), float(0), float(0), float(0), float(0), 45)
	wt_officer6 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(10), float(11.9), float(-16), float(121.8), float(0), float(0), float(0), float(0), 45)
	wt_officer7 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(29), float(-139.4), float(-44), float(79.4), float(0), float(0), float(0), float(0), 45)
	wt_officer8 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(29), float(-153), float(-44), float(90.6), float(0), float(0), float(0), float(0), 45)
	wt_officer9 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(26), float(-76.2), float(-28), float(74), float(0), float(0), float(0), float(0), 45)
	wt_officer10 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(26), float(-75.7), float(-28), float(69.4), float(0), float(0), float(0), float(0), 45)
	wt_officer11 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(32), float(-90.5), float(-44), float(-20.4), float(0), float(0), float(0), float(0), 45)
	wt_officer12 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(32), float(-59.9), float(-44), float(4.2), float(0), float(0), float(0), float(0), 45)
	wt_officer13 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(32), float(-86.2), float(-44), float(-9.3), float(0), float(0), float(0), float(0), 45)
	wt_officer14 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(17), float(-77), float(-28), float(95.6), float(0), float(0), float(0), float(0), 45)
	wt_officer15 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(17), float(-78.2), float(-28), float(126.4), float(0), float(0), float(0), float(0), 45)
	wt_officer16 = stcSvc.spawnObject('white_thranta_security_officer', 'tatooine', wt_bunker.getCellByCellNumber(17), float(-96.6), float(-28), float(110.6), float(0), float(0), float(0), float(0), 45)
	
	wt_specialist = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(7), float(23.7), float(-16), float(79), float(0), float(0), float(0), float(0), 45)
	wt_specialist1 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(18), float(-14.3), float(-12), float(86.8), float(0), float(0), float(0), float(0), 45)
	wt_specialist2 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(18), float(-16.4), float(-12), float(96.6), float(0), float(0), float(0), float(0), 45)
	wt_specialist3 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(10), float(-9.7), float(-16), float(115.5), float(0), float(0), float(0), float(0), 45)
	wt_specialist4 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(10), float(-11), float(-16), float(126.5), float(0), float(0), float(0), float(0), 45)
	wt_specialist5 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(10), float(-2.2), float(-16), float(104.8), float(0), float(0), float(0), float(0), 45)
	wt_specialist6 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(26), float(-76.1), float(-28), float(55.2), float(0), float(0), float(0), float(0), 45)
	wt_specialist7 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(32), float(-93.4), float(-44), float(-8.8), float(0), float(0), float(0), float(0), 45)
	wt_specialist8 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(12), float(-23.3), float(-12), float(162.9), float(0), float(0), float(0), float(0), 45)
	wt_specialist9 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(12), float(-33.2), float(-12), float(167.9), float(0), float(0), float(0), float(0), 45)
	wt_specialist10 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(12), float(-31.4), float(-12), float(171.2), float(0), float(0), float(0), float(0), 45)
	wt_specialist11 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(12), float(-27), float(-12), float(144), float(0), float(0), float(0), float(0), 45)
	wt_specialist12 = stcSvc.spawnObject('white_thranta_specialist', 'tatooine', wt_bunker.getCellByCellNumber(12), float(-33.3), float(-12), float(151.1), float(0), float(0), float(0), float(0), 45)

	wt_manager = stcSvc.spawnObject('white_thranta_manager', 'tatooine', wt_bunker.getCellByCellNumber(20), float(-40.2), float(-12), float(87.5), float(0), float(0), float(0), float(0), 45)
		
	wt_elite = stcSvc.spawnObject('white_thranta_security_elite', 'tatooine', wt_bunker.getCellByCellNumber(29), float(-137.8), float(-44), float(74.1), float(0), float(0), float(0), float(0), 45)
	wt_elite1 = stcSvc.spawnObject('white_thranta_security_elite', 'tatooine', wt_bunker.getCellByCellNumber(29), float(-159.3), float(-44), float(85.1), float(0), float(0), float(0), float(0), 45)

	prisoner = stcSvc.spawnObject('object/mobile/shared_bothan_female.iff', 'tatooine', wt_bunker.getCellByCellNumber(21), float(-40.5), float(-12), float(130.8), float(-0.700), float(0), float(0.713), float(0))
	prisoner.setCustomName('Carkufluv Reoslav\'Kre')
	prisoner.setOptionsBitmask(256)
	
	prisoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_rodian_female_01.iff', 'tatooine', wt_bunker.getCellByCellNumber(21), float(-43), float(-12), float(133.9), float(0.737), float(0), float(-0.676), float(0))
	prisoner1.setCustomName('Basse Crestinglighter')
	prisoner1.setOptionsBitmask(256)
	prisoner1.setPosture(Posture.KnockedDown)
	
	prisoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_fancy_human_female.iff', 'tatooine', wt_bunker.getCellByCellNumber(21), float(-33.3), float(-12), float(137.9), float(0), float(0), float(1), float(0))
	prisoner2.setCustomName('Setweoko I\'tvo')
	prisoner2.setOptionsBitmask(256)
	prisoner2.setPosture(Posture.KnockedDown)
	
	prisoner3 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_trandoshan_female_01.iff', 'tatooine', wt_bunker.getCellByCellNumber(21), float(-30), float(-12), float(133.1), float(0.713), float(0), float(0.701), float(0))
	prisoner3.setCustomName('Itzoosko')
	prisoner3.setOptionsBitmask(256)
	prisoner3.setPosture(Posture.KnockedDown)
	
	prisoner4 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_twilek_female_01.iff', 'tatooine', wt_bunker.getCellByCellNumber(21), float(-36), float(-12), float(129.9), float(0.994), float(0), float(0.105), float(0))
	prisoner4.setCustomName('Ogavi Stibi')
	prisoner4.setOptionsBitmask(256)
	
	durr = stcSvc.spawnObject('durr_rodak', 'tatooine', wt_bunker.getCellByCellNumber(29), float(-153.7), float(-44), float(97.8), float(0), float(0), float(0), float(0), 45)
	
	brok = stcSvc.spawnObject('brok_ziamzun', 'tatooine', wt_bunker.getCellByCellNumber(32), float(-58.1), float(-44), float(-7.3), float(0), float(0), float(0), float(0), 45)
	return
	
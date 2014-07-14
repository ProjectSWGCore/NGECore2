import sys
# Project SWG:   Mos Eisley:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO:  Add NPCs sitting at Cantina tables with SittingOnChair bitmask.
	
	#Cantina Interior
	building = core.objectService.getObject(long(1082874)) 
	wuher = stcSvc.spawnObject('object/mobile/shared_wuher.iff', 'tatooine', building.getCellByCellNumber(3), float(8.6), float(-0.9), float(0.4), float(0), float(0.71), float(0), float(0.71)) 
	wuher.setCustomName('Wuher')
	wuher.setOptionsBitmask(256)
	
	chadraFanFemale = stcSvc.spawnObject('object/mobile/shared_chadra_fan_female.iff', 'tatooine', building.getCellByCellNumber(3), float(10.6), float(-0.9), float(-1.5), float(0), float(0.42), float(0), float(0.90))
	chadraFanFemale.setCustomName('a Chadra Fan Female')
	chadraFanFemale.setOptionsBitmask(256)
	
	chadraFanMale = stcSvc.spawnObject('object/mobile/shared_chadra_fan_male.iff', 'tatooine', building.getCellByCellNumber(3), float(10.7), float(-0.9), float(-0.3), float(0), float(0.64), float(0), float(0.77))
	chadraFanMale.setCustomName('a Chadra Fan Male')
	chadraFanMale.setOptionsBitmask(256)
	
	muftak = stcSvc.spawnObject('object/mobile/shared_muftak.iff', 'tatooine', building.getCellByCellNumber(3), float(20.3), float(-0.9), float(4.9), float(0), float(0.82), float(0), float(0.57)) 
	muftak.setCustomName('Muftak')
	muftak.setOptionsBitmask(256)
	
	chissMale1 = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_etyyy_chiss_poacher_01.iff', 'tatooine', building.getCellByCellNumber(3), float(1.7), float(-0.9), float(-5.0), float(0), float(0.71), float(0), float(0.71)) 
	chissMale1.setCustomName('a Chiss Male')
	chissMale1.setOptionsBitmask(256)
	
	chissMale2 = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_etyyy_chiss_poacher_02.iff', 'tatooine', building.getCellByCellNumber(3), float(3.4), float(-0.9), float(-4.8), float(0), float(0), float(0), float(0)) 
	chissMale2.setCustomName('a Chiss Male')
	chissMale2.setOptionsBitmask(256)
	
	cantinaStorm = stcSvc.spawnObject('object/mobile/shared_stormtrooper.iff', 'tatooine', building.getCellByCellNumber(3), float(2.9), float(-0.9), float(-6.5), float(0), float(0.71), float(0), float(0.71)) 
	cantinaStorm.setCustomName('a Stormtrooper')
	cantinaStorm.setOptionsBitmask(256)
	
	cantinaStormL = stcSvc.spawnObject('object/mobile/shared_stormtrooper_groupleader.iff', 'tatooine', building.getCellByCellNumber(3), float(3.6), float(-0.9), float(-7.9), float(0), float(0.71), float(0), float(0.71)) 
	cantinaStormL.setCustomName('a Stormtrooper Squad Leader')
	cantinaStormL.setOptionsBitmask(256)
			
	businessman1 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(11.0), float(-0.9), float(2.1), float(0), float(0.38), float(0), float(-0.92)) 
	businessman1.setCustomName('a Businessman')
	businessman1.setOptionsBitmask(256)
	
	commoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(10.3), float(-0.9), float(2.7), float(0), float(0.82), float(0), float(0.57)) 
	commoner1.setCustomName('a Commoner')
	commoner1.setOptionsBitmask(256)
	
	entertainer1 = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_female_01.iff', 'tatooine', building.getCellByCellNumber(3), float(9.4), float(-0.9), float(3.9), float(0), float(0.38), float(0), float(-0.92)) 
	entertainer1.setCustomName('an Entertainer')
	entertainer1.setOptionsBitmask(256)
	
	noble1 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_trandoshan_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(8.6), float(-0.9), float(4.8), float(0), float(0.82), float(0), float(0.57)) 
	noble1.setCustomName('a Noble')
	noble1.setOptionsBitmask(256)
	
	commoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_female_01.iff', 'tatooine', building.getCellByCellNumber(3), float(4.1), float(-0.9), float(5.7), float(0), float(1), float(0), float(0)) 
	commoner2.setCustomName('a Commoner')
	commoner2.setOptionsBitmask(256)
	
	commoner3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_female_01.iff', 'tatooine', building.getCellByCellNumber(3), float(3.1), float(-0.9), float(5.9), float(0), float(1), float(0), float(0)) 
	commoner3.setCustomName('a Commoner')
	commoner3.setOptionsBitmask(256)
	
	commoner4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(1.7), float(-0.9), float(6.0), float(0), float(1), float(0), float(0)) 
	commoner4.setCustomName('a Commoner')
	commoner4.setOptionsBitmask(256)
	
	commoner5 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(-0.4), float(-0.9), float(5.9), float(0), float(1), float(0), float(0)) 
	commoner5.setCustomName('a Commoner')
	commoner5.setOptionsBitmask(256)
	
	commoner6 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_05.iff', 'tatooine', building.getCellByCellNumber(3), float(16.0), float(-0.9), float(4.1), float(0), float(0), float(0), float(0)) 
	commoner6.setCustomName('a Commoner')
	commoner6.setOptionsBitmask(256)
	
	commoner7 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_zabrak_male_01.iff', 'tatooine', building.getCellByCellNumber(3), float(8.8), float(-0.9), float(-6.0), float(0), float(0.98), float(0), float(-0.22)) 
	commoner7.setCustomName('a Patron')
	commoner7.setOptionsBitmask(256)
	
	commoner8 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_08.iff', 'tatooine', building.getCellByCellNumber(3), float(6.8), float(-0.9), float(-6.5), float(0), float(0.98), float(0), float(-0.22)) 
	commoner8.setCustomName('a Patron')
	commoner8.setOptionsBitmask(256)
	
	commoner9 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_09.iff', 'tatooine', building.getCellByCellNumber(3), float(1.1), float(-0.9), float(-7.7), float(0), float(0.42), float(0), float(0.91)) 
	commoner9.setCustomName('a Commoner')
	commoner9.setOptionsBitmask(256)
	
	commoner10 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff', 'tatooine', building.getCellByCellNumber(3), float(2.1), float(-0.9), float(-8.4), float(0), float(0.42), float(0), float(0.91)) 
	commoner10.setCustomName('a Commoner')
	commoner10.setOptionsBitmask(256)
	
	anetiaKahryn = stcSvc.spawnObject('object/mobile/shared_dressed_twk_entertainer.iff', 'tatooine', building.getCellByCellNumber(4), float(19.8), float(-0.9), float(-21), float(0), float(-0.03), float(0), float(0.99)) 
	anetiaKahryn.setCustomName('Anetia Kah\'ryn')
	anetiaKahryn.setOptionsBitmask(256)
	
	figrinDan = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_figrin_dan.iff', 'tatooine', building.getCellByCellNumber(6), float(3.7), float(-0.9), float(-14.4), float(0), float(0.42), float(0), float(0.91)) 
	figrinDan.setCustomName('Figrin D\'an')
	figrinDan.setOptionsBitmask(256)
	
	techMor = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', building.getCellByCellNumber(6), float(4.0), float(-0.9), float(-17.0), float(0), float(0.42), float(0), float(0.91)) 
	techMor.setCustomName('Tech M\'or')
	techMor.setOptionsBitmask(256)
	
	doikkNats = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', building.getCellByCellNumber(6), float(2.2), float(-0.9), float(-16.4), float(0), float(0.42), float(0), float(0.91)) 
	doikkNats.setCustomName('Doikk Na\'ts')
	doikkNats.setOptionsBitmask(256)
	
	tednDahai = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', building.getCellByCellNumber(6), float(1.3), float(-0.9), float(-15.2), float(0), float(0.42), float(0), float(0.91)) 
	tednDahai.setCustomName('Tedn Dahai')
	tednDahai.setOptionsBitmask(256)
	
	nalanCheel = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', building.getCellByCellNumber(6), float(0.5), float(-0.9), float(-17.1), float(0), float(0.42), float(0), float(0.91)) 
	nalanCheel.setCustomName('Nalan Cheel')
	nalanCheel.setOptionsBitmask(256)
	
	dravis = stcSvc.spawnObject('object/mobile/shared_space_privateer_tier1_tatooine.iff', 'tatooine', building.getCellByCellNumber(12), float(-21.7), float(-0.9), float(25.5), float(0), float(0.99), float(0), float(0.03)) 
	#dravis.setCustomName2('Dravis')
	#dravis.setOptionsBitmask(256)
	
	talonKarrde = stcSvc.spawnObject('object/mobile/shared_dressed_talon_karrde.iff', 'tatooine', building.getCellByCellNumber(13), float(-25.7), float(-0.5), float(8.8), float(0), float(0.21), float(0), float(0.97)) 
	talonKarrde.setCustomName('Talon Karrde')
	talonKarrde.setOptionsBitmask(256)
	
	#Starport Interior
	
	
	#City Hall Interior
	
	#Lucky Despot Interior    TODO:  Check Cell IDs for Spawned NPCS inside once Lucky Despot is spawning properly again.
	#building = core.objectService.getObject(long(-466404040703447347)) 
	#stcSvc.spawnObject('object/mobile/shared_han_solo.iff', 'tatooine', building.getCellByCellNumber(9), float(32.3), float(7.0), float(1.6), float(0), float(0), float(0), float(0)) 
	
	# Disabled for now. She seems to be the cause for quite a few crashes (but not the only reason for the crashes)
	#valarian = stcSvc.spawnObject('object/mobile/shared_lady_valarian.iff', 'tatooine', building.getCellByCellNumber(4), float(-21.5), float(9.0), float(0.5), float(0), float(0.70), float(0), float(-0.71)) 
	#valarian.setCustomName('Lady Valarian')
	#valarian.setOptionsBitmask(256)
	
	#Medical Center Interior
	
	#Theater Interior
	
	#Miscellaneous Building Interiors

	#Outside
	businessman2 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', long(0), float(3663.3), float(4.0), float(-4738.6), float(0), float(0)) 
	businessman2.setCustomName('a Businessman')
	businessman2.setOptionsBitmask(256)
	
	noble2 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91)) 
	noble2.setCustomName('a Noble')
	noble2.setOptionsBitmask(256)
	
	commoner11 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(3529.1), float(5.0), float(-4900.4), float(0.42), float(0.91)) 
	commoner11.setCustomName('a Commoner')
	
	businessman3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'tatooine', long(0), float(3595.7), float(5.0), float(-4740.1), float(0), float(0)) 
	businessman3.setCustomName('a Businessman')
	businessman3.setOptionsBitmask(256)
	
	jawa1 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3663.3), float(5.0), float(-4858.6), float(0), float(0)) 
	jawa1.setCustomName('a Jawa')
	jawa1.setOptionsBitmask(256)
	
	commoner12 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(3490.3), float(5.0), float(-4799.4), float(0), float(0)) 
	commoner12.setCustomName('a Scientist')
	
	commoner13 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(3559.7), float(5.0), float(-4725.9), float(0), float(0)) 
	commoner13.setCustomName('a Commoner')
	
	commoner14 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_01.iff', 'tatooine', long(0), float(3527.7), float(5.0), float(-4721.1), float(0.71), float(0.71)) 
	commoner14.setCustomName('a Commoner')
	
	commoner15 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_tatooine_aqualish_female_01.iff', 'tatooine', long(0), float(3514.9), float(5.0), float(-4737.8), float(0), float(0)) 
	commoner15.setCustomName('a Commoner')
	
	jawa2 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3485.2), float(4.9), float(-4859.2), float(0), float(0)) 
	jawa2.setCustomName('a Jawa')
	jawa2.setOptionsBitmask(256)
	
	jawa3 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3465.3), float(5.0), float(-4860.1), float(0.71), float(-0.71)) 
	jawa3.setCustomName('a Jawa')
	jawa3.setOptionsBitmask(256)
	
	jawa4 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3486.8), float(5.0), float(-4884.7), float(0.43051), float(-0.9025)) 
	jawa4.setCustomName('a Jawa')
	jawa4.setOptionsBitmask(256)
	
	jawa5 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3487.1), float(5.0), float(-4886.0), float(0.95105), float(0.3090)) 
	jawa5.setCustomName('a Jawa')
	jawa5.setOptionsBitmask(256)
	
	jawa6 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3488.8), float(5.0), float(-4884.4), float(0.3255), float(-0.9455)) 
	jawa6.setCustomName('a Jawa')
	jawa6.setOptionsBitmask(256)
	
	jawa7 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3472.2), float(5.0), float(-4918.4), float(0), float(0)) 
	jawa7.setCustomName('a Jawa')
	jawa7.setOptionsBitmask(256)
	
	jawa8 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3471.4), float(5.0), float(-4919.5), float(0), float(0)) 
	jawa8.setCustomName('a Jawa')
	jawa8.setOptionsBitmask(256)
	
	jawa9 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3470.3), float(5.0), float(-4918.7), float(0), float(0)) 
	jawa9.setCustomName('a Jawa')
	jawa9.setOptionsBitmask(256)
	
	bib = stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', long(0), float(3552.4), float(5.0), float(-4933.2), float(0.31730), float(-0.9483))
	bib.setCustomName('Bib Fortuna')
	bib.setOptionsBitmask(256)
	
	commoner16 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(3398.2), float(4.0), float(-4654.2), float(0.42), float(0.91)) 
	commoner16.setCustomName('a Commoner')

	noble3 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(3396.3), float(4.0), float(-4774.1), float(0.42), float(0.91)) 
	noble3.setCustomName('a Noble')
	noble3.setOptionsBitmask(256)
	
	entertainer1 = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_female_01.iff', 'tatooine', long(0), float(3305.7), float(5.6), float(-4771.7), float(0), float(0)) 
	entertainer1.setCustomName('an Entertainer')
	entertainer1.setOptionsBitmask(256)
	
	r3m6 = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'tatooine', long(0), float(3460.1), float(4.0), float(-4898.2), float(0.38), float(-0.92)) 
	r3m6.setCustomName('R3-M6')
	r3m6.setOptionsBitmask(256)
	
	eg1 = stcSvc.spawnObject('object/mobile/shared_eg6_power_droid.iff', 'tatooine', long(0), float(3463.8), float(4.0), float(-4882.6), float(-0.38), float(0.92)) 
	eg1.setCustomName('an EG-6 Power Droid')
	eg1.setOptionsBitmask(256)

	commoner17 = stcSvc.spawnCommoner('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(3452.6), float(4.0), float(-4937.1), float(0), float(0)) 
	commoner17.setCustomName('a Commoner')
	
	lifter1 = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'tatooine', long(0), float(3547), float(5.0), float(-4768.9), float(0), float(0)) 
	lifter1.setCustomName('a CLL-8 Binary Load Lifter')
	lifter1.setOptionsBitmask(256)
	
	r3j7 = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'tatooine', long(0), float(3311.1), float(4.0), float(-4820.2), float(0.38), float(-0.92)) 
	r3j7.setCustomName('R3-J7')
	r3j7.setOptionsBitmask(256)
	
	noble4 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_03.iff', 'tatooine', long(0), float(3255.3), float(4.0), float(-4848.1), float(0.42), float(0.91)) 
	noble4.setCustomName('a Noble')
	noble4.setOptionsBitmask(256)
	
	byxlePedette = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_byxle.iff', 'tatooine', long(0), float(3365), float(5), float(-4639), float(0.99), float(0.12)) 
	byxlePedette.setOptionsBitmask(264)
	
	errikDarksider = stcSvc.spawnObject('object/mobile/shared_dressed_herald_tatooine_01.iff', 'tatooine', long(0), float(3381), float(4.6), float(-4498), float(0.91), float(0.40)) 
	errikDarksider.setCustomName('Errik Darksider')
	errikDarksider.setOptionsBitmask(256)
	
	gendra = stcSvc.spawnObject('object/mobile/shared_dressed_gendra.iff', 'tatooine', long(0), float(3308), float(5.6), float(-4785), float(0.84), float(0.53)) 
	gendra.setOptionsBitmask(264)
	
	lurval = stcSvc.spawnObject('object/mobile/shared_lurval.iff', 'tatooine', long(0), float(3387), float(5), float(-4791), float(-0.4), float(0.91)) 
	lurval.setCustomName('Lurval')
	lurval.setOptionsBitmask(256)
	
	matildaCarson = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_02.iff', 'tatooine', long(0), float(3490.2), float(5), float(-4778), float(0.87), float(0.48)) 
	matildaCarson.setCustomName('Matilda Carson')
	matildaCarson.setOptionsBitmask(256)
	
	vanvi = stcSvc.spawnObject('object/mobile/shared_dressed_bestine_artist01.iff', 'tatooine', long(0), float(3312), float(5), float(-4655), float(0.95), float(-0.28)) 
	vanvi.setCustomName('Vanvi Hotn')
	vanvi.setOptionsBitmask(256)
	
	harburik = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_lt_harburik.iff', 'tatooine', long(0), float(3486), float(5), float(-4740), float(0), float(-0.71), float(0), float(0.71)) 
	harburik.setCustomName('Lt. Harburik')
	harburik.setOptionsBitmask(256)
	
	trehla = stcSvc.spawnObject('object/mobile/shared_dressed_tutorial_mentor.iff', 'tatooine', long(0), float(3484), float(5), float(-4808), float(0), float(0.92), float(0), float(-0.38)) 
	trehla.setCustomName('Trehla Keelo')
	trehla.setOptionsBitmask(256)
	
	#Eisley Legacy Quest NPCs
	
	vourk = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_santos.iff', 'tatooine', long(0), float(3520.0), float(5.0), float(-4821.0), float(0.42), float(0.91))
	vourk.setCustomName('Vourk Ver\'Zremp')
	vourk.setOptionsBitmask(256)
	
	building = core.objectService.getObject(long(1279956)) 
	mayor = stcSvc.spawnObject('object/mobile/shared_dressed_mayor_mikdanyell_guhrantt.iff', 'tatooine', building.getCellByCellNumber(4), float(1.2), float(2.5), float(5.4), float(0), float(0), float(0), float(0))
	#mayor.setOptionsBitmask(264)
	
	enthaKandela = stcSvc.spawnObject('object/mobile/shared_dressed_entha_kandela.iff', 'tatooine', long(0), float(3511), float(5.0), float(-4785), float(0.70), float(0.71))
	enthaKandela.setCustomName('Entha Kandela')
	enthaKandela.setOptionsBitmask(256)
	
	purvis = stcSvc.spawnObject('object/mobile/shared_dressed_purvis_arrison.iff', 'tatooine', long(0), float(3512.4), float(5.0), float(-4764.9), float(0.38), float(-0.92))
	purvis.setCustomName('Purvis Arrison')
	purvis.setOptionsBitmask(256)
	
	building = core.objectService.getObject(long(1189630)) 
	peawpRdawc = stcSvc.spawnObject('object/mobile/shared_peawp_bodyguard_trainer.iff', 'tatooine', building.getCellByCellNumber(9), float(-11.8), float(1.1), float(-10.1), float(0), float(0), float(0), float(0))
	peawpRdawc.setCustomName('Peawp R\'dawc')
	peawpRdawc.setOptionsBitmask(256)
	
	nikoBrehe = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_niko.iff', 'tatooine', long(0), float(3506.7), float(5.0), float(-4795.8), float(0.70), float(0.71))
	nikoBrehe.setOptionsBitmask(264)
	
	dunir = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_dunir.iff', 'tatooine', long(0), float(3520.7), float(5.0), float(-4683.7), float(0.99), float(-0.08))
	dunir.setOptionsBitmask(264)
	
	#Eisley Ship Controller
	stcSvc.spawnObject('object/mobile/shared_distant_ship_controller.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91))
	

	#Profession Counselor
	#stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', 'tatooine', long(0), float(3533.14), float(5), float(-4788.86), float(-0.3327), float(0.9288))
	
	#Junk Dealers
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3355), float(5), float(-4823), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3412), float(5), float(-4713), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3500), float(5), float(-4960), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3497), float(5), float(-4928), float(0), float(1))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3377), float(5), float(-4524), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3271), float(5), float(-4704), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'tatooine', long(0), float(3476), float(5), float(-4665), float(0.71), float(0.71))


	return
	

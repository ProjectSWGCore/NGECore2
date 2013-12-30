import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
# Wayfar Life Day, by Wefi, modified by Fingies
		
	stcSvc.spawnObject('object/mobile/shared_lifeday_saun_dann.iff', 'tatooine', long(0), float(-5037.00), float(75), float(-6561), float(-0.75), float(0)) # Life Day
	figlife = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan.iff', 'tatooine', long(0), float(-5046.00), float(75), float(-6560), float(-0.75), float(0)) # LD Figrin
	figlife.setCustomName2('Figrin D\'an')
	bandtat1 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5040.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 1
	bandtat1.setCustomName2('Doikk Nats')
	bandtat2 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(-0.75), float(0)) # LD band 2
	bandtat2.setCustomName2('Tech Mor')
	bandtat3 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5048.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 3
	bandtat3.setCustomName2('Nalan Cheel')
	bandtat4 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(-0.75), float(0)) # LD band 4
	bandtat4.setCustomName2('Sunil Eide')
	reblifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_rebel_vendor.iff', 'tatooine', long(0), float(-5212.2), float(75.0), float(-6571.7), float(0.656), float(-0.7547)) #Rebel Wookiee Vendor
	reblifevendor.setCustomName2('Oolovv \(Wookiee Freedom Fighter)')
	reblifesold1 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6569.6), float(0.669), float(-0.743)) #Rebel Vendor Guard 1
	reblifesold1.setCustomName2('a Rebel Soldier')
	reblifesold2 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6573.1), float(0.669), float(-0.743)) #Rebel Vendor Guard 2
	reblifesold2.setCustomName2('a Rebel Soldier')
	implifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_imperial_vendor.iff', 'tatooine', long(0), float(-5097.1), float(75.0), float(-6570.7), float(0.0087), float(-0.9999)) #Imp Vendor
	implifevendor.setCustomName2('Sstrigge \(Trandoshan \'Trader\')')
	implifesold1 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5092.2), float(75.0), float(-6569.0), float(0.0087), float(-0.9999)) #Imp Vendor Guard 1
	implifesold1.setCustomName2('a Stormtrooper')
	implifesold2 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5100.8), float(75.0), float(-6569.4), float(0.0087), float(-0.9999)) #Imp Vendor Guard 2
	implifesold2.setCustomName2('a Stormtrooper')
	stcSvc.spawnObject('object/tangible/instrument/shared_nalargon.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(0.999132931232), float(-0.0416347384453)) # Drums
	stcSvc.spawnObject('object/tangible/instrument/shared_ommni_box.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(0.999132931232), float(-0.0416347384453)) # Box
	
# Mos Eisley Spawns:  WORK IN PROGRESS by Levarris
	
	#Cantina Interior
	wuher = stcSvc.spawnObject('object/mobile/shared_wuher.iff', 'tatooine', long(1082877), float(8.6), float(-0.9), float(0.4), float(0.71), float(0.71))
	wuher.setCustomName2('Wuher')
	wuher.addOption(Options.INVULNERABLE)
	wuher.setStateBitmask(StateStatus.SittingOnChair)
	
	chadraFanFemale = stcSvc.spawnObject('object/mobile/shared_chadra_fan_female.iff', 'tatooine', long(1082877), float(10.6), float(-0.9), float(-1.5), float(0.42), float(0.90))
	chadraFanFemale.setCustomName2('a Chadra Fan Female')
	
	chadraFanMale = stcSvc.spawnObject('object/mobile/shared_chadra_fan_male.iff', 'tatooine', long(1082877), float(10.7), float(-0.9), float(-0.3), float(0.64), float(0.77))
	chadraFanMale.setCustomName2('a Chadra Fan Male')
	
	muftak = stcSvc.spawnObject('object/mobile/shared_muftak.iff', 'tatooine', long(1082877), float(20.3), float(-0.9), float(4.9), float(0.82), float(0.57)) 
	muftak.setCustomName2('Muftak')
	
	chissMale1 = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_etyyy_chiss_poacher_01.iff', 'tatooine', long(1082877), float(1.7), float(-0.9), float(-5.0), float(0.71), float(0.71)) 
	chissMale1.setCustomName2('a Chiss Male')
	
	chissMale2 = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_etyyy_chiss_poacher_02.iff', 'tatooine', long(1082877), float(3.4), float(-0.9), float(-4.8), float(0), float(0)) 
	chissMale2.setCustomName2('a Chiss Male')
	
	cantinaStorm = stcSvc.spawnObject('object/mobile/shared_stormtrooper.iff', 'tatooine', long(1082877), float(2.9), float(-0.9), float(-6.5), float(0.71), float(0.71)) 
	cantinaStorm.setCustomName2('a Stormtrooper')
	
	cantinaStormL = stcSvc.spawnObject('object/mobile/shared_stormtrooper_groupleader.iff', 'tatooine', long(1082877), float(3.6), float(-0.9), float(-7.9), float(0.71), float(0.71)) 
	cantinaStormL.setCustomName2('a Stormtrooper Squad Leader')
	
	figrinDan = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(3.7), float(-0.9), float(-14.4), float(0.42), float(0.91)) 
	figrinDan.setCustomName2('Figrin D\'an')
	
	techMor = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(4.0), float(-0.9), float(-17.0), float(0.42), float(0.91)) 
	techMor.setCustomName2('Tech M\'or')
	
	doikkNats = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(2.2), float(-0.9), float(-16.4), float(0.42), float(0.91)) 
	doikkNats.setCustomName2('Doikk Na\'ts')
	
	tednDahai = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(1.3), float(-0.9), float(-15.2), float(0.42), float(0.91)) 
	tednDahai.setCustomName2('Tedn Dahai')
	
	nalanCheel = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(0.5), float(-0.9), float(-17.1), float(0.42), float(0.91)) 
	nalanCheel.setCustomName2('Nalan Cheel')
	
	businessman1 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', long(1082877), float(11.0), float(-0.9), float(2.1), float(0.38), float(-0.92)) 
	businessman1.setCustomName2('a Businessman')
	
	commoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_01.iff', 'tatooine', long(1082877), float(10.3), float(-0.9), float(2.7), float(0.82), float(0.57)) 
	commoner1.setCustomName2('a Commoner')
	
	entertainer1 = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_female_01.iff', 'tatooine', long(1082877), float(9.4), float(-0.9), float(3.9), float(0.38), float(-0.92)) 
	entertainer1.setCustomName2('an Entertainer')
	
	noble1 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_trandoshan_male_01.iff', 'tatooine', long(1082877), float(8.6), float(-0.9), float(4.8), float(0.82), float(0.57)) 
	noble1.setCustomName2('a Noble')
	
	commoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_female_01.iff', 'tatooine', long(1082877), float(4.1), float(-0.9), float(5.7), float(1), float(0)) 
	commoner2.setCustomName2('a Commoner')
	
	commoner3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_female_01.iff', 'tatooine', long(1082877), float(3.1), float(-0.9), float(5.9), float(1), float(0)) 
	commoner3.setCustomName2('a Commoner')
	
	commoner4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_male_01.iff', 'tatooine', long(1082877), float(1.7), float(-0.9), float(6.0), float(1), float(0)) 
	commoner4.setCustomName2('a Commoner')
	
	commoner5 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_01.iff', 'tatooine', long(1082877), float(-0.4), float(-0.9), float(5.9), float(1), float(0)) 
	commoner5.setCustomName2('a Commoner')
	
	commoner6 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_05.iff', 'tatooine', long(1082877), float(16.0), float(-0.9), float(4.1), float(0), float(0)) 
	commoner6.setCustomName2('a Commoner')
	
	commoner7 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_zabrak_male_01.iff', 'tatooine', long(1082877), float(8.8), float(-0.9), float(-6.0), float(0.98), float(-0.22)) 
	commoner7.setCustomName2('a Patron')
	
	commoner8 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_08.iff', 'tatooine', long(1082877), float(6.8), float(-0.9), float(-6.5), float(0.98), float(-0.22)) 
	commoner8.setCustomName2('a Patron')
	
	commoner9 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_09.iff', 'tatooine', long(1082877), float(1.1), float(-0.9), float(-7.7), float(0.42), float(0.91)) 
	commoner9.setCustomName2('a Commoner')
	
	commoner10 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff', 'tatooine', long(1082877), float(2.1), float(-0.9), float(-8.4), float(0.42), float(0.91)) 
	commoner10.setCustomName2('a Commoner')
	
	#Starport Interior
	
	#City Hall Interior
	
	#Lucky Despot Interior
	
	#Medical Center Interior
	
	#Theater Interior
	
	#Miscellaneous Building Interiors
	
	#Outside
	businessman2 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', long(0), float(3663.3), float(4.0), float(-4738.6), float(0), float(0)) 
	businessman2.setCustomName2('a Businessman')
	
	noble2 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91)) 
	noble2.setCustomName2('a Noble')
	
	commoner11 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(3529.1), float(5.0), float(-4900.4), float(0.42), float(0.91)) 
	commoner11.setCustomName2('a Commoner')
	
	businessman3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'tatooine', long(0), float(3595.7), float(5.0), float(-4740.1), float(0), float(0)) 
	businessman3.setCustomName2('a Businessman')
	
	jawa1 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3663.3), float(5.0), float(-4858.6), float(0), float(0)) 
	jawa1.setCustomName2('a Jawa')
	
	commoner12 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(3512.2), float(5.0), float(-4764.2), float(0), float(0)) 
	commoner12.setCustomName2('a Scientist')
	
	commoner13 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(3559.7), float(5.0), float(-4725.9), float(0), float(0)) 
	commoner13.setCustomName2('a Commoner')
	
	commoner14 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_01.iff', 'tatooine', long(0), float(3527.7), float(5.0), float(-4721.1), float(0.71), float(0.71)) 
	commoner14.setCustomName2('a Commoner')
	
	commoner15 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_female_01.iff', 'tatooine', long(0), float(3514.9), float(5.0), float(-4737.8), float(0), float(0)) 
	commoner15.setCustomName2('a Commoner')
	
	jawa2 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3485.2), float(4.9), float(-4859.2), float(0), float(0)) 
	jawa2.setCustomName2('a Jawa')
	
	jawa3 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3465.3), float(5.0), float(-4860.1), float(0.71), float(-0.71)) 
	jawa3.setCustomName2('a Jawa')
	
	jawa4 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3486.8), float(5.0), float(-4884.7), float(0.43051), float(-0.9025)) 
	jawa4.setCustomName2('a Jawa')
	
	jawa5 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3487.1), float(5.0), float(-4886.0), float(0.95105), float(0.3090)) 
	jawa5.setCustomName2('a Jawa')
	
	jawa6 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3488.8), float(5.0), float(-4884.4), float(0.3255), float(-0.9455)) 
	jawa6.setCustomName2('a Jawa')
	
	jawa7 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3472.2), float(5.0), float(-4918.4), float(0), float(0)) 
	jawa7.setCustomName2('a Jawa')
	
	jawa8 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3471.4), float(5.0), float(-4919.5), float(0), float(0)) 
	jawa8.setCustomName2('a Jawa')
	
	jawa9 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3470.3), float(5.0), float(-4918.7), float(0), float(0)) 
	jawa9.setCustomName2('a Jawa')
	
	bib = stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', long(0), float(3552.4), float(5.0), float(-4933.2), float(0.31730), float(-0.9483))
	bib.setCustomName2('Bib Fortuna')
	
	commoner16 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(3398.2), float(4.0), float(-4654.2), float(0.42), float(0.91)) 
	commoner16.setCustomName2('a Commoner')
	
	noble3 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(3396.3), float(4.0), float(-4774.1), float(0.42), float(0.91)) 
	noble3.setCustomName2('a Noble')
	
	entertainer1 = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_female_01.iff', 'tatooine', long(0), float(3305.7), float(5.6), float(-4771.7), float(0), float(0)) 
	entertainer1.setCustomName2('an Entertainer')
	
	r3m6 = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'tatooine', long(0), float(3460.1), float(4.0), float(-4898.2), float(0.38), float(-0.92)) 
	r3m6.setCustomName2('R3-M6')
	
	eg1 = stcSvc.spawnObject('object/mobile/shared_eg6_power_droid.iff', 'tatooine', long(0), float(3463.8), float(4.0), float(-4882.6), float(-0.38), float(0.92)) 
	eg1.setCustomName2('an EG-6 Power Droid')

	commoner17 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(3452.6), float(4.0), float(-4937.1), float(0), float(0)) 
	commoner17.setCustomName2('a Commoner')
	
	lifter1 = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'tatooine', long(0), float(3547), float(5.0), float(-4768.9), float(0), float(0)) 
	lifter1.setCustomName2('a CLL-8 Binary Load Lifter')
	
	r3j7 = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'tatooine', long(0), float(3311.1), float(4.0), float(-4820.2), float(0.38), float(-0.92)) 
	r3j7.setCustomName2('R3-J7')
	
	noble4 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_03.iff', 'tatooine', long(0), float(3255.3), float(4.0), float(-4848.1), float(0.42), float(0.91)) 
	noble4.setCustomName2('a Noble')


	#Eisley Ship Controller
	stcSvc.spawnObject('object/mobile/shared_distant_ship_controller.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91))
	
# Mos Espa NPC Spawns

# Mos Entha NPC Spawns

# Bestine NPC Spawns

# Anchorhead NPC Spawns

# Mos Taike NPC Spawns

# Wayfar NPC Spawns

# Jabba's Palace Theme Park Spawns	
	
	return
	

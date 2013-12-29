import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
# Wayfar Life Day, by Wefi
		
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
	reblifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_rebel_vendor.iff', 'tatooine', long(0), float(-5212.2), float(75.0), float(-6571.7), float(0.656), float(-0.7547)) #Rebel Wookiee Vendor
	reblifevendor.setCustomName2('Oolovv \(Wookiee Freedom Fighter)')
	reblifesold1 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6569.6), float(0.669), float(-0.743)) #Rebel Vendor Guard 1
	reblifesold1.setCustomName2('a Rebel Soldier')
	reblifesold2 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6573.1), float(0.669), float(-0.743)) #Rebel Vendor Guard 2
	reblifesold2.setCustomName2('a Rebel Soldier')
	implifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_imperial_vendor.iff', 'tatooine', long(0), float(-5095.6), float(75.0), float(-6559.2), float(0.0436), float(0.9990)) #Imp Vendor
	implifevendor.setCustomName2('Sstrigge \(Trandoshan \'Trader\')')
	implifesold1 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5092.2), float(75.0), float(-6559.0), float(0.0087), float(0.9999)) #Imp Vendor Guard 1
	implifesold1.setCustomName2('a Stormtrooper')
	implifesold2 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5100.8), float(75.0), float(-6569.4), float(0.0261), float(0.9996)) #Imp Vendor Guard 2
	implifesold2.setCustomName2('a Stormtrooper')
	stcSvc.spawnObject('object/tangible/instrument/shared_nalargon.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(0.999132931232), float(-0.0416347384453)) # Drums
	stcSvc.spawnObject('object/tangible/instrument/shared_ommni_box.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(0.999132931232), float(-0.0416347384453)) # Box
	
# Mos Eisley Spawns:  WORK IN PROGRESS by Levarris
	
	#Cantina Interior
	wuher = stcSvc.spawnObject('object/mobile/shared_wuher.iff', 'tatooine', long(1082877), float(8.6), float(-0.9), float(0.4), float(0.71), float(0.71))
	wuher.setCustomName2('Wuher')
	
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
	figrinDan.setCustomName2('Figrin Dan')
	
	techMor = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(4.0), float(-0.9), float(-17.0), float(0.42), float(0.91)) 
	techMor.setCustomName2('Tech M\'or')
	
	doikkNats = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(2.2), float(-0.9), float(-16.4), float(0.42), float(0.91)) 
	doikkNats.setCustomName2('Doikk Na\'ts')
	
	tednDahai = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(1.3), float(-0.9), float(-15.2), float(0.42), float(0.91)) 
	tednDahai.setCustomName2('Tedn Dahai')
	
	nalanCheel = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_figrin_dan.iff', 'tatooine', long(1082880), float(0.5), float(-0.9), float(-17.1), float(0.42), float(0.91)) 
	nalanCheel.setCustomName2('Nalan Cheel')
	
	businessman1 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', long(1082877), float(10.7), float(-0.9), float(1.9), float(0.42), float(0.91)) 
	businessman1.setCustomName2('a Businessman')
	
	commoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_01.iff', 'tatooine', long(1082877), float(10.3), float(-0.9), float(2.7), float(0.42), float(0.91)) 
	commoner1.setCustomName2('a Commoner')
	
	entertainer1 = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_female_01.iff', 'tatooine', long(1082877), float(9.4), float(-0.9), float(3.9), float(0.42), float(0.91)) 
	entertainer1.setCustomName2('an Entertainer')
	
	noble1 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_trandoshan_male_01.iff', 'tatooine', long(1082877), float(8.6), float(-0.9), float(4.8), float(0.42), float(0.91)) 
	noble1.setCustomName2('a Noble')
	
	commoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_female_01.iff', 'tatooine', long(1082877), float(4.1), float(-0.9), float(5.7), float(0.42), float(0.91)) 
	commoner2.setCustomName2('a Commoner')
	
	commoner3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_female_01.iff', 'tatooine', long(1082877), float(3.1), float(-0.9), float(5.9), float(0.42), float(0.91)) 
	commoner3.setCustomName2('a Commoner')
	
	commoner4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_male_01.iff', 'tatooine', long(1082877), float(1.7), float(-0.9), float(6.0), float(0.42), float(0.91)) 
	commoner4.setCustomName2('a Commoner')
	
	commoner5 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_01.iff', 'tatooine', long(1082877), float(-0.4), float(-0.9), float(5.9), float(0.42), float(0.91)) 
	commoner5.setCustomName2('a Commoner')
	
	commoner6 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_05.iff', 'tatooine', long(1082877), float(16.0), float(-0.9), float(4.1), float(0.42), float(0.91)) 
	commoner6.setCustomName2('a Commoner')
	
	commoner7 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_zabrak_male_01.iff', 'tatooine', long(1082877), float(8.8), float(-0.9), float(-6.0), float(0.42), float(0.91)) 
	commoner7.setCustomName2('a Patron')
	
	commoner8 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_08.iff', 'tatooine', long(1082877), float(6.8), float(-0.9), float(-6.5), float(0.42), float(0.91)) 
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
	businessman2 = stcSvc.spawnObject('object/mobile/shared_dressed_businessman_human_male_01.iff', 'tatooine', long(0), float(3532.7), float(5.0), float(-4788.1), float(0), float(0)) 
	businessman2.setCustomName2('a Businessman')
	
	noble2 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91)) 
	noble2.setCustomName2('a Noble')
	
	commoner11 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(3529.1), float(5.0), float(-4900.4), float(0.42), float(0.91)) 
	commoner11.setCustomName2('a Commoner')
	
	businessman3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'tatooine', long(0), float(3532.7), float(5.0), float(-4788.1), float(0), float(0)) 
	businessman3.setCustomName2('a Businessman')
	
	jawa1 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3663.3), float(5.0), float(-4858.6), float(0), float(0)) 
	jawa1.setCustomName2('a Jawa')
	
	commoner12 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(3532.7), float(5.0), float(-4788.1), float(0), float(0)) 
	commoner12.setCustomName2('a Scientist')
	
	commoner13 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(3559.7), float(5.0), float(-4725.9), float(0), float(0)) 
	commoner13.setCustomName2('a Commoner')
	
	commoner14 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_01.iff', 'tatooine', long(0), float(3527.7), float(5.0), float(-4721.1), float(0), float(0)) 
	commoner14.setCustomName2('a Commoner')
	
	commoner15 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_female_01.iff', 'tatooine', long(0), float(3513.7), float(5.0), float(-4740.4), float(0), float(0)) 
	commoner15.setCustomName2('a Commoner')
	
	ltHarburik = stcSvc.spawnObject('object/mobile/tatooine_npc/shared_lt_harburik.iff', 'tatooine', long(0), float(3485.4), float(5.0), float(-4788.1), float(0), float(0)) 
	ltHarburik.setCustomName2('Lieutenant Harburik')
	
	jawa2 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3475.4), float(5.0), float(-4852.7), float(0), float(0)) 
	jawa2.setCustomName2('a Jawa')
	
	jawa3 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3469.3), float(5.0), float(-4861.5), float(0), float(0)) 
	jawa3.setCustomName2('a Jawa')
	
	jawa4 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3486.1), float(5.0), float(-4884.7), float(0), float(0)) 
	jawa4.setCustomName2('a Jawa')
	
	jawa5 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3487.1), float(5.0), float(-4886.0), float(0), float(0)) 
	jawa5.setCustomName2('a Jawa')
	
	jawa6 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3488.8), float(5.0), float(-4884.4), float(0), float(0)) 
	jawa6.setCustomName2('a Jawa')
	
	jawa7 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3472.2), float(5.0), float(-4918.4), float(0), float(0)) 
	jawa7.setCustomName2('a Jawa')
	
	jawa8 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3471.4), float(5.0), float(-4919.5), float(0), float(0)) 
	jawa8.setCustomName2('a Jawa')
	
	jawa9 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(3470.3), float(5.0), float(-4918.7), float(0), float(0)) 
	jawa9.setCustomName2('a Jawa')
	
	bib = stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', long(0), float(3552.4), float(5.0), float(-4933.2), float(0), float(0))
	bib.setCustomName2('Bib Fortuna')
	
# Mos Espa NPC Spawns

# Mos Entha NPC Spawns

# Bestine NPC Spawns

# Anchorhead NPC Spawns

# Mos Taike NPC Spawns

# Wayfar NPC Spawns

# Jabba's Palace Theme Park Spawns	
	
	return
	

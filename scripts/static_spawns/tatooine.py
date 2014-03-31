import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
# Wayfar Life Day, by Wefi, modified by Fingies
		
#	stcSvc.spawnObject('object/mobile/shared_lifeday_saun_dann.iff', 'tatooine', long(0), float(-5037.00), float(75), float(-6561), float(-0.75), float(0)) # Life Day
#	figlife = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan.iff', 'tatooine', long(0), float(-5046.00), float(75), float(-6560), float(-0.75), float(0)) # LD Figrin
#	figlife.setCustomName2('Figrin D\'an')
#	bandtat1 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5040.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 1
#	bandtat1.setCustomName2('Doikk Nats')
#	bandtat2 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(-0.75), float(0)) # LD band 2
#	bandtat2.setCustomName2('Tech Mor')
#	bandtat3 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5048.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 3
#	bandtat3.setCustomName2('Nalan Cheel')
#	bandtat4 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(-0.75), float(0)) # LD band 4
#	bandtat4.setCustomName2('Sunil Eide')
#	reblifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_rebel_vendor.iff', 'tatooine', long(0), float(-5212.2), float(75.0), float(-6571.7), float(0.656), float(-0.7547)) #Rebel Wookiee Vendor
#	reblifevendor.setCustomName2('Oolovv \(Wookiee Freedom Fighter)')
#	reblifesold1 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6569.6), float(0.669), float(-0.743)) #Rebel Vendor Guard 1
#	reblifesold1.setCustomName2('a Rebel Soldier')
#	reblifesold2 = stcSvc.spawnObject('object/mobile/shared_rebel_snow_m_01.iff', 'tatooine', long(0), float(-5208.8), float(75.0), float(-6573.1), float(0.669), float(-0.743)) #Rebel Vendor Guard 2
#	reblifesold2.setCustomName2('a Rebel Soldier')
#	implifevendor = stcSvc.spawnObject('object/mobile/shared_life_day_imperial_vendor.iff', 'tatooine', long(0), float(-5097.1), float(75.0), float(-6570.7), float(0.0087), float(-0.9999)) #Imp Vendor
#	implifevendor.setCustomName2('Sstrigge \(Trandoshan \'Trader\')')
#	implifesold1 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5092.2), float(75.0), float(-6569.0), float(0.0087), float(-0.9999)) #Imp Vendor Guard 1
#	implifesold1.setCustomName2('a Stormtrooper Elite')
#	implifesold2 = stcSvc.spawnObject('object/mobile/shared_snowtrooper_s01.iff', 'tatooine', long(0), float(-5100.8), float(75.0), float(-6569.4), float(0.0087), float(-0.9999)) #Imp Vendor Guard 2
#	implifesold2.setCustomName2('a Stormtrooper')
#	stcSvc.spawnObject('object/tangible/instrument/shared_nalargon.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(0.999132931232), float(-0.0416347384453)) # Drums
#	stcSvc.spawnObject('object/tangible/instrument/shared_ommni_box.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(0.999132931232), float(-0.0416347384453)) # Box
	
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
	
	anetiaKahryn = stcSvc.spawnObject('object/mobile/shared_dressed_twk_entertainer.iff', 'tatooine', long(1082878), float(19.8), float(-0.9), float(-21), float(-0.03), float(0.99)) 
	anetiaKahryn.setCustomName2('Anetia Kah\'ryn')
	
	dravis = stcSvc.spawnObject('object/mobile/shared_space_privateer_tier1_tatooine.iff', 'tatooine', long(1082886), float(-21.7), float(-0.9), float(25.5), float(0.99), float(0.03)) 
	dravis.setCustomName2('Dravis')
	
	talonKarrde = stcSvc.spawnObject('object/mobile/shared_dressed_talon_karrde.iff', 'tatooine', long(1082887), float(-25.7), float(-0.5), float(8.8), float(0.21), float(0.97)) 
	talonKarrde.setCustomName2('Talon Karrde')
	
	#Starport Interior
	
	bartender1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff', 'tatooine', long(1106380), float(-59.1), float(2.6), float(39.5), float(0), float(0)) 
	bartender1.setCustomName2('a Bartender')
	
	#City Hall Interior
	
	#Lucky Despot Interior
	
	hansolo1 = stcSvc.spawnObject('object/mobile/shared_han_solo.iff', 'tatooine', long(26949), float(32.3), float(7.0), float(1.6), float(0), float(0)) 
	hansolo1.setCustomName2('Han Solo')
	
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
	
	commoner12 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(3490.3), float(5.0), float(-4799.4), float(0), float(0)) 
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
	
	byxlePedette = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_byxle.iff', 'tatooine', long(0), float(3365), float(5), float(-4639), float(0.99), float(0.12)) 
	byxlePedette.setCustomName2('Byxle Pedette')
	
	errikDarksider = stcSvc.spawnObject('object/mobile/shared_dressed_herald_tatooine_01.iff', 'tatooine', long(0), float(3381), float(4.6), float(-4498), float(0.91), float(0.40)) 
	errikDarksider.setCustomName2('Errik Darksider')
	
	gendra = stcSvc.spawnObject('object/mobile/shared_dressed_gendra.iff', 'tatooine', long(0), float(3308), float(5.6), float(-4785), float(0.84), float(0.53)) 
	gendra.setCustomName2('Gendra')
	
	lurval = stcSvc.spawnObject('object/mobile/shared_lurval.iff', 'tatooine', long(0), float(3387), float(5), float(-4791), float(-0.4), float(0.91)) 
	lurval.setCustomName2('Lurval')
	
	matildaCarson = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_02.iff', 'tatooine', long(0), float(3490.2), float(5), float(-4778), float(0.87), float(0.48)) 
	matildaCarson.setCustomName2('Matilda Carson')
	
	vanvi = stcSvc.spawnObject('object/mobile/shared_dressed_bestine_artist01.iff', 'tatooine', long(0), float(3312), float(5), float(-4655), float(0.95), float(-0.28)) 
	vanvi.setCustomName2('Vanvi Hotn')
	
	#Eisley Legacy Quest NPCs
	
	vourk = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_santos.iff', 'tatooine', long(0), float(3520.0), float(5.0), float(-4821.0), float(0.42), float(0.91))
	vourk.setCustomName2('Vourk Ver\'Zremp')
	
	mayor = stcSvc.spawnObject('object/mobile/shared_dressed_mayor_mikdanyell_guhrantt.iff', 'tatooine', long(1279960), float(1.2), float(2.5), float(5.4), float(0), float(0))
	mayor.setCustomName2('Mayor Mikdanyell Guh\'rantt')
	
	enthaKandela = stcSvc.spawnObject('object/mobile/shared_dressed_entha_kandela.iff', 'tatooine', long(0), float(3511), float(5.0), float(-4785), float(0.70), float(0.71))
	enthaKandela.setCustomName2('Entha Kandela')
	
	purvis = stcSvc.spawnObject('object/mobile/shared_dressed_purvis_arrison.iff', 'tatooine', long(0), float(3512.4), float(5.0), float(-4764.9), float(0.38), float(-0.92))
	purvis.setCustomName2('Purvis Arrison')
	
	peawpRdawc = stcSvc.spawnObject('object/mobile/shared_peawp_bodyguard_trainer.iff', 'tatooine', long(1189639), float(-11.8), float(1.1), float(-10.1), float(0), float(0))
	peawpRdawc.setCustomName2('Peawp R\'dawc')
	
	peawpRdawc = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_niko.iff', 'tatooine', long(0), float(3506.7), float(5.0), float(-4795.8), float(0.70), float(0.71))
	peawpRdawc.setCustomName2('Niko Brehe')
	
	dunir = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_dunir.iff', 'tatooine', long(0), float(3520.7), float(5.0), float(-4683.7), float(0.99), float(-0.08))
	dunir.setCustomName2('Dunir')
	
	#Eisley Ship Controller
	stcSvc.spawnObject('object/mobile/shared_distant_ship_controller.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91))
	
# Mos Espa NPC Spawns
	# Cantina Interior
	dalaSocuna = stcSvc.spawnObject('object/mobile/shared_space_rebel_tier1_tatooine_socuna.iff', 'tatooine', long(1256068), float(-28.4), float(-0.5), float(9.4), float(0.33), float(0.94))
	dalaSocuna.setCustomName2('Commander Da\'la Socuna')
	
	watto = stcSvc.spawnObject('object/mobile/shared_watto.iff', 'tatooine', long(26670), float(4.7), float(-0.5), float(2.4), float(0.70), float(-0.71))
	watto.setCustomName2('Watto')
	
	#Exterior Uninteractables
	commoner18 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(-2915.3), float(5.0), float(2148.5), float(0), float(0)) 
	commoner18.setCustomName2('a Commoner')
	
	jawa10 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(-2966.3), float(5.0), float(2196.9), float(0), float(0)) 
	jawa10.setCustomName2('a Jawa')

	commoner19 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(-2960.4), float(5.0), float(2271.3), float(0), float(0)) 
	commoner19.setCustomName2('a Commoner')
	
	commoner20 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_01.iff', 'tatooine', long(0), float(-2934.3), float(5.0), float(2298.9), float(0.71), float(0.71)) 
	commoner20.setCustomName2('a Commoner')
	
	commoner21 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(-2897.7), float(5.0), float(2345.4), float(0.71), float(0.71)) 
	commoner21.setCustomName2('a Commoner')
	
	commoner22 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_male_01.iff', 'tatooine', long(0), float(-2763.8), float(5.0), float(2305.2), float(0.71), float(0.71)) 
	commoner22.setCustomName2('a Commoner')
	
	jawa11 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(-2936.8), float(5.0), float(2078.4), float(0.95105), float(0.3090)) 
	jawa11.setCustomName2('a Jawa')
	
	businessman4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'tatooine', long(0), float(-2914.2), float(5.0), float(2022.4), float(0), float(0)) 
	businessman4.setCustomName2('a Businessman')
	
	commoner23 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(-2904.1), float(5.0), float(1965.3), float(0.71), float(0.71)) 
	commoner23.setCustomName2('a Commoner')
	
# Mos Entha NPC Spawns
	#Outside
	ankwee = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_entha_ankwee.iff', 'tatooine', long(0), float(1351), float(5.0), float(3108), float(0.42), float(0.91))
	ankwee.setCustomName2('An\'kwee')
	
# Bestine NPC Spawns
	# City hall
	indigoSiyan = stcSvc.spawnObject('object/mobile/shared_dressed_indigo_siyan.iff', 'tatooine', long(926483), float(24.7), float(3.2), float(-30.7), float(-0.06), float(0.99))
	indigoSiyan.setCustomName2('Indigo Siyan')
	
	keanna = stcSvc.spawnObject('object/mobile/shared_dressed_keanna_likyna.iff', 'tatooine', long(926480), float(-18.7), float(3.2), float(20.6), float(-0.97), float(0.2))
	keanna.setCustomName2('Keanna Li\'kyna')
	
	oberhaur = stcSvc.spawnObject('object/mobile/shared_space_imperial_tier2_tatooine_oberhaur.iff', 'tatooine', long(926480), float(-21.9), float(3.2), float(26.9), float(0.99), float(0.01))
	oberhaur.setCustomName2('Commander Oberhaur')
	
	seanTrenwell = stcSvc.spawnObject('object/mobile/shared_dressed_sean_trenwell.iff', 'tatooine', long(926483), float(19.4), float(3.2), float(-36), float(-0.06), float(0.99))
	seanTrenwell.setCustomName2('Sean Trenwell ')
	
	talmont = stcSvc.spawnObject('object/mobile/shared_prefect_talmont.iff', 'tatooine', long(926475), float(-1.9), float(3.1), float(-10.3), float(0.99), float(0))
	
	tourAryon = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_tour_aryon.iff', 'tatooine', long(926479), float(-36.8), float(1.3), float(0.3), float(0.8), float(0.59))
	tourAryon.setCustomName2('Tour Aryon')
	
	victorVisalis = stcSvc.spawnObject('object/mobile/shared_dressed_victor_visalis.iff', 'tatooine', long(926480), float(-26.7), float(3.2), float(20.8), float(0.96), float(0.24))
	victorVisalis.setCustomName2('Victor Visalis')
	
	wilhalmSkrim = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_wilhalm_skrim.iff', 'tatooine', long(926482), float(28.9), float(1.3), float(-6.0), float(0.23), float(0.97))
	
	#Miscellaneous Building Interiors
	akalColzet = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_trainer_space_01.iff', 'tatooine', long(1212359), float(0.7), float(1.8), float(-14), float(0), float(0.99))
	akalColzet.setCustomName2('lt. Akal Colzet')
	
	fariousGletch = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_farious_gletch.iff', 'tatooine', long(1278989), float(2.0), float(-0.4), float(-5.7), float(-0.15), float(0.98))

	pfilbeeJhorn = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_pfilbee_jhorn.iff', 'tatooine', long(1279923), float(5.1), float(0.1), float(-3.8), float(-0.7), float(0.71))
	pfilbeeJhorn.setCustomName2('Pfilbee Jhorn')
	
	#Hotel interior
	gilBurtin = stcSvc.spawnObject('object/mobile/shared_dressed_industrialist_trainer_01.iff', 'tatooine', long(1223850), float(20.3), float(1.6), float(12.8), float(0.99), float(0))
	gilBurtin.setCustomName2('Gil Burtin')
	
	# Outside
	barak = stcSvc.spawnObject('object/mobile/shared_smuggler_broker_barak.iff', 'tatooine', long(0), float(-1049), float(5.0), float(-3537), float(0.97), float(0.23))
	barak.setCustomName2('Barak')
	
	barrezz = stcSvc.spawnObject('object/mobile/shared_dressed_dressed_legacy_barrezz.iff', 'tatooine', long(0), float(-1146.8), float(98.0), float(-3892.1), float(0.75), float(0.65))
	barrezz.setCustomName2('Commander Barrezz')
	
	jasha = stcSvc.spawnObject('object/mobile/shared_dressed_bestinejobs_jasha.iff', 'tatooine', long(0), float(-1128), float(98.0), float(-3900), float(-0.42), float(0.90))
	jasha.setCustomName2('Captain Jasha')

	dkrn = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_general_m.iff', 'tatooine', long(0), float(-1160), float(5.0), float(-3525), float(0.90), float(-0.41))
	dkrn.setCustomName2('Commander D\'krn')
	
	gunham = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_lieutenant_m.iff', 'tatooine', long(0), float(-1125), float(12.2), float(-3620), float(0.9), float(0.42))
	gunham.setCustomName2('Commander Gunham')
	
	kormundThrylle = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_kormund_thrylle.iff', 'tatooine', long(0), float(-1043), float(10.0), float(-3530), float(0.99), float(0.06))
	kormundThrylle.setCustomName2('Kormund Thrylle')
	
	calebKnolar = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_major_cold_m.iff', 'tatooine', long(0), float(-1149), float(98.0), float(-3903), float(0.42), float(0.91))
	calebKnolar.setCustomName2('Major Caleb Knolar')
	
# Anchorhead NPC Spawns
	# Cantina Interior
	borraSetas = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_female_03.iff', 'tatooine', long(1213345), float(9.8), float(0.4), float(-1.2), float(-0.65), float(0.75))
	borraSetas.setCustomName2('Borra Setas')
		
	# Outside
	aaphKoden = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_aaph_koden.iff', 'tatooine', long(0), float(129), float(5.0), float(-5399), float(-0.67), float(0.73))
	aaphKoden.setCustomName2('Aaph Koden')
	
	Alger = stcSvc.spawnObject('object/mobile/shared_smuggler_fence_alger.iff', 'tatooine', long(0), float(107), float(5.0), float(-5315), float(0.96), float(0.26))
	Alger.setCustomName2('Alger')
	
	carhlaBastra = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_bastra.iff', 'tatooine', long(0), float(128), float(5.0), float(-5428), float(-0.26), float(0.96))
	carhlaBastra.setCustomName2('Carh\'la Bastra')
	
	cuanTalon = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_cuan.iff', 'tatooine', long(0), float(-161.7), float(65.0), float(-5322.8), float(0), float(0))
	cuanTalon.setCustomName2('Cuan Talon')
	
	dromaOrdo = stcSvc.spawnObject('object/mobile/shared_dressed_anchorjobs_ordo.iff', 'tatooine', long(0), float(110), float(52.0), float(-5431), float(0.36), float(0.93))
	dromaOrdo.setCustomName2('Droma Ordo')
	
	Sorna = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_tosche_sorna.iff', 'tatooine', long(0), float(-135), float(52.0), float(-5331), float(0.36), float(0.93))
	Sorna.setCustomName2('Sorna')
	
# Lars Homestead
	zefAndo = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_zef.iff', 'tatooine', long(0), float(-2574.9), float(0), float(-5516.7), float(0), float(0))
	zefAndo.setCustomName2('Zef Ando')
	
# Jawa Sandcrawler (legacy quest)
	fa2po = stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid.iff', 'tatooine', long(0), float(-3805.7), float(30.4), float(-4721.6), float(0), float(0))
	fa2po.setCustomName2('FA-2PO')
	
# Darklighter Cache Cave (tat -260 -6930 not spawning from buildout)


	

# Mos Taike NPC Spawns

# Wayfar NPC Spawns

# Jabba's Palace Theme Park Spawns

	reelo = stcSvc.spawnObject('object/mobile/shared_reelo_baruk.iff', 'tatooine', long(26560), float(-3.5), float(0.2), float(113.5), float(0), float(0))
	reelo.setCustomName2('Reelo Baruk')
	
	reeyees = stcSvc.spawnObject('object/mobile/shared_dressed_gran_thug_male_01.iff', 'tatooine', long(26560), float(5.8), float(0.2), float(115.7), float(0.70), float(-0.71))
	reeyees.setCustomName2('Ree-Yees')
	
	ephant = stcSvc.spawnObject('object/mobile/shared_ephant_mon.iff', 'tatooine', long(26564), float(-6.1), float(5.8), float(86.1), float(0), float(0))
	ephant.setCustomName2('Ephant Mon')
	
	porcellus = stcSvc.spawnObject('object/mobile/shared_dressed_porcellus.iff', 'tatooine', long(26572), float(-44.0), float(3.0), float(63.4), float(0), float(0))
	porcellus.setCustomName2('Porcellus')
	
	barada = stcSvc.spawnObject('object/mobile/shared_barada.iff', 'tatooine', long(26596), float(31.2), float(0.2), float(-1.0), float(0.98), float(-0.17))
	barada.setCustomName2('Barada')
	
	bibMain = stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', long(26582), float(-11.1), float(2.0), float(49.5), float(0.70), float(0.71))
	bibMain.setCustomName2('Bib Fortuna')
	
	jabba = stcSvc.spawnObject('object/mobile/shared_jabba_the_hutt.iff', 'tatooine', long(26582), float(-17.0), float(2.8), float(44.9), float(0.70), float(0.71))
	jabba.setCustomName2('Jabba the Hutt')
	
	oola = stcSvc.spawnObject('object/mobile/shared_oola.iff', 'tatooine', long(26582), float(-10.3), float(2.0), float(43.9), float(0.70), float(-0.71))
	oola.setCustomName2('Oola')
	oola.setCurrentAnimation('wave1')
	
	bobaFett = stcSvc.spawnObject('object/mobile/shared_boba_fett.iff', 'tatooine', long(26582), float(-1.0), float(3.0), float(33.1), float(0), float(0))
	bobaFett.setCustomName2('Boba Fett')
	
	ev9d9 = stcSvc.spawnObject('object/mobile/shared_ev_9d9.iff', 'tatooine', long(26574), float(18.8), float(0.2), float(78.7), float(1.0), float(0))
	ev9d9.setCustomName2('EV-9D9')
	
	malakili = stcSvc.spawnObject('object/mobile/shared_malakili.iff', 'tatooine', long(26599), float(17.3), float(-11.0), float(43.8), float(0), float(0))
	malakili.setCustomName2('Malakili')
		#Need to somehow get the cellID for the Rancor Pit or it will NEVER be spawned.
		
	maxRebo = stcSvc.spawnObject('object/mobile/shared_max_rebo.iff', 'tatooine', long(26582), float(-1.4), float(3.0), float(26.9), float(0.42), float(-0.9))
	maxRebo.setCustomName2('Max Rebo')
	
	droopy = stcSvc.spawnObject('object/mobile/shared_droopy_mccool.iff', 'tatooine', long(26582), float(-3.9), float(3.0), float(26.1), float(0.42), float(-0.9))
	droopy.setCustomName2('Droopy McCool')
	
	sySnootles = stcSvc.spawnObject('object/mobile/shared_sy_snootles.iff', 'tatooine', long(26582), float(-1.4), float(3.0), float(29.8), float(0.42), float(-0.))
	sySnootles.setCustomName2('Sy Snootles')
	
	g5po = stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', long(26582), float(-14.3), float(2.0), float(47.4), float(0.70), float(0.71))
	g5po.setCustomName2('G5-PO')
	
	return
	

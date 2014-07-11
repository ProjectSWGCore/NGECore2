import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService

#-Coronet
	ralMundi = stcSvc.spawnObject('object/mobile/shared_dressed_noble_fat_twilek_male_02.iff', 'corellia', long(0), float(-138.975), float(28), float(-4718.86), float(-0.97), float(0.21))
	ralMundi.setCustomName2('Ral Mundi')
	ralMundi.setOptionsBitmask(256)
	
	ioTsomcren = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'corellia', long(0), float(-140.701), float(28), float(-4719.16), float(-0.97), float(0.21))
	ioTsomcren.setCustomName2('Io Tsomcren')
	ioTsomcren.setOptionsBitmask(256)

	tarthJax = stcSvc.spawnObject('object/mobile/shared_dressed_binayre_pirate_zabrak_male_01.iff', 'corellia', long(0), float(-137.464), float(28), float(-4718.83), float(-0.97), float(0.21))
	tarthJax.setCustomName2('Tarth Jaxx')
	tarthJax.setOptionsBitmask(256)
	
	rebCoord = stcSvc.spawnObject('object/mobile/shared_dressed_rebel_communication_female_01.iff', 'corellia', long(0), float(94.8749), float(28), float(-4519.08), float(-0.97), float(0.21))
	rebCoord.setCustomName2('Rebel Coordinator')
	rebCoord.setOptionsBitmask(264)
	
	huntrJavz = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_rodian_hunter_m.iff', 'corellia', long(0), float(-51.79), float(28), float(-4662.65), float(0.11), float(0.21))
	huntrJavz.setCustomName2('Hunter Javeezo')
	huntrJavz.setOptionsBitmask(256)	

	dw_heraldRebel = stcSvc.spawnObject('object/mobile/shared_dressed_rebel_general_moncal_male_01.iff', 'corellia', long(0), float(-213.64), float(28), float(-4445.46), float(-0.97), float(0.21))
	dw_heraldRebel.setCustomName2('Lutin Nightstalker')
	dw_heraldRebel.setOptionsBitmask(256)

#---Coronet-CityHall-interrior
	brantlee = stcSvc.spawnObject('object/mobile/shared_dressed_brantlee_spondoon.iff', 'corellia', long(1855463), float(-25.7), float(1.3), float(-0.5), float(0.98), float(0.01))
	#brantlee.setCustomName2('Brantlee Spondoon')
	#brantlee.setOptionsBitmask(256)
	
	cor_brawlr00 = stcSvc.spawnObject('object/mobile/shared_dressed_brawler_trainer_01.iff', 'corellia', long(1855463), float(-1.7274), float(7.9), float(-32.175), float(0.5), float(0.01))
	#cor_brawlr00.setCustomName2('a brawler')	

	cor_btdiplmt02 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_01.iff', 'corellia', long(1855463), float(5.228), float(0.3), float(2.9167), float(-0.47), float(0.21))
	#cor_btdiplmt02.setCustomName2('a Bothan Diplomat')

	cor_btinfobkr00 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_female_02.iff', 'corellia', long(1855463), float(5.228), float(0.3), float(4.016), float(0.99), float(0.01))
	#cor_btinfobkr00.setCustomName2('a Bothan Information Broker')

	cor_btinfobkr01 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_02.iff', 'corellia', long(1855463), float(5.345), float(2.278), float(-27.0615), float(-0.14), float(0.22))
	#cor_btinfobkr01.setCustomName2('a Bothan Information Broker')

	cor_ent00 = stcSvc.spawnObject('object/mobile/shared_dressed_twk_entertainer.iff', 'corellia', long(1855463), float(0.767877), float(0.3), float(-2.902), float(-0.50), float(0.11))
	#cor_ent00.setCustomName2('an Entertainer')
	
	cor_farmer00 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_02.iff', 'corellia', long(1855463), float(-18.6014), float(1.3026), float(-11.314), float(-0.45), float(0.21))
	#cor_farmer00.setCustomName2('a farmer')

	cor_farmer01 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_01.iff', 'corellia', long(1855463), float(0.767877), float(0.3), float(-4.002), float(-0.45), float(0.21))
	#cor_farmer01.setCustomName2('a farmer')
	
	galluraHanderin = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_02.iff', 'corellia', long(1855463), float(-6.51554), float(1.303), float(9.6562), float(0.1), float(0.01))
	#galluraHanderin.setCustomName2('Gallura Handerin')
	#galluraHanderin.setOptionsBitmask(256)
	
	corsec_agent00 = stcSvc.spawnObject('object/mobile/shared_dressed_corellia_local_corsec_chief.iff', 'corellia', long(1855463), float(-0.332), float(0.3), float(-2.902), float(0.89), float(0.01))
	#corsec_agent00.setCustomName2('a CorSec Agent')
	
	thaleDustrunner = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_smuggler_human_male_01.iff', 'corellia', long(1855463), float(-0.05), float(3.078), float(-10.9388), float(0.99), float(0.11))
	#thaleDustrunner.setCustomName2('Thale Dustrunner')
	#thaleDustrunner.setOptionsBitmask(256)

#---Coronet-StarPort-interrior
	cor_btdiplmt00 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_bothan_female_01.iff', 'corellia', long(1855678), float(56.669), float(-0.521137), float(33.7689), float(0.99), float(0.11))
	#cor_btdiplmt00.setCustomName2('a Bothan Diplomat')		
	
	cor_btdiplmt01 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_bothan_male_01.iff', 'corellia', long(1855672), float(8.651), float(0.6394), float(74.448), float(-0.47), float(0.21))
	#cor_btdiplmt01.setCustomName2('a Bothan Diplomat')	
	
	corsec_cadt00 = stcSvc.spawnObject('object/mobile/shared_dressed_corsec_pilot_human_female_01.iff', 'corellia', long(1855678), float(37.163), float(0.6394), float(40.706), float(0.99), float(0.11))
	#corsec_cadt00.setCustomName2('a CorSec cadet')

	farmer02 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_04.iff', 'corellia', long(1855683), float(-62.767), float(2.6394), float(40.66), float(-0.45), float(0.21))
	#farmer02.setCustomName2('a farmer')
	
	gambler00 = stcSvc.spawnObject('object/mobile/ep3/shared_ep3_cheated_gambler.iff', 'corellia', long(1855675), float(-4.61669), float(0.6394), float(67.826), float(0.99), float(0.11))
	#gambler00.setCustomName2('a gambler')
		
	cor_hunter00 = stcSvc.spawnObject('object/mobile/shared_dressed_trader_thug_male_moncal_02.iff', 'corellia', long(1855672), float(8.651), float(0.6394), float(75.548), float(0.99), float(0.11))
	#cor_hunter00.setCustomName2('a hunter')

	jessbConvorr = stcSvc.spawnObject('object/mobile/shared_dressed_corellia_jesseb_convorr.iff', 'corellia', long(1855684), float(50.2), float(1.0), float(20.1), float(-0.47), float(0.21))
	#jessbConvorr.setCustomName2('Jesseb Convorr')
	#jessbConvorr.setOptionsBitmask(256)
	
	cor_merc00 = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_strong_hum_m.iff', 'corellia', long(1855683), float(-62.767), float(2.6394), float(41.76), float(0.99), float(0.11))
	#cor_merc00.setCustomName2('a mercenary')	
	
	cor_miner00 = stcSvc.spawnObject('object/mobile/shared_dressed_mand_miner_hum_03.iff', 'corellia', long(1855678), float(37.163), float(0.6394), float(39.606), float(-0.47), float(0.21))
	#cor_miner00.setCustomName2('a miner')
	
	cor_scintst00 = stcSvc.spawnObject('object/mobile/shared_dressed_twi_female_scientist_01.iff', 'corellia', long(1855678), float(56.669), float(-0.521137), float(32.6689), float(-0.47), float(0.21))
	#cor_scintst00.setCustomName2('a Scientist')

	cor_slice00 = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_slicer_human_male_01.iff', 'corellia', long(1855675), float(-4.61669), float(0.6394), float(66.7263), float(-0.5), float(0.21))
	#cor_slice00.setCustomName2('a slicer')

#-Coronet-Legacy NPCs
	stregand = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_bith_male_01.iff', 'corellia', long(0), float(-33), float(28), float(-4424), float(0.67), float(0.21))
	stregand.setCustomName2('Stregand')
	stregand.setOptionsBitmask(256)
	
	borgan = stcSvc.spawnObject('object/creature/npc/base/shared_whiphid_base_male.iff', 'corellia', long(0), float(-34.4), float(28), float(-4418.2), float(0.75), float(0.21))
	borgan.setCustomName2('Borgan')
	borgan.setOptionsBitmask(256)
	
	#Lt. Joth - wrong appearance - looking for a bearded guy with BLUE CorSec Jacket
	ltJoth = stcSvc.spawnObject('object/mobile/shared_dressed_corellia_cec_officer.iff', 'corellia', long(0), float(-283), float(28), float(-4697), float(0.07), float(0.11))
	ltJoth.setCustomName2('Lieutenant Joth')
	ltJoth.setOptionsBitmask(256)
	
	mackJasper = stcSvc.spawnObject('object/mobile/shared_dressed_corsec_officer_human_male_01.iff', 'corellia', long(0), float(-68.2), float(28), float(-4631.8), float(-0.97), float(0.21))
	mackJasper.setCustomName2('Lieutenant Mack Jasper')
	mackJasper.setOptionsBitmask(256)
	
#-Coronet Misc NPCS
	#Profession Counselor
	stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', 'corellia', long(0), float(-129), float(28), float(-4758), float(-0.3327), float(0.9288))
	
	#Junk Dealer
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-118), float(28), float(-4792), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(22), float(28), float(-4773), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-42), float(28), float(-4612), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-68), float(28), float(-4585), float(0), float(1))
	
	return
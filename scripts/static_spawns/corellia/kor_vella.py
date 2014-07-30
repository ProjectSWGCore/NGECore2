
import sys
# Project SWG:   Kor_Vella:  Static Spawns
# (C)2014 ProjectSWG
# help scripted Steve__ aka Oswin Edit on 7/21/2014


from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	
	stcSvc = core.staticService
	objSvc = core.objectService
	
	#Outside 
	Chemojiq = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_ishitib_male_03.iff', 'corellia', long(0),
							float(-3455.77441406),float(78.0),float(3026.50268555),
							float(0.0627723112702),float(0.998027861118))

	Chemojiq.setCustomName('Chemojiq Afyadaqu (a commoner)')
	Chemojiq.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSecPilot = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'corellia', long(0),
							float(-3367.06),float(86.0),float(3142.54),
							float(-0.53),float(0.84))
	CorSecPilot.setCustomName('(Retired) CorSec Pilot')
	CorSecPilot.setOptionsBitmask(Options.CONVERSABLE)
	
	slicer1 = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_slicer_human_female_01.iff', 'corellia', long(0),
							 float(-3450.92749023),float(78.0),float(3029.52978516),
							 float(0.0627723112702),float(0.998027861118))
	slicer1.setCustomName('a slicer')
	slicer1.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Lieutenant1 = stcSvc.spawnObject('corsec_lieutenant', 'corellia', long(0),
							 float(-3450.28344727),float(78.0),float(3030.74560547),
							 float(0.992517650127),float(0.122101254761))
	
	
	Mew_Ixo = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_human_female_02.iff', 'corellia', long(0),
							 float(-3508.42431641),float(78.0),float(3148.71386719),
							 float(-0.451727330685),float(0.892156124115))
	Mew_Ixo.setCustomName('Mew Ixo (a commoner)')
	Mew_Ixo.setOptionsBitmask(Options.INVULNERABLE)
	
	mercenary1 = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_messanger_sullustan_m.iff', 'corellia', long(0),
							 float(-3520.18286133),float(78.0),float(3126.24707031),
							 float(0.953324377537),float(0.301948100328))
	mercenary1.setCustomName('a mercenary')
	mercenary1.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Lieutenant2 = stcSvc.spawnObject('corsec_inspector_sergeant', 'corellia', long(0),
							 float(-3519.31201172),float(78.0),float(3125.01074219),
							 float(-0.0985990464687),float(0.99512720108))
	 
	
	Elureh = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_twilek_female_01.iff', 'corellia', long(0),
							 float(-3427.33447266),float(86.0),float(3028.91259766),
							 float(0.722705543041),float(0.691156029701))
	Elureh.setCustomName('Elureh Twozel (a commoner)')
	Elureh.setOptionsBitmask(Options.INVULNERABLE)
	
	Wiwhehir = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_female_01.iff', 'corellia', long(0),
							 float(-3415.70898438),float(86.0),float(3032.9765625),
							 float(-0.0664957985282),float(0.997786700726))
	Wiwhehir.setCustomName('Wiwhehir Aina  (a commoner)')
	Wiwhehir.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	gambler1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_03.iff', 'corellia', long(0),
							 float(-3407.97241211),float(86.0),float(3047.93017578),
							 float(-0.202964067459),float(0.979186177254))
	gambler1.setCustomName('a gambler')
	gambler1.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Detective1 = stcSvc.spawnObject('corsec_inspector_sergeant', 'corellia', long(0),
							float(-3408.59838867),float(86.0),float(3049.53833008),
							float(0.974824905396),float(0.222971782088))
	
	Yobre = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_twilek_male_02.iff', 'corellia', long(0),
							 float(-3407.97241211),float(86.0),float(3047.93017578),
							 float(-0.202964067459),float(0.979186177254))
	Yobre.setCustomName('Yobre Digu (a commoner)')
	Yobre.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer2 = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_pirate_human_male_01.iff', 'corellia', long(0),
							 float(-3384.47460938),float(86.0),float(3006.25268555),
							 float(-0.0476738996804),float(0.998862981796))
	slicer2.setCustomName('a slicer')
	slicer2.setOptionsBitmask(Options.INVULNERABLE)
	
	pilot1 = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_messanger_hum_f.iff', 'corellia', long(0),
							 float(-3384.33007812),float(86.0),float(3007.69677734),
							 float(0.994272530079),float(-0.106873080134))
	pilot1.setCustomName('a pilot')
	pilot1.setOptionsBitmask(Options.INVULNERABLE)
	
	Yary = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_02.iff', 'corellia', long(0),
							 float(-3402.54736328),float(86.0),float(2997.35668945),
							 float(0.952086389065),float(-0.305829107761))
	Yary.setCustomName('Yary Quan  (a commoner)')
	Yary.setOptionsBitmask(Options.INVULNERABLE)
	
	Ssubu_Uit = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_zabrak_male_01.iff', 'corellia', long(0),
							 float(-3378.84716797),float(86.0),float(2985.39355469),
							 float(-0.500320196152),float(0.865840494633))
	Ssubu_Uit.setCustomName('Ssubu Uit (a commoner)')
	Ssubu_Uit.setOptionsBitmask(Options.INVULNERABLE)
	
	Vemio_Ulaibr = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_02.iff', 'corellia', long(0),
							 float(-3384.40307617),float(86.0),float(2958.86572266),
							 float(0.334467589855),float(0.942407310009))
	Vemio_Ulaibr.setCustomName('Vemio Ulaibr (a commoner)')
	Vemio_Ulaibr.setOptionsBitmask(Options.INVULNERABLE)
	
	
	Gilken_Budz = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_02.iff', 'corellia', long(0),
							 float(-3335.35205078),float(72.2221221924),float(2925.86010742),
							 float(0.80168646574),float(-0.597744822502))
	Gilken_Budz.setCustomName('Gilken Budz (a commoner)')
	Gilken_Budz.setOptionsBitmask(Options.INVULNERABLE)
	
	Bapr_Ryog = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_01.iff', 'corellia', long(0),
							 float(-3311.41577148),float(69.5288619995),float(2886.07495117),
							 float(0.409965753555),float(0.91210091114))
	Bapr_Ryog.setCustomName('Bapr Ryog (a commoner)')
	Bapr_Ryog.setOptionsBitmask(Options.INVULNERABLE)
	
	Que_Dapo = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_01.iff', 'corellia', long(0),
							 float(-3296.4387207),float(65.8883514404),float(2877.5012207),
							 float(0.999049544334),float(-0.0435907281935))
	Que_Dapo.setCustomName('Que Dapo (a commoner)')
	Que_Dapo.setOptionsBitmask(Options.INVULNERABLE)
	
	Kizoji_Soofl  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_male_03.iff', 'corellia', long(0),
							 float(-3225.52026367),float(32.1508407593),float(2816.15893555),
							 float(-0.135993495584),float(0.990709781647))
	Kizoji_Soofl.setCustomName('Kizoji Soofl (a commoner)')
	Kizoji_Soofl.setOptionsBitmask(Options.INVULNERABLE)
	
	Aigryiw_Queneugry  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_male_03.iff', 'corellia', long(0),
							 float(-3199.22290039),float(30.6414299011),float(2785.6940918),
							 float(-0.0591334067285),float(0.998250186443))
	Aigryiw_Queneugry.setCustomName('Aigryiw Queneugry (a commoner)')
	Aigryiw_Queneugry.setOptionsBitmask(Options.INVULNERABLE)
	
	Biorequ_Petulruni  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff', 'corellia', long(0),
							 float(-3178.03686523),float(30.9999752045),float(2774.5078125),
							 float(-0.510287046432),float(0.86000418663))
	Biorequ_Petulruni.setCustomName('Biorequ Petulruni (a commoner)')
	Biorequ_Petulruni.setOptionsBitmask(Options.INVULNERABLE)
		
	Mavo_Hocif  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff', 'corellia', long(0),
							 float(-3160.58447266),float(30.7000808716),float(2762.60375977),
							 float(0.0315626524389),float(0.99950170517))
	Mavo_Hocif.setCustomName('Mavo Hocif (a commoner)')
	Mavo_Hocif.setOptionsBitmask(Options.INVULNERABLE)	
	
	CorSec_trooper1 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							 float(-3150.68994141),float(31.128408432),float(2772.26635742),
							 float(-0.437099069357),float(0.899413347244))
	CorSec_major1 = stcSvc.spawnObject('corsec_major', 'corellia', long(0),
							 float(-3149.33300781),float(31.1562690735),float(2773.83544922),
							 float(-0.114501707256),float(0.993423104286))
	CorSec_Sergeant = stcSvc.spawnObject('corsec_sergeant', 'corellia', long(0),
							 float(-3148.36938477),float(31.1735477448),float(2771.69555664),
							 float(0.733043313026),float(-0.680181980133))
	
	Haklepi_Liziewa  = stcSvc.spawnObject('object/mobile/shared_dressed_diplomat_human_female_01.iff', 'corellia', long(0),
							 float(-3158.82519531),float(31.0),float(2812.09790039),
							 float(-0.204079598188),float(0.97895437479))
	Haklepi_Liziewa.setCustomName('Haklepi Liziewa (a businessman)')
	Haklepi_Liziewa.setOptionsBitmask(Options.INVULNERABLE)
	
	Weblaimi_Noejaghaw  = stcSvc.spawnObject('object/mobile/shared_dressed_diplomat_human_female_01.iff', 'corellia', long(0),
							 float(-3160.08300781),float(31.0),float(2814.52807617),
							 float(0.97382080555),float(0.227317377925))
	Weblaimi_Noejaghaw.setCustomName('Weblaimi Noejaghaw (a rancher)')
	Weblaimi_Noejaghaw.setOptionsBitmask(Options.INVULNERABLE)
	
	R3_L8  = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'corellia', long(0),
							 float(-3151.65673828),float(31.0),float(2800.70043945),
							 float(0.809274017811),float(-0.587431490421))
	R3_L8.setCustomName('R3-L8')
	R3_L8.setOptionsBitmask(Options.INVULNERABLE)
	
	R3_U1  = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'corellia', long(0),
							 float(-3422.14672852),float(78.1507873535),float(3333.83081055),
							 float(0.395325213671),float(0.918541252613))
	R3_U1.setCustomName('R3-U1')
	R3_U1.setOptionsBitmask(Options.INVULNERABLE)
	
	R5_U7  = stcSvc.spawnObject('object/mobile/shared_r5.iff', 'corellia', long(0),
							 float(-3134.43603516),float(31.0),float(2801.16674805),
							 float(0.91083240509),float(-0.41277629137))
	R5_U7.setCustomName('R5-U7')
	R5_U7.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	Jug_Wesufyuka  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_bith_male_04.iff', 'corellia', long(0),
							 float(-3160.08300781),float(31.0),float(2814.52807617),
							 float(0.97382080555),float(0.227317377925))
	Jug_Wesufyuka.setCustomName('Jug Wesufyuka (a commoner)')
	Jug_Wesufyuka.setOptionsBitmask(Options.INVULNERABLE)
	
	Halubot_Ibic  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_03.iff', 'corellia', long(0),
							 float(-3065.94018555),float(31.0),float(2924.62109375),
							 float(-0.0835588648915),float(0.996502935886))
	Halubot_Ibic.setCustomName('Halubot Ibic (a commoner)')
	Halubot_Ibic.setOptionsBitmask(Options.INVULNERABLE)
	
	CLL_8  = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'corellia', long(0),
							 float(-3095.24633789),float(31.0),float(2975.92724609),
							 float(0.495998561382),float(0.86832344532))
	CLL_8.setCustomName('CLL-8 binary load lifter')
	CLL_8.setOptionsBitmask(Options.INVULNERABLE)
	
	CLL_8_2  = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'corellia', long(0),
							 float(-3444.72363281),float(78.0),float(3384.37133789),
							 float(-0.0269635543227),float(0.999636352062))
	CLL_8_2.setCustomName('CLL-8 binary load lifter')
	CLL_8_2.setOptionsBitmask(Options.INVULNERABLE)
	
	R5_W1  = stcSvc.spawnObject('object/mobile/shared_r5.iff', 'corellia', long(0),
							 float(-3094.45092773),float(31.0),float(2972.26147461),
							 float(0.561120271683),float(0.827734291553))
	R5_W1.setCustomName('R5-W1')
	R5_W1.setOptionsBitmask(Options.INVULNERABLE)
	
	Oymakl_Ochaf  = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_male_01.iff', 'corellia', long(0),
							 float(-3095.37866211),float(31.0),float(2970.3762207),
							 float(-0.705596029758),float(0.708614349365))
	Oymakl_Ochaf.setCustomName('Oymakl Ochaf (a noble)')
	Oymakl_Ochaf.setOptionsBitmask(Options.INVULNERABLE)
	
	Reguboa_Toghyut  = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_male_01.iff', 'corellia', long(0),
							 float(-3087.37866211),float(31.0),float(2984.3762207),
							 float(0.143215626478),float(0.9896915555))
	Reguboa_Toghyut.setCustomName('Reguboa Toghyut (a commoner)')
	Reguboa_Toghyut.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_trooper2 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							 float(-3101.47802734),float(31.0),float(2988.96508789),
							 float(-0.622550427914),float(0.78257972002))
	
	CorSec_trooper3 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							 float(-3103.53442383),float(31.0),float(2987.81103516),
							 float(-0.676236629486),float(0.736684501171))
	
	CorSec_Inspector_Sergeant = stcSvc.spawnObject('corsec_inspector_sergeant', 'corellia', long(0),
							 float(-3102.26074219),float(31.0),float(2988.79785156),
							 float(-0.601358771324),float(0.798979103565))
	
	Epedi_Hovuzeki  = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_male_01.iff', 'corellia', long(0),
							 float(-3112.57324219),float(31.0),float(2984.1340332),
							 float(0.933502137661),float(0.358571708202))
	Epedi_Hovuzeki.setCustomName('Epedi Hovuzeki (a commoner)')
	Epedi_Hovuzeki.setOptionsBitmask(Options.INVULNERABLE)
	
	Daghu_Dryahyuxy  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_05.iff', 'corellia', long(0),
							 float(-3521.90527344),float(70.0),float(3233.68945312),
							 float(-0.515658140182),float(0.856794476509))
	Daghu_Dryahyuxy.setCustomName('Daghu Dryahyuxyi (a commoner)')
	Daghu_Dryahyuxy.setOptionsBitmask(Options.INVULNERABLE)
	
	Vaquiv_Lryequo  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'corellia', long(0),
							 float(-3131.35546875),float(31.0),float(2994.15527344),
							 float(0.937140107155),float(0.348953545094))
	Vaquiv_Lryequo.setCustomName('Vaquiv Lryequo (a commoner)')
	Vaquiv_Lryequo.setOptionsBitmask(Options.INVULNERABLE)
	
	
	Groibleap_Hyize  = stcSvc.spawnObject('object/mobile/shared_dressed_diplomat_trando_male_01.iff', 'corellia', long(0),
							float(-3151.25341797),float(31.0),float(2973.03271484),
							float(-0.0114126997069),float(0.999934911728))
	Groibleap_Hyize.setCustomName('Groibleap Hyize (a businessman)')
	Groibleap_Hyize.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer3 = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_assassin_human_female_01.iff', 'corellia', long(0),
							 float(-3151.17016602),float(31.0),float(2974.5612793),
							 float(0.999989628792),float(0.00454667024314))
	slicer3.setCustomName('a slicer')
	slicer3.setOptionsBitmask(Options.INVULNERABLE)
	
	Llehoc_Moquo  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_01.iff', 'corellia', long(0),
							 float(-3421.0),float(86.0),float(3104.0),
							 float(-0.547767102718),float(0.836630880833))
	Llehoc_Moquo.setCustomName('Llehoc Moquo (a commoner)')
	Llehoc_Moquo.setOptionsBitmask(Options.INVULNERABLE)
	
	Nesmary_Sneipriyg  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_04.iff', 'corellia', long(0),
							 float(-3435.1328125),float(86.0),float(3118.82788086),
							 float(0.41085088253),float(0.911702573299))
	Nesmary_Sneipriyg.setCustomName('Nesmary Sneipriyg (a commoner)')
	Nesmary_Sneipriyg.setOptionsBitmask(Options.INVULNERABLE)
	
	
	Master_Sergeant_Crowley = stcSvc.spawnObject('object/mobile/shared_dressed_corvette_rebel_crowley.iff', 'corellia', long(0),
							float(-3404.28540039),float(86.0),float(3091.94287109),
							float(-0.306415110826),float(0.951898038387))
	Master_Sergeant_Crowley.setCustomName('Master Sergeant Crowley')
	Master_Sergeant_Crowley.setOptionsBitmask(Options.CONVERSABLE)
	
	Bryubloov_Sahu  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_bith_female_06.iff', 'corellia', long(0),
							 float(-3403.70263672),float(86.0),float(3110.86132812),
							 float(0.611221253872),float(0.791459858418))
	Bryubloov_Sahu.setCustomName('Bryubloov Sahu (a commoner)')
	Bryubloov_Sahu.setOptionsBitmask(Options.INVULNERABLE)
	
	Bikozo_Uquidui  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'corellia', long(0),
							 float(-3407.75073242),float(86.0),float(3125.31835938),
							 float(0.942842662334),float(-0.333238601685))
	Bikozo_Uquidui.setCustomName('Bikozo Uquidui (a commoner)')
	Bikozo_Uquidui.setOptionsBitmask(Options.INVULNERABLE)
	
	Nacabooh_Ibuglequ = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_03.iff', 'corellia', long(0),
							 float(-3396.38598633),float(86.0),float(3131.50512695),
							 float(0.857697546482),float(0.514154672623))
	Nacabooh_Ibuglequ.setCustomName('Nacabooh Ibuglequ (a commoner)')
	Nacabooh_Ibuglequ.setOptionsBitmask(Options.INVULNERABLE)
	
	Lodi_Wuvu = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'corellia', long(0),
							 float(-3391.82373047),float(86.0),float(3139.77148438),
							 float(0.889429748058),float(-0.457071959972))
	Lodi_Wuvu.setCustomName('Lodi Wuvu (a commoner)')
	Lodi_Wuvu.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	Fliwribi_Nemubo = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_01.iff', 'corellia', long(0),
							 float(-3391.82373047),float(86.0),float(3139.77148438),
							 float(0.889429748058),float(-0.457071959972))
	Fliwribi_Nemubo.setCustomName('Fliwribi Nemubo (a commoner)')
	Fliwribi_Nemubo.setOptionsBitmask(Options.INVULNERABLE)
	
	Kumop_Ikequoqui = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_08.iff', 'corellia', long(0),
							 float(-3361.3046875),float(86.0),float(3216.63500977),
							 float(0.879864752293),float(0.475224226713))
	Kumop_Ikequoqui.setCustomName('Kumop Ikequoqui (a commoner)')
	Kumop_Ikequoqui.setOptionsBitmask(Options.INVULNERABLE)
	
	Imperial_Sergeant1 = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_5.iff', 'corellia', long(0),
							 float(-3365.23193359),float(86.0),float(3231.57666016),
							 float(0.988618791103),float(0.150441750884))
	Imperial_Sergeant1.setCustomName('Imperial Sergeant')
	Imperial_Sergeant1.setOptionsBitmask(Options.INVULNERABLE)
	
	Imperial_Sergeant2 = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_5.iff', 'corellia', long(0),
							 float(-3368.02197266),float(86.0),float(3242.66259766),
							 float(0.899261772633),float(-0.437410831451))
	Imperial_Sergeant2.setCustomName('Imperial Sergeant')
	Imperial_Sergeant2.setOptionsBitmask(Options.INVULNERABLE)
	
	Fur_Ilo = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_bothan_male_02.iff', 'corellia', long(0),
							 float(-3374.20239258),float(86.0),float(3265.62231445),
							 float(0.362528830767),float(0.931972622871))
	Fur_Ilo.setCustomName('Fur Ilo (a commoner)')
	Fur_Ilo.setOptionsBitmask(Options.INVULNERABLE)
	
	Neibu_Cyameoxu = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_sullustan_male_06.iff', 'corellia', long(0),
							 float(-3364.96044922),float(86.0),float(3283.85620117),
							 float(0.341743439436),float(0.939793288708))
	Neibu_Cyameoxu.setCustomName('Neibu Cyameoxu (a commoner)')
	Neibu_Cyameoxu.setOptionsBitmask(Options.INVULNERABLE)
	
	Varu_Nudi = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_sullustan_male_06.iff', 'corellia', long(0),
							 float(-3351.00878906),float(86.0),float(3302.57104492),
							 float(0.314834952354),float(0.949146449566))
	Varu_Nudi.setCustomName('Varu Nudi (a commoner)')
	Varu_Nudi.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	
	
	
	Eyrigupui_Tuleho = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_03.iff', 'corellia', long(0),
							 float(-3342.88842773),float(86.0),float(3268.03100586),
							 float(0.950825273991),float(-0.309727817774))
	Eyrigupui_Tuleho.setCustomName('Eyrigupui Tuleho (a commoner)')
	Eyrigupui_Tuleho.setOptionsBitmask(Options.INVULNERABLE)
	
	Fly_Xyagru = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_07.iff', 'corellia', long(0),
							 float(-3321.79931641),float(86.0),float(3282.72924805),
							 float(-0.65944904089),float(0.75174921751))
	Fly_Xyagru.setCustomName('Fly Xyagru (a commoner)')
	Fly_Xyagru.setOptionsBitmask(Options.INVULNERABLE)
	
	Drussots_Huaxoziba = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_02.iff', 'corellia', long(0),
							 float(-3324.01318359),float(86.0),float(3260.02319336),
							 float(-0.655426263809),float(0.755259335041))
	Drussots_Huaxoziba.setCustomName('Drussots Huaxoziba (a commoner)')
	Drussots_Huaxoziba.setOptionsBitmask(Options.INVULNERABLE)
	
	R3_N0  = stcSvc.spawnObject('object/mobile/shared_r3.iff', 'corellia', long(0),
							 float(-3326.89526367),float(86.0),float(3233.50366211),
							 float(0.998068630695),float(-0.0621217042208))
	R3_N0.setCustomName('R3-N0')
	R3_N0.setOptionsBitmask(Options.INVULNERABLE)
	
	Udorir_Viokl = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_01.iff', 'corellia', long(0),
							 float(-3320.28735352),float(86.0),float(3228.51708984),
							 float(0.861179411411),float(0.508301138878))
	Udorir_Viokl.setCustomName('Udorir Viokl (a commoner)')
	Udorir_Viokl.setOptionsBitmask(Options.INVULNERABLE)
	
		
	
	rogue_CorSec_trooper1 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							float(-3331.39111328),float(86.0),float(3302.44067383),
							float(-0.687127411366),float(0.726536929607))
	
	
	rogue_CorSec_trooper2 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							float(-3330.75),float(86.0),float(3291.57104492),
							float(-0.0489982105792),float(0.998798906803))
	
	
	rogue_CorSec_trooper3 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0),
							float(-3340.65600586),float(86.0),float(3291.95214844),
							float(-0.425937533379),float(0.904752612114))
	
	
	Fugu_Cyeq = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_female_01.iff', 'corellia', long(0),
							 float(-3331.94628906),float(86.0),float(3306.92089844),
							 float(0.822796583176),float(-0.568336069584))
	Fugu_Cyeq.setCustomName('Fugu Cyeq (a commoner)')
	Fugu_Cyeq.setOptionsBitmask(Options.INVULNERABLE)
	
	Denell_Kel_Vannon = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_female_01.iff', 'corellia', long(0),
							 float(-3413.91113281),float(74.0),float(3335.56860352),
							 float(0.0562224052846),float(0.998418390751))
	Denell_Kel_Vannon.setCustomName('Denell Kel''Vannon')
	Denell_Kel_Vannon.setOptionsBitmask(Options.INVULNERABLE)
	
	Izi_Eki = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_twilek_male_01.iff', 'corellia', long(0),
							 float(-3454.50927734),float(78.0),float(3343.19506836),
							 float(0.841786086559),float(0.539811134338))
	Izi_Eki.setCustomName('Izi Eki (a commoner)')
	Izi_Eki.setOptionsBitmask(Options.INVULNERABLE)
	
	Bateo_Cyar = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_twilek_male_01.iff', 'corellia', long(0),
							 float(-3572.0),float(70.0),float(3208.0),
							 float(0.848098933697),float(-0.529837846756))
	Bateo_Cyar.setCustomName('Bateo Cyar (a commoner)')
	Bateo_Cyar.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	
	Miabiox_Yaglyixaq  = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_smuggler_human_male_01.iff', 'corellia', long(0),
							 float(-3557.49),float(86.0),float(3158.11),
							 float(0.848098933697),float(-0.529837846756))
	Miabiox_Yaglyixaq .setCustomName('Miabiox Yaglyixaq (an information broker)')
	Miabiox_Yaglyixaq .setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	Xye_Jamecina  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_ishitib_male_01.iff', 'corellia', long(0),
							 float(-3591.81860352),float(86.0),float(3180.23388672),
							 float(0.994436740875),float(0.105335846543))
	Xye_Jamecina .setCustomName('Xye Jamecina (a businessman)')
	Xye_Jamecina .setOptionsBitmask(Options.INVULNERABLE)
	
	Mumiedo_Drafe  = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_elite_medic_human_male_01.iff', 'corellia', long(0),
							 float(-3591.74),float(86.0),float(3179.0),
							 float(0.105335846543),float(0.994436740875))
	Mumiedo_Drafe .setCustomName('Mumiedo Drafe (a medic)')
	Mumiedo_Drafe .setOptionsBitmask(Options.INVULNERABLE)
	
	Gazejiro_Obrepitri  = stcSvc.spawnObject('object/mobile/shared_dressed_noble_fat_human_male_02.iff', 'corellia', long(0),
							 float(-3610.27),float(86.0),float(3199.40),
							 float(0.994436740875),float(0.105335846543))
	Gazejiro_Obrepitri.setCustomName('Gazejiro Obrepitri (a noble)')
	Gazejiro_Obrepitri.setOptionsBitmask(Options.INVULNERABLE)
	
	Zoquilra_Nemuge  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_01.iff', 'corellia', long(0),
							 float(-3610.27),float(86.0),float(3198.40),
							 float(0.105335846543),float(0.994436740875))
	Zoquilra_Nemuge.setCustomName('Zoquilra Nemuge (a farmer)')
	Zoquilra_Nemuge.setOptionsBitmask(Options.INVULNERABLE)
	
	Izoujo_Megaijom  = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_male_01.iff', 'corellia', long(0),
							 float(-3602.67),float(86.0),float(3221.17),
							 float(0.105335846543),float(0.994436740875))
	Izoujo_Megaijom.setCustomName('Izoujo Megaijom (an entertainer)')
	Izoujo_Megaijom.setOptionsBitmask(Options.INVULNERABLE)
	
	bodyguard1  = stcSvc.spawnObject('object/mobile/shared_dressed_mugger.iff', 'corellia', long(0),
							 float(-3587.59594727),float(86.0),float(3134.09228516),
							 float(0.05890763551),float(0.998263478279))
	bodyguard1.setCustomName('a bodyguard')
	bodyguard1.setOptionsBitmask(Options.ATTACKABLE)
	
	
	
	
	CLL_8_3  = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'corellia', long(0),
							 float(-3654.6706543),float(86.0),float(3081.50561523),
							 float(0.263878554106),float(0.964555978775))
	CLL_8_3.setCustomName('CLL-8 binary load lifter')
	CLL_8_3.setOptionsBitmask(Options.INVULNERABLE)

	EG_6_1  = stcSvc.spawnObject('object/mobile/shared_eg6_power_droid.iff', 'corellia', long(0),
							 float(-3766.44),float(86.00),float(3180.90),
							 float(0.263878554106),float(0.964555978775))
	EG_6_1.setCustomName('EG-6 power droid')
	EG_6_1.setOptionsBitmask(Options.INVULNERABLE)
	
	Spiowhote_Quer = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_04.iff', 'corellia', long(0),
							 float(-3447.0),float(78.0),float(3219.0),
							 float(0.955809354782),float(-0.293987184763))
	Spiowhote_Quer.setCustomName('Spiowhote Quer (a commoner)')
	Spiowhote_Quer.setOptionsBitmask(Options.INVULNERABLE)
	
	brawler_1 = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_thug_human_female_01.iff', 'corellia', long(0),
							 float(-3459.55),float(77.97),float(3226.02),
							 float(0.955809354782),float(-0.293987184763))
	brawler_1.setCustomName('a brawler')
	brawler_1.setOptionsBitmask(Options.INVULNERABLE)
	
	Naputu_Cyawhal = stcSvc.spawnObject('object/mobile/shared_dressed_noble_old_human_male_01.iff', 'corellia', long(0),
							 float(-3460.53955078),float(78.0),float(3225.08105469),
							 float(0.351847827435),float(0.936057269573))
	Naputu_Cyawhal.setCustomName('Naputu Cyawhal (a noble)')
	Naputu_Cyawhal.setOptionsBitmask(Options.INVULNERABLE)
	
	Yigaraka_Faite = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_sullustan_male_06.iff', 'corellia', long(0),
							 float(-3256.00),float(86.00),float(3124.30),
							 float(0.955809354782),float(-0.293987184763))
	Yigaraka_Faite.setCustomName('Yigaraka Faite (a commoner)')
	Yigaraka_Faite.setOptionsBitmask(Options.INVULNERABLE)
	
	Ubakyinai_Ayx = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_sullustan_male_05.iff', 'corellia', long(0),
							 float(-3276.70),float(86.00),float(3089.82),
							 float(0.955809354782),float(-0.293987184763))
	Ubakyinai_Ayx.setCustomName('Yigaraka Faite (a commoner)')
	Ubakyinai_Ayx.setOptionsBitmask(Options.INVULNERABLE)
	
	Ugigi_Ayzoje = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_male_06.iff', 'corellia', long(0),
							 float(-3272.53),float(86.00),float(3071.96),
							 float(0.775923252106),float(-0.630827367306))
	Ugigi_Ayzoje.setCustomName('Ugigi Ayzoje (a commoner)')
	Ugigi_Ayzoje.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	
	
	
	
	#Med Center Interior 
	MedCenter = core.objectService.getObject(long(3375390)) 
	
	Rouciquoj_Chya  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_01.iff', 'corellia',  MedCenter.getCellByCellNumber(2),
							 float(-0.674946367741),float(0.184067219496),float(2.21024084091),
							 float(0.732300519943),float(0),float(-0.680981576443),float(0))
	Rouciquoj_Chya .setCustomName('Rouciquoj Chya (a commoner)')
	Rouciquoj_Chya .setOptionsBitmask(Options.INVULNERABLE)
	
	Grauk_Yazezor  = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_zabrak_male_02.iff', 'corellia',  MedCenter.getCellByCellNumber(2),
							 float(-2.59157586098),float(0.184067234397),float(2.28344249725),
							 float(0.718892276287),float(0),float(0.695121467113),float(0))
	Grauk_Yazezor .setCustomName('Grauk Yazezor (a commoner)')
	Grauk_Yazezor .setOptionsBitmask(Options.INVULNERABLE)
	
	R2_J0  = stcSvc.spawnObject('object/mobile/shared_r2.iff', 'corellia',  MedCenter.getCellByCellNumber(2),
							 float(-1.25240755081),float(0.184067204595),float(3.47042012215),
							 float(-0.0710527598858),float(0),float(0.997472584248),float(0))
	R2_J0.setCustomName('R2-J0')
	R2_J0.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer1_medCenter = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_assassin_human_female_01.iff', 'corellia', MedCenter.getCellByCellNumber(2), 
							 float(-0.317518740892),float(0.184067204595),float(8.21783828735),
							 float(0.0503212586045),float(0),float(0.998733103275),float(0))
	slicer1_medCenter.setCustomName('a slicer')
	slicer1_medCenter.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_agent1_medCenter = stcSvc.spawnObject('corsec_agent', 'corellia', MedCenter.getCellByCellNumber(2),
							 float(-0.172100394964),float(0.184067204595),float(6.55947399139),
							 float(0.999038815498),float(0),float(-0.0438341200352),float(0))
	#################### End Of Interior MedCenter			
	#Starport Interior
	
	building = core.objectService.getObject(long(4255419))
	CorSec_Inspector_Sergeant_Starport = stcSvc.spawnObject('corsec_inspector_sergeant', 'corellia',  building.getCellByCellNumber(12),
							 float(-63.1180114746),float(2.63941693306),float(40.2964515686),
							 float(0.48009365797),float(0),float(0.877217292786),float(0))
	
	Akuidapeo_Yimewua = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_elite_medic_human_male_01.iff', 'corellia', building.getCellByCellNumber(12),
							 float(-63.3935203552),float(2.63941693306),float(41.687122345),
							 float(0.00596272945404),float(0),float(0.999982237816),float(0)) 
	Akuidapeo_Yimewua.setCustomName('Akuidapeo Yimewua ( a Medic)')
	Akuidapeo_Yimewua.setOptionsBitmask(Options.INVULNERABLE)
	
	Byobu_Ghomaluvo = stcSvc.spawnObject('object/mobile/shared_space_chassis_broker_01.iff', 'corellia', building.getCellByCellNumber(14),
							 float(-56.7822303772),float(0.97456330061),float(8.40205574036),
							 float(0.93222540617),float(0),float(0.361877948046),float(0)) 
	Byobu_Ghomaluvo.setCustomName('Byobu Ghomaluvo (Starship Chassis Broker)')
	Byobu_Ghomaluvo.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer1_starport = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_slicer_human_female_01.iff', 'corellia', building.getCellByCellNumber(4), 
							 float(0.0457805395126),float(0.639421164989),float(61.9549484253),
							 float(0.148834854364),float(0),float(0.988862037659),float(0)) 
	slicer1_starport.setCustomName('a slicer')
	slicer1_starport.setOptionsBitmask(Options.INVULNERABLE)
	
	gambler1_starport = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_twilek_female_01.iff', 'corellia', building.getCellByCellNumber(4), 
							 float(0.0907254219055),float(0.639421105385),float(60.6564064026),
							 float(0.997990727425),float(0),float(-0.0633612126112),float(0)) 
	gambler1_starport.setCustomName('a gambler')
	gambler1_starport.setOptionsBitmask(Options.INVULNERABLE)
	
	gambler2_starport = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_twilek_female_01.iff', 'corellia', building.getCellByCellNumber(4), 
							 float(-1.05139815807),float(0.639421641827),float(61.3781585693),
							 float(0.715660512447),float(0),float(0.698448359966),float(0))
	gambler2_starport.setCustomName('a gambler')
	gambler2_starport.setOptionsBitmask(Options.INVULNERABLE)
	
	Iahusli_Dobuzu = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_elite_medic_human_male_01.iff', 'corellia', building.getCellByCellNumber(7),
							float(45.2350540161),float(-0.521138191223),float(34.7171173096),
							float(0.997193932533),float(0),float(-0.0748613998294),float(0)) 
	Iahusli_Dobuzu.setCustomName('Iahusli Dobuzu ( a Medic)')
	Iahusli_Dobuzu.setOptionsBitmask(Options.INVULNERABLE)
	
	explorer1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_02.iff', 'corellia', building.getCellByCellNumber(7),
							float(45.7087554932),float(-0.521138012409),float(36.2064552307),
							float(0.760842502117),float(0),
							float(0.6489366889),float(0))
	#explorer1.setCustomName('')
	explorer1.setOptionsBitmask(Options.ATTACKABLE)
	
	miner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_male_01.iff', 'corellia', building.getCellByCellNumber(7),
							float(45.5443992615),float(-0.521138072014),float(36.707862854),
							float(0.366610348225),float(0),
							float(0.930374622345),float(0))
	miner1.setCustomName('a miner')
	miner1.setOptionsBitmask(Options.ATTACKABLE)
	
	#Landing area of Starport
	miner_landing = stcSvc.spawnObject('object/mobile/shared_dressed_mand_miner_aqualish.iff', 'corellia', long(0),
							 float(-3162.06567383),float(31.0),float(2903.53662109),
							 float(0.995190143585),float(0.0979623347521))
	miner_landing.setCustomName('a miner')
	miner_landing.setOptionsBitmask(Options.INVULNERABLE)
	
	miner_landing2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_02.iff', 'corellia', long(0),
							float(-3163.85009766),float(31.0),float(2901.92822266),
							float(0.398814111948),float(0.917031764984))
	miner_landing2.setCustomName('a miner')
	miner_landing2.setOptionsBitmask(Options.INVULNERABLE)
	
	Mavi_Xeoseoqu = stcSvc.spawnObject('object/mobile/shared_dressed_quest_farmer.iff', 'corellia', long(0),
							 float(-3163.54321289),float(31.0),float(2903.48583984),
							 float(0.992678105831),float(0.120789788663))
	Mavi_Xeoseoqu.setCustomName('Mavi Xeoseoqu (a farmer)')
	Mavi_Xeoseoqu.setOptionsBitmask(Options.INVULNERABLE)
	
	Ulos_Yehyizyo = stcSvc.spawnObject('object/mobile/shared_dressed_entertainer_trainer_twk_male_01.iff', 'corellia', long(0),
							 float(-3100.43945312),float(31.0),float(2888.68725586),
							 float(-0.0712026059628),float(0.997461855412))
	Ulos_Yehyizyo.setCustomName('Ulos Yehyizyo (an entertainer)')
	Ulos_Yehyizyo.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer1_landing = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_pirate_human_male_01.iff', 'corellia', long(0),
							 float(-3089.89624023),float(31.0),float(2902.02929688),
							 float(0.980194985867),float(0.198034897447))
	slicer1_landing.setCustomName('a slice')
	slicer1_landing.setOptionsBitmask(Options.INVULNERABLE)
	
	Bothan1_landing = stcSvc.spawnObject('object/mobile/shared_dressed_hutt_informant_quest.iff', 'corellia', long(0),
							 float(-3089.48754883),float(31.0),float(2900.61425781),
							 float(-0.146755486727),float(0.989172875881))
	Bothan1_landing.setCustomName('Bothan information broker')
	Bothan1_landing.setOptionsBitmask(Options.INVULNERABLE)
	
	gambler1_landing = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_03.iff', 'corellia', long(0),
							 float(-3091.47436523),float(31.0),float(2901.66796875),
							 float(0.934282422066),float(0.3565338552))
	gambler1_landing.setCustomName('a gambler')
	gambler1_landing.setOptionsBitmask(Options.INVULNERABLE)
	
	################################################################
	
	
		
	
	#Cantina Interior
	# Edited on 7/19/2014
	
	building = core.objectService.getObject(long(3005396)) 
	
	
	Lasha = stcSvc.spawnObject('object/mobile/shared_dressed_lasha_bindari.iff', 'corellia', building.getCellByCellNumber(1),
							 float(39.0631713867), float(0.104999311268),
							 float(0.525127232075), float(-0.0444506034255),
							 float(0.99901163578), float(0), float(0)) 
	Lasha.setCustomName('Lasha Bindari')
	Lasha.setOptionsBitmask(Options.INVULNERABLE)
	
	
	
	merc1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_mercenary_messanger_sullustan_m.iff', 'corellia', building.getCellByCellNumber(2),
							 float(33.9964485168), float(0.104999475181),
							 float(-7.30346107483), float(0.223889902234),
							 float(0.974614441395), float(0), float(0)) 
	merc1_cantina.setCustomName('a mercenary')
	merc1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	Viqu = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_artisan_bith_male_01.iff', 'corellia', building.getCellByCellNumber(2),
							 float(34.5256996155), float(0.104999452829),
							 float(-7.92706298828), float(0.941255509853),
							 float(-0.337695538998), float(0), float(0)) 
	Viqu.setCustomName('Viqu Zemoju (an artisan)')
	Viqu.setOptionsBitmask(Options.INVULNERABLE)
	
	brawler1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_brawler_trainer_02.iff', 'corellia', building.getCellByCellNumber(5),
							 float(22.7363262177), float(-0.894991755486),
							 float(20.0875148773), float(0.996762037277),
							 float(-0.0804077535868), float(0), float(0)) 
	brawler1_cantina.setCustomName('a brawler')
	brawler1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	fringer1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_rodian_male_02.iff', 'corellia', building.getCellByCellNumber(5),
							 float(22.6583843231), float(-0.894991755486),
							 float(21.3311824799), float(-0.0531293153763),
							 float(0.998587667942), float(0), float(0)) 
	fringer1_cantina.setCustomName('a fringer')
	fringer1_cantina.setOptionsBitmask(Options.ATTACKABLE)
	
	Luwetul = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'corellia', building.getCellByCellNumber(5),
							 float(21.5881118774), float(-0.894991755486),
							 float(21.0014343262), float(0.722301185131), 
							 float(0), float(0.691578567028), float(0)) 
	Luwetul.setCustomName('Luwetul Urequ (a commoner)')
	Luwetul.setOptionsBitmask(Options.INVULNERABLE)
	
	Hoysryuw = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_female_05.iff', 'corellia', building.getCellByCellNumber(3),
							 float(6.95027732849), float(-0.894991517067),
							 float(-2.20286750793), float(0.214196294546), 
							 float(0.97679066658), float(0), float(0)) 
	Hoysryuw.setCustomName('Hoysryuw Ufuanit (a Bartender)')
	Hoysryuw.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Chief1_cantina = stcSvc.spawnObject('corsec_chief', 'corellia', building.getCellByCellNumber(4),
							 float(20.4221305847), float(-0.894996166229),
							 float(-17.7232723236), float(0.0389355011284), 
							 float(0.999241828918), float(0), float(0)) 
	#CorSec_Chief1_cantina.setCustomName('a CorSec Chief')
	#CorSec_Chief1_cantina.setOptionsBitmask(Options.ATTACKABLE)
	
	miner1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_mand_miner_hum_02.iff', 'corellia', building.getCellByCellNumber(4),
							 float(20.5276184082), float(-0.894995987415),
							 float(-19.5976524353), float(0.994058728218),
							 float(0), float(-0.108844988048), float(0)) 
	miner1_cantina.setCustomName('a miner')
	miner1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	Dutidi = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_female_02.iff', 'corellia', building.getCellByCellNumber(6), 
						    float(10.9009456635), float(-0.894991755486),
							float(-15.4980859756), float(0.999989688396), 
							float(0), float(-0.00455114850774), float(0)) 
	Dutidi.setCustomName('Dutidi Timaremeg (a farmer)')
	Dutidi.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_slicer_human_female_01.iff', 'corellia', building.getCellByCellNumber(6), 
							 float(10.8570604324), float(-0.894991517067),
							 float(-13.9565658569), float(0.118118427694), 
							 float(0), float(0.992999613285), float(0)) 
	slicer1_cantina.setCustomName('a slicer')
	slicer1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	Bryizonoo = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_human_female_01.iff', 'corellia', building.getCellByCellNumber(15),
							 float(-44.3342094421),float(0.105008669198),
							 float(-21.9658203125),float(0.999781668186),
							 float(0),float(-0.0208939183503),float(0))
	Bryizonoo.setCustomName('Bryizonoo Pajanan (a commoner)')
	Bryizonoo.setOptionsBitmask(Options.INVULNERABLE)
	
	rogue_CorSec_trooper_cantina = stcSvc.spawnObject('corsec_lieutenant', 'corellia', building.getCellByCellNumber(15),
							 float(-44.4299125671), float(0.104664899409),
							 float(-19.6749382019),float(-0.0956733301282),
							 float(0),float(0.995412766933),float(0))
	rogue_CorSec_trooper_cantina.setCustomName('a rogue CorSec trooper ')
	rogue_CorSec_trooper_cantina.setOptionsBitmask(Options.ATTACKABLE)
	
	skinkner1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_skinkner.iff', 'corellia', building.getCellByCellNumber(7),
							 float(8.05954837799), float(-0.894991755486),
							 float(22.2462425232), float(0.346195280552), 
							 float(0), float(0.938162446022), float(0)) 
	skinkner1_cantina.setCustomName('Skinkner')
	skinkner1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	gambler1_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_nikto_male_02.iff', 'corellia', building.getCellByCellNumber(9),
							 float(-2.5893881321), float(-0.894993782043),
							 float(22.9540920258), float(0.9990234375), 
							 float(0), float(-0.0441829189658), float(0)) 
	gambler1_cantina.setCustomName('a gambler')
	gambler1_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	brawler2_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_desperado_bith_male_01.iff', 'corellia', building.getCellByCellNumber(9),
							 float(-2.61188316345), float(-0.894991755486),
							 float(24.5507926941), float(0.211504951119), 
							 float(0), float(0.977376997471), float(0)) 
	brawler2_cantina.setCustomName('a brawler')
	brawler2_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	slicer2_cantina = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_assassin_human_female_01.iff', 'corellia', building.getCellByCellNumber(13),
							float(-25.6314811707), float(-0.519990563393),
							float(11.3749580383), float(0.0208092723042), 
							 float(0) , float(0.999783456326), float(0)) 
	slicer2_cantina.setCustomName('a slicer')
	slicer2_cantina.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Commissioner_cantina = stcSvc.spawnObject('corsec_commissioner', 'corellia', building.getCellByCellNumber(13),
							 float(-25.556886673),float(-0.519990563393),
							 float(10.0968637466),float(0.999893128872),
							 float(0),float(-0.0146219246089),float(0))
	CorSec_Commissioner_cantina.setCustomName('a CorSec Commissioner ')
	#CorSec_Commissioner_cantina.setOptionsBitmask(Options.ATTACKABLE)
	
	
	
	# ------------------- End of Cantina NPC'S
	# ------------------- Cloning Center Interior
	
	Cloning_Center = core.objectService.getObject(long(3005426))
	
	CorSec_Chief1_Cloning_Center = stcSvc.spawnObject('corsec_chief', 'corellia', Cloning_Center.getCellByCellNumber(2),
							float(4.3115940094),float(-0.0513746365905),float(-3.38994717598),
							float(0.999990940094),float(0),float(-0.00425739679486),float(0))
	
	slicer1_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_pirate_human_male_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(2),
							float(4.21447372437),float(-0.0513746365905),float(-1.60490179062),
							float(0.0658825933933),float(0),float(0.997827351093),float(0))
	slicer1_Cloning_Center.setCustomName('a slicer')
	slicer1_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	miner1_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_female_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(2),
							float(5.35788059235),float(-0.0513746365905),float(1.65928137302),
							float(0.993703305721),float(0),float(-0.112043380737),float(0))
	miner1_Cloning_Center.setCustomName('a miner')
	miner1_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	broker1_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_eisley_officer_bothan_female_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(2),
							float(5.27716112137),float(-0.0513746365905),float(3.12595248222),
							float(0.104398600757),float(0),float(0.994535565376),float(0))
	broker1_Cloning_Center.setCustomName('a Bothan information broker')
	broker1_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	diplomat1_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_eisley_officer_bothan_female_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(2),
							float(4.31655788422),float(-0.0513746365905),float(2.54979276657),float(0.425313532352),
							float(0),float(0.905046105385),float(0))
	diplomat1_Cloning_Center.setCustomName('a Bothan diplomat')
	diplomat1_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	Feedo_Zuvazeb_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_diplomat_human_male_02.iff', 'corellia', Cloning_Center.getCellByCellNumber(4),
							float(4.7659406662),float(-5.5),float(-8.42499351501),float(0.998970448971),
							float(0),float(-0.0453660972416),float(0))
	Feedo_Zuvazeb_Cloning_Center.setCustomName('Feedo Zuvazeb (a businessman)')
	Feedo_Zuvazeb_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	diplomat2_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_eisley_officer_bothan_female_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(4),
							float(4.66340637207),float(-5.5),float(-7.29698801041),
							float(0.0684288367629),float(0),float(0.99765598774),float(0))
	diplomat2_Cloning_Center.setCustomName('a Bothan diplomat')
	diplomat2_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	Brawler1_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_criminal_thug_human_female_01.iff', 'corellia', Cloning_Center.getCellByCellNumber(4),
							float(2.81334352493),float(-5.5),float(-7.4947347641),
							float(0.537544429302),float(0),float(0.843235254288),float(0))
	Brawler1_Cloning_Center.setCustomName('a Brawler')
	Brawler1_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	Wegas_Equi_Cloning_Center = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_aqualish_female_04.iff', 'corellia', Cloning_Center.getCellByCellNumber(4),
							float(-5.33185815811),float(-5.5),float(-10.7494421005),float(0.999544918537),
							float(0),float(-0.0301669035107),float(0))
	Wegas_Equi_Cloning_Center.setCustomName('Wegas Equi (a rancher)')
	Wegas_Equi_Cloning_Center.setOptionsBitmask(Options.INVULNERABLE)
	
	CorSec_Agent1_Cloning_Center = stcSvc.spawnObject('corsec_agent', 'corellia', Cloning_Center.getCellByCellNumber(4),
							float(-5.38953495026),float(-5.5),float(-9.79256629944),
							float(-0.641088664532),float(0),float(0.767466783524),float(0))
	
	
	
	
	
	#################### End Cloning Center Interior
	
	
	building = core.objectService.getObject(long(2955418))
	JunkDealer = stcSvc.spawnObject('object/mobile/shared_junk_sneg.iff', 'corellia', building.getCellByCellNumber(6),
							 float(-14.2290649414),float(1.13305664062),float(2.77622461319),
							 float(0.755741894245),float(0),float(0.654869616032),float(0))
	
	##### Guild Hall KorVella
	GuildHall = core.objectService.getObject(long(2955408))
	miner1 = stcSvc.spawnObject('object/mobile/shared_dressed_mand_miner_hum_03.iff', 'corellia', GuildHall.getCellByCellNumber(8),
							 float(3.35150575638),float(1.13305664062),float(-9.4189119339),
							 float(-0.584942698479),float(0),float(0.811074733734),float(0))
	miner1.setCustomName('a miner')
	miner1.setOptionsBitmask(Options.INVULNERABLE)
	
	#################################
	
	
	
	
	
	
	
	
	
	#korvella
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-3133.96044922),float(31.0),float(2972.92773438),float(0.0107700955123),float(0.999942004681))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-3567), float(86), float(3143), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-3788), float(86), float(-3190), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-3390), float(86), float(-3288), float(0), float(1))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-3151), float(31), float(2790), float(0.71), float(0.71))
	
	

	return	

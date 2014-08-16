import sys
from resources.datatables import Options
from resources.datatables import State
from resources.datatables import StateStatus
from java.util import Vector
from engine.resources.scene import Point3D

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService
	
	#---Starport Landing
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3357.0879), float(308.0000), float(5638.9761), float(0.1636), float(0.0000), float(0.9865), float(0.0000))
	commoner02 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3414.4688), float(308.0000), float(5623.7598), float(-0.5839), float(0.0000), float(0.8118), float(0.0000))
	
	#---Starport interrior
	starport = core.objectService.getObject(long(9665352))
	
	noble01 = stcSvc.spawnObject('noble', 'corellia', starport.getCellByCellNumber(long(13)), float(48.3856), float(0.9746), float(22.6336), float(0.9175), float(0.0000), float(0.3978), float(0.0000))

	bothan_dip01 = stcSvc.spawnObject('bothan_diplomat', 'corellia', starport.getCellByCellNumber(long(7)), float(37.8076), float(0.6394), float(41.7707), float(-0.0005), float(0.0000), float(1.0000), float(0.0000))
	
	farmer01 = stcSvc.spawnObject('farmer', 'corellia', starport.getCellByCellNumber(long(7)), float(37.9857), float(0.6394), float(40.0040), float(0.9986), float(0.0000), float(-0.0537), float(0.0000))
	farmer02 = stcSvc.spawnObject('farmer', 'corellia', starport.getCellByCellNumber(long(12)), float(-62.6612), float(2.6394), float(41.9996), float(0.0274), float(0.0000), float(0.9996), float(0.0000))
	
	explorer01 = stcSvc.spawnObject('explorer', 'corellia', starport.getCellByCellNumber(long(12)), float(-62.5357), float(2.6394), float(40.5378), float(0.9784), float(0.0000), float(-0.2068), float(0.0000))
	

	
	busMan01 = stcSvc.spawnObject('businessman', 'corellia', starport.getCellByCellNumber(long(7)), float(52.4243), float(0.6394), float(48.5247), float(-0.0609), float(0.0000), float(0.9981), float(0.0000))
	
	slicer01 = stcSvc.spawnObject('slicer', 'corellia', starport.getCellByCellNumber(long(4)), float(1.7211), float(0.6394), float(66.6282), float(0.9929), float(0.0000), float(0.1186), float(0.0000))
	
	brawler01 = stcSvc.spawnObject('brawler', 'corellia', starport.getCellByCellNumber(long(4)), float(-4.6357), float(0.6394), float(61.5154), float(0.0589), float(0.0000), float(0.9983), float(0.0000))
	
	cor_commiss01 = stcSvc.spawnObject('corsec_commissioner', 'corellia', starport.getCellByCellNumber(long(4)), float(-4.4543), float(0.6394), float(60.1750), float(0.9992), float(0.0000), float(-0.0401), float(0.0000))
	
	#---Doaba outside
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3139.2620), float(300.0000), float(5246.5532), float(0.9364), float(0.0000), float(0.3509), float(0.0000))
	commoner02 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3320.1372), float(324.0000), float(5709.1797), float(0.2493), float(0.0000), float(0.9684), float(0.0000))
	commoner03 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3306.9360), float(308.0602), float(5618.7446), float(-0.5475), float(0.0000), float(0.8368), float(0.0000))
	commoner04 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3295.9290), float(324.0000), float(5759.2920), float(-0.1815), float(0.0000), float(0.9834), float(0.0000))
	commoner05 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3385.3269), float(308.0000), float(5699.8477), float(0.8636), float(0.0000), float(-0.5042), float(0.0000))
	commoner06 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3107.5591), float(300.0000), float(5229.1519), float(0.7975), float(0.0000), float(0.6033), float(0.0000))
	commoner07 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3179.8054), float(300.0000), float(5213.3833), float(-0.5768), float(0.0000), float(0.8169), float(0.0000))
	commoner08 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3116.4871), float(300.0000), float(5194.3721), float(0.8461), float(0.0000), float(0.5331), float(0.0000))
	commoner09 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3411.3010), float(308.0000), float(5514.5981), float(-0.6279), float(0.0000), float(0.7783), float(0.0000))
	commoner10 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3315.2837), float(308.0000), float(5496.3877), float(1.0000), float(0.0000), float(0.0099), float(0.0000))
	commoner11 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3323.0273), float(308.0000), float(5482.7817), float(0.9583), float(0.0000), float(-0.2857), float(0.0000))
	commoner12 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3276.2700), float(300.0000), float(5438.4839), float(0.9385), float(-0.0000), float(-0.3453), float(0.0000))
	commoner13 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3238.8445), float(300.0000), float(5413.7866), float(0.2354), float(0.0000), float(0.9719), float(0.0000))
	commoner14 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3308.0959), float(300.0000), float(5395.9321), float(0.7810), float(0.0000), float(-0.6246), float(0.0000))
	commoner15 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3199.4314), float(300.0000), float(5449.1846), float(0.2493), float(0.0000), float(0.9684), float(0.0000))
	commoner16 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3158.5447), float(300.0000), float(5397.6260), float(0.4421), float(0.0000), float(0.8970), float(0.0000))
	commoner17 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3189.9250), float(300.0000), float(5268.2456), float(1.0000), float(0.0000), float(0.0067), float(0.0000))
	commoner18 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3102.7073), float(300.0000), float(5164.8354), float(0.7326), float(0.0000), float(0.6806), float(0.0000))
	commoner19 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3192.6235), float(302.7468), float(5112.9141), float(0.7511), float(0.0000), float(-0.6601), float(0.0000))
	commoner20 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3202.1958), float(290.0000), float(5033.8560), float(0.2288), float(0.0000), float(0.9735), float(0.0000))
	commoner21 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3203.8254), float(290.0000), float(5002.0566), float(0.9997), float(0.0000), float(-0.0253), float(-0.0000))
	commoner22 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(3161.8572), float(290.0000), float(4966.0811), float(0.8088), float(0.0000), float(0.5881), float(0.0000))
	
	farmer01 = stcSvc.spawnObject('farmer', 'corellia', long(0), float(3145.8169), float(290.0000), float(4995.1577), float(-0.2259), float(0.0000), float(0.9741), float(0.0000))
	
	hunter01 = stcSvc.spawnObject('hunter', 'corellia', long(0), float(3145.6782), float(289.9998), float(4993.4551), float(1.0000), float(0.0000), float(0.0088), float(0.0000))
	
	
	
	gambler01 = stcSvc.spawnObject('gambler', 'corellia',long(0), float(3183.9673), float(300.0000), float(5162.3828), float(-0.5356), float(0.0000), float(0.8445), float(0.0000))
	gambler02 = stcSvc.spawnObject('gambler', 'corellia',long(0), float(3181.5857), float(300.0000), float(5161.1270), float(0.9124), float(0.0000), float(0.4094), float(0.0000))
	
	rancher01 = stcSvc.spawnObject('farmer_rancher', 'corellia',long(0), float(3195.5637), float(294.9913), float(5073.7783), float(0.9960), float(0.0000), float(0.0888), float(0.0000))
	rancher02 = stcSvc.spawnObject('farmer_rancher', 'corellia',long(0), float(3195.9800), float(295.1579), float(5074.7144), float(-0.1407), float(0.0000), float(0.9900), float(0.0000))
	
	bothan_info_bk01 = stcSvc.spawnObject('bothan_information_broker', 'corellia',long(0), float(3202.1929), float(290.0000), float(4989.4224), float(0.8756), float(0.0000), float(0.4831), float(0.0000))
	
	
	
	noble01 = stcSvc.spawnObject('noble', 'corellia',long(0), float(3158.2246), float(300.0000), float(5352.3330), float(0.6411), float(0.0000), float(0.7674), float(0.0000))
	

	cor_Master_serg01 = stcSvc.spawnObject('corsec_master_sergeant', 'corellia',long(0), float(3300.2244), float(308.0000), float(5494.5674), float(0.9997), float(0.0000), float(0.0256), float(0.0000))
	
	cor_Chief01 = stcSvc.spawnObject('corsec_chief', 'corellia',long(0), float(3121.1323), float(285.0001), float(5006.1870), float(-0.6258), float(0.0000), float(0.7800), float(0.0000))
	
	cor_Trooper01 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(3119.5518), float(285.0000), float(5002.1099), float(0.9669), float(0.0000), float(-0.2550), float(0.0000))
	
	#----Guild Hall interrior
	guildHall =  core.objectService.getObject(long(4395395))
	
	artisan01 = stcSvc.spawnObject('artisan', 'corellia', guildHall.getCellByCellNumber(long(1)), float(-0.7202), float(0.6000), float(-3.5621), float(0.0600), float(0.0000), float(0.9982), float(0.0000))
	artisan02 = stcSvc.spawnObject('artisan', 'corellia', guildHall.getCellByCellNumber(long(6)), float(1.1595), float(2.6000), float(4.0230), float(0.0362), float(0.0000), float(0.9993), float(0.0000))
	artisan03 = stcSvc.spawnObject('artisan', 'corellia', guildHall.getCellByCellNumber(long(9)), float(-19.1429), float(2.1288), float(66.5565), float(-0.6740), float(0.0000), float(0.7387), float(0.0000))
	
	bodyGuard01 = stcSvc.spawnObject('bodyguard', 'corellia', guildHall.getCellByCellNumber(long(9)), float(-21.0123), float(2.1288), float(65.0031), float(0.8684), float(0.0000), float(0.4959), float(0.0000))
	

	bothan_info_bk01 = stcSvc.spawnObject('bothan_information_broker', 'corellia', guildHall.getCellByCellNumber(long(1)), float(-0.6576), float(0.6000), float(-4.9845), float(0.9990), float(0.0000), float(-0.0442), float(0.0000))
	bothan_info_bk02 = stcSvc.spawnObject('bothan_information_broker', 'corellia', guildHall.getCellByCellNumber(long(2)), float(-23.4785), float(0.6000), float(-3.9132), float(0.9995), float(0.0000), float(-0.0312), float(-0.0000))
	
	rancher01 = stcSvc.spawnObject('farmer_rancher', 'corellia',guildHall.getCellByCellNumber(long(2)), float(-23.6074), float(0.6000), float(-2.0499), float(0.0008), float(0.0000), float(1.0000), float(0.0000))
	
	merc01 = stcSvc.spawnObject('mercenary', 'corellia',guildHall.getCellByCellNumber(long(6)), float(1.0394), float(2.6000), float(2.1427), float(0.9996), float(0.0000), float(-0.0274), float(0.0000))
	merc02 = stcSvc.spawnObject('mercenary', 'corellia',guildHall.getCellByCellNumber(long(9)), float(-20.4949), float(2.1288), float(72.5500), float(0.9980), float(0.0000), float(-0.0630), float(0.0000))
	
	hunter01  = stcSvc.spawnObject('hunter', 'corellia',guildHall.getCellByCellNumber(long(9)), float(-22.3099), float(2.1288), float(72.2873), float(0.9890), float(0.0000), float(0.1477), float(0.0000))
	
	
	
	noble01 = stcSvc.spawnObject('noble', 'corellia',guildHall.getCellByCellNumber(long(6)), float(15.4912), float(2.6000), float(22.3318), float(0.9999), float(0.0000), float(-0.0166), float(0.0000))
	noble02 = stcSvc.spawnObject('noble', 'corellia',guildHall.getCellByCellNumber(long(7)), float(29.2607), float(2.1288), float(57.5845), float(0.9986), float(0.0000), float(-0.0529), float(0.0000))
	noble03 = stcSvc.spawnObject('noble', 'corellia',guildHall.getCellByCellNumber(long(8)), float(19.3232), float(2.1284), float(56.7834), float(-0.6967), float(0.0000), float(0.7173), float(0.0000))
	
	
	ent01 = stcSvc.spawnObject('entertainer', 'corellia',guildHall.getCellByCellNumber(long(6)), float(15.4720), float(2.4204), float(24.4234), float(-0.0107), float(0.0000), float(0.9999), float(0.0000))
	
	farmer01 = stcSvc.spawnObject('farmer', 'corellia',guildHall.getCellByCellNumber(long(6)), float(25.5611), float(0.8520), float(41.5122), float(0.7143), float(0.0000), float(-0.6998), float(0.0000))
	farmer02 = stcSvc.spawnObject('farmer', 'corellia',guildHall.getCellByCellNumber(long(6)), float(-16.3688), float(0.7791), float(42.2555), float(0.9999), float(0.0000), float(-0.0105), float(0.0000))
	farmer03 = stcSvc.spawnObject('farmer', 'corellia',guildHall.getCellByCellNumber(long(6)), float(-16.2866), float(0.6000), float(44.7492), float(0.2051), float(0.0000), float(0.9788), float(0.0000))
	farmer04 = stcSvc.spawnObject('farmer', 'corellia',guildHall.getCellByCellNumber(long(8)), float(2.8824), float(2.1288), float(74.8993), float(0.7154), float(0.0000), float(0.6987), float(0.0000))
	
	fringer01 = stcSvc.spawnObject('fringer', 'corellia',guildHall.getCellByCellNumber(long(9)), float(-20.6990), float(2.1288), float(74.5990), float(0.0650), float(0.0000), float(0.9979), float(0.0000))
	
	
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',guildHall.getCellByCellNumber(long(6)), float(23.5834), float(0.8465), float(41.5697), float(0.7410), float(0.0000), float(0.6715), float(0.0000))
	
	infor_bk01  = stcSvc.spawnObject('info_broker', 'corellia',guildHall.getCellByCellNumber(long(6)), float(-13.2984), float(2.3030), float(25.7022), float(0.0369), float(0.0000), float(0.9993), float(0.0000))
	
	cor_MS_Serg01  = stcSvc.spawnObject('corsec_master_sergeant', 'corellia',guildHall.getCellByCellNumber(long(6)), float(-13.3215), float(2.4728), float(23.8521), float(0.9994), float(0.0000), float(-0.0351), float(0.0000))
	
	cor_trooper01  = stcSvc.spawnObject('corsec_trooper', 'corellia',guildHall.getCellByCellNumber(long(8)), float(5.3869), float(2.1288), float(74.9699), float(-0.6691), float(0.0000), float(0.7432), float(0.0000))
	
	#----MedCenter interrior
	medCenter =  core.objectService.getObject(long(4345352))
	
	#surgical_droid01 = stcSvc.spawnObject('storyteller_surgical_droid_21b', 'corellia', medCenter.getCellByCellNumber(long(2)), float(-2.3210), float(0.1841), float(-1.7199), float(0.7357), float(0.0000), float(0.6774), float(0.0000))
	
	cor_Serg01  = stcSvc.spawnObject('corsec_sergeant', 'corellia',medCenter.getCellByCellNumber(long(2)), float(-3.8589), float(0.1841), float(-5.3977), float(0.0714), float(0.0000), float(0.9974), float(0.0000))
	
	bothan_info_bk01 = stcSvc.spawnObject('bothan_information_broker', 'corellia',medCenter.getCellByCellNumber(long(2)), float(-3.8889), float(0.1841), float(-7.3088), float(0.9998), float(0.0000), float(-0.0217), float(0.0000))
	
	#----Cantina interrior
	cantina =  core.objectService.getObject(long(3075426))
	
	slicer01 = stcSvc.spawnObject('slicer', 'corellia', cantina.getCellByCellNumber(long(1)), float(40.8687), float(0.1050), float(2.6624), float(0.7342), float(0.0000), float(0.6790), float(0.0000))
	
	cor_Invest01 = stcSvc.spawnObject('corsec_investigator', 'corellia', cantina.getCellByCellNumber(long(1)), float(41.7222), float(0.1050), float(2.7356), float(-0.6805), float(0.0000), float(0.7328), float(0.0000))
	
	cor_Sergeant01 = stcSvc.spawnObject('corsec_sergeant', 'corellia', cantina.getCellByCellNumber(long(3)), float(9.5931), float(-0.8950), float(7.1372), float(0.9999), float(0.0000), float(-0.0105), float(0.0000))
	
	rancher01 = stcSvc.spawnObject('farmer_rancher', 'corellia', cantina.getCellByCellNumber(long(3)), float(9.4579), float(-0.8950), float(9.1989), float(-0.0089), float(0.0000), float(1.0000), float(0.0000))
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',cantina.getCellByCellNumber(long(7)), float(6.0070), float(-0.8950), float(20.6317), float(0.0600), float(0.0000), float(0.9982), float(0.0000))
	
	cor_Commiss01 = stcSvc.spawnObject('corsec_commissioner', 'corellia',cantina.getCellByCellNumber(long(4)), float(22.0523), float(-0.8950), float(-14.8453), float(0.0076), float(0.0000), float(1.0000), float(0.0000))
	
	ent01 = stcSvc.spawnObject('entertainer', 'corellia',cantina.getCellByCellNumber(long(4)), float(22.1271), float(-0.8950), float(-16.2825), float(0.9997), float(0.0000), float(-0.0224), float(0.0000))
	
	bar01 = stcSvc.spawnObject('bartender', 'corellia',cantina.getCellByCellNumber(long(3)), float(-3.2184), float(-0.8950), float(-2.4958), float(0.0718), float(0.0000), float(0.9974), float(0.0000))
	
	bhunter01 = stcSvc.spawnObject('bounty_hunter', 'corellia',cantina.getCellByCellNumber(long(15)), float(-41.4447), float(0.1050), float(-25.2014), float(0.9807), float(0.0000), float(-0.1956), float(-0.0000))
	
	bothan_dip01 = stcSvc.spawnObject('bothan_diplomat', 'corellia',cantina.getCellByCellNumber(long(15)), float(-41.9125), float(0.1050), float(-23.2441), float(0.0421), float(0.0000), float(0.9991), float(0.0000))
	
	

	
	#doaba guerfel
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(3190), float(300.2), float(5387), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(3227), float(300.2), float(5387), float(0.71), float(-0.71))

	return	

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

#-Coronet
	ralMundi = stcSvc.spawnObject('ral_mundi', 'corellia', long(0), float(-138.975), float(28), float(-4718.86), float(-0.97), float(0.21))	
	ioTsomcren = stcSvc.spawnObject('io_tsomcren', 'corellia', long(0), float(-140.701), float(28), float(-4719.16), float(-0.97), float(0.21))
	tarthJax = stcSvc.spawnObject('tarth_jax', 'corellia', long(0), float(-137.464), float(28), float(-4718.83), float(-0.97), float(0.21))	
	rebCoord = stcSvc.spawnObject('rebel_coordinator', 'corellia', long(0), float(94.8749), float(28), float(-4519.08), float(-0.97), float(0.21))		
	huntrJavz = stcSvc.spawnObject('hunter_javeezo', 'corellia', long(0), float(-51.79), float(28), float(-4662.65), float(0.11), float(0.21))
	dw_heraldRebel = stcSvc.spawnObject('lutin_nightstalker', 'corellia', long(0), float(-213.64), float(28), float(-4445.46), float(-0.97), float(0.21))
	
	cor_CorSec_Tr1 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0), float(-347.3138), float(28.0000), float(-4442.0034), float(0.9979), float(0.0000), float(-0.0641), float(0.0000))
	cor_CorSec_Tr2 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-344.6161), float(28.0000), float(-4444.0317), float(0.9976), float(0.0000), float(0.0687), float(0.0000))
	cor_CorSec_Tr3 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-171.6331), float(28.0000), float(-4165.8228), float(0.9997), float(0.0000), float(-0.0253), float(0.0000))
	cor_CorSec_Tr4 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-171.6331), float(28.0000), float(-4165.8228), float(0.9997), float(0.0000), float(-0.0253), float(0.0000))
	cor_CorSec_Tr5 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-4.0739), float(28.0000), float(-4463.5615), float(0.6774), float(0.0000), float(0.7357), float(0.0000))
	cor_CorSec_Tr6 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-240.9703), float(28.0000), float(-4449.7622), float(0.7342), float(0.0000), float(0.6789), float(0.0000))
	cor_CorSec_Tr7 = stcSvc.spawnObject('corsec_trooper', 'corellia',long(0), float(-59.8208), float(28.5179), float(-4595.8525), float(-0.6281), float(0.0000), float(0.7781), float(0.0000))
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-283.0829), float(28.0000), float(-4343.2593), float(-0.1113), float(0.0000), float(0.9938), float(0.0000))
	commoner02 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-349.6332), float(28.0000), float(-4287.4580), float(0.9354), float(0.0000), float(-0.3536), float(0.0000))
	commoner03 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-356.8634), float(28.0000), float(-4266.9824), float(0.9404), float(0.0000), float(0.3400), float(0.0000))
	commoner04 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-350.6947), float(28.0000), float(-4249.0435), float(-0.2276), float(0.0000), float(0.9738), float(0.0000))
	commoner05 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-349.4394), float(28.0000), float(-4218.3232), float(-0.4209), float(0.0000), float(0.9071), float(0.0000))
	commoner06 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-55.5174), float(28.0000), float(-4228.8154), float(-0.1285), float(0.0000), float(0.9917), float(0.0000))
	commoner07 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-162.6287), float(28.0000), float(-4181.4468), float(0.9660), float(0.0000), float(-0.2584), float(0.0000))
	commoner08 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-77.4451), float(28.0000), float(-4286.5864), float(0.9841), float(0.0000), float(0.1776), float(0.0000))
	commoner09 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-112.5358), float(28.0000), float(-4310.4663), float(0.9065), float(0.0000), float(0.4222), float(0.0000))
	commoner10 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-86.7089), float(28.0000), float(-4301.9307), float(-0.5301), float(0.0000), float(0.8479), float(0.0000))
	commoner11 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-69.8353), float(28.0000), float(-4324.9697), float(0.1599), float(0.0000), float(0.9871), float(0.0000))
	commoner12 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-53.5411), float(28.0000), float(-4303.9351), float(0.8875), float(0.0000), float(-0.4607), float(0.0000))
	commoner13 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-117.5260), float(28.0000), float(-4284.7837), float(-0.6495), float(0.0000), float(0.7604), float(0.0000))
	commoner14 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-122.3031), float(28.0000), float(-4393.3867), float(0.6083), float(0.0000), float(0.7937), float(0.0000))
	commoner15 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-64.5749), float(28.0000), float(-4397.8574), float(0.7303), float(0.0000), float(0.6831), float(0.0000))
	commoner16 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-12.8610), float(28.0000), float(-4415.4233), float(0.7263), float(0.0000), float(0.6874), float(0.0000))
	commoner17 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(15.0293), float(28.0000), float(-4407.1909), float(-0.6966), float(0.0000), float(0.7174), float(0.0000))
	commoner18 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(39.8424), float(28.0000), float(-4415.6084), float(0.5633), float(0.0000), float(0.8263), float(0.0000))
	commoner19 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-14.1186), float(28.0000), float(-4452.8408), float(-0.0864), float(0.0000), float(0.9963), float(0.0000))
	commoner20 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-54.6331), float(28.0000), float(-4529.0942), float(-0.2526), float(0.0000), float(0.9676), float(0.0000))
	commoner21 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-25.5423), float(28.0000), float(-4558.5298), float(0.4044), float(0.0000), float(0.9146), float(0.0000))
	commoner22 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(10.1283), float(28.0000), float(-4633.5830), float(-0.6180), float(0.0000), float(0.7862), float(0.0000))
	commoner23 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(71.6505), float(28.0000), float(-4567.3335), float(0.6603), float(0.0000), float(0.7510), float(0.0000))
	commoner23.setCustomName('Roebyumu Bot')
	commoner24 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(101.1723), float(28.0000), float(-4554.0181), float(0.8671), float(0.0000), float(0.4982), float(0.0000))
	commoner25 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(87.9861), float(28.0000), float(-4726.8359), float(-0.5040), float(0.0000), float(0.8637), float(0.0000))
	commoner26 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(56.8984), float(28.0000), float(-4782.4414), float(0.8616), float(0.0000), float(-0.5077), float(0.0000))
	commoner27 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-163.5970), float(28.0000), float(-4605.3096), float(0.0348), float(0.0000), float(0.9994), float(0.0000))
	commoner28 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-162.7145), float(28.0000), float(-4551.6558), float(0.9990), float(0.0000), float(-0.0453), float(-0.0000))
	commoner29 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-156.9207), float(28.0000), float(-4525.1318), float(-0.1777), float(0.0000), float(0.9841), float(0.0000))
	commoner30 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-168.3703), float(28.0000), float(-4516.5913), float(-0.6776), float(0.0000), float(0.7354), float(0.0000))
	
	busMan01 = stcSvc.spawnObject('businessman', 'corellia',long(0), float(-313.0182), float(28.0000), float(-4647.4194), float(-0.6317), float(0.0000), float(0.7752), float(0.0000))
	
	cor_agent1 = stcSvc.spawnObject('corsec_agent', 'corellia',long(0), float(-202.4406), float(28.0000), float(-4504.6704), float(1.0000), float(0.0000), float(0.0089), float(0.0000))
	
	cor_agent1 = stcSvc.spawnObject('corsec_agent', 'corellia',long(0), float(-202.4406), float(28.0000), float(-4504.6704), float(1.0000), float(0.0000), float(0.0089), float(0.0000))
	
	cll8binarylifter = stcSvc.spawnObject('cll8binarylifter', 'corellia',long(0), float(-149.1503), float(28.0000), float(-4739.1738), float(0.9914), float(0.0000), float(0.1312), float(0.0000))
	aiSvc.setLoiter(cll8binarylifter, float(3), float(25))
	
	R3S1 = stcSvc.spawnObject('R3S1', 'corellia',long(0), float(-181.1660), float(28.0000), float(-4730.8774), float(0.7541), float(0.0000), float(-0.6567), float(0.0000))
	aiSvc.setLoiter(R3S1, float(4), float(60))

	mouseDroid1 = stcSvc.spawnObject('mouse_droid', 'corellia',long(0), float(-191.1187), float(28.0000), float(-4742.2847), float(-0.2080), float(0.0000), float(0.9781), float(0.0000))
	

#---Coronet-CityHall-interrior
	cityHall = core.objectService.getObject(long(1855460))
	
	brantlee = stcSvc.spawnObject('brantlee_spondoon', 'corellia', cityHall.getCellByCellNumber(3), float(-26.0),float(1.29),float(-0.52),float(0.14),float(0),float(0.98),float(0))
	
	daclif = stcSvc.spawnObject('daclif_gallamby', 'corellia', cityHall.getCellByCellNumber(long(7)),	float(-35.4381), float(1.2942),	float(-1.6650), float(0.7298), float(0.0000), float(0.6836), float(0.0000))
	
	cor_detec1 = stcSvc.spawnObject('corsec_detective', 'corellia', cityHall.getCellByCellNumber(long(8)),float(-20.3867), float(3.2232), float(22.2275), float(0.0070), float(0.0000), float(1.0000), float(0.0000))
	
	farmer01 = stcSvc.spawnObject('farmer', 'corellia', cityHall.getCellByCellNumber(long(8)), float(-20.3785), float(3.2232), float(21.1333), float(0.9997), float(0.0000), float(0.0233), float(0.0000))
	
	cor_LT1 = stcSvc.spawnObject('corsec_lieutenant', 'corellia', cityHall.getCellByCellNumber(long(9)), float(21.7037), float(3.2232), float(24.9946), float(0.9999), float(0.0000), float(0.0110), float(0.0000))
	
	info_Bk1 = stcSvc.spawnObject('info_broker', 'corellia', cityHall.getCellByCellNumber(long(9)), float(21.6461), float(3.2232), float(26.2573), float(0.0379), float(0.0000), float(0.9993), float(0.0000))
	
	slice00 = stcSvc.spawnObject('slicer', 'corellia', cityHall.getCellByCellNumber(long(3)), float(-18.5787), float(1.3030), float(-10.0671), float(-0.0060), float(0.0000), float(1.0000), float(0.0000))
	
	medic01 = stcSvc.spawnObject('medic', 'corellia', cityHall.getCellByCellNumber(long(3)), float(-0.4508), float(0.3000), float(-4.3491), float(0.9673), float(0.0000), float(0.2536), float(0.0000))

	noble01 = stcSvc.spawnObject('noble', 'corellia', cityHall.getCellByCellNumber(long(3)), float(4.1942), float(0.3000), float(4.0967), float(0.4387), float(0.0000), float(0.8986), float(0.0000))
	
	businessman01 = stcSvc.spawnObject('businessman', 'corellia', cityHall.getCellByCellNumber(long(10)), float(37.3953), float(1.2942), float(-2.3909), float(0.0317), float(0.0000), float(0.9995), float(0.0000))
	
	farmer02 = stcSvc.spawnObject('farmer', 'corellia', cityHall.getCellByCellNumber(long(10)), float(37.5829), float(1.2942), float(-4.6875), float(0.9994), float(0.0000), float(-0.0345), float(0.0000))

	cor_brawlr00 = stcSvc.spawnObject('brawler', 'corellia', cityHall.getCellByCellNumber(3),float(-1.73),float(7.90),float(-32.1),float(0.99),float(0),float(-0.04),float(0))
	
	cor_btdiplmt02 = stcSvc.spawnObject('bothan_diplomat', 'corellia', cityHall.getCellByCellNumber(3), float(5.21),float(0.30),float(2.82),float(0.99),float(0),float(-0.008),float(0))
	
	cor_btinfobkr00 = stcSvc.spawnObject('bothan_information_broker', 'corellia', cityHall.getCellByCellNumber(3),float(5.26),float(0.30),float(3.98),float(0.05),float(0),float(0.99),float(0))

	cor_btinfobkr01 = stcSvc.spawnObject('bothan_information_broker', 'corellia', cityHall.getCellByCellNumber(3),float(5.02),float(2.27),float(-27.43),float(0.99),float(0),float(-0.04),float(0))
	
	cor_ent00 = stcSvc.spawnObject('entertainer', 'corellia',cityHall.getCellByCellNumber(3),float(0.72),float(0.30),float(-2.71),float(-0.33),float(0),float(0.94),float(0))
	
	cor_farmer00 = stcSvc.spawnObject('farmer', 'corellia', cityHall.getCellByCellNumber(3), float(0.79),float(0.30),float(-4.09),float(0.99),float(0),float(0.001),float(0))
	
	cor_farmer01 = stcSvc.spawnObject('farmer', 'corellia', cityHall.getCellByCellNumber(3), float(-18.65),float(1.30),float(-11.34),float(0.99),float(0),float(0.00),float(0))
	
	galluraHanderin = stcSvc.spawnObject('corellia_gallura_handerin', 'corellia', cityHall.getCellByCellNumber(3), float(-6.49),float(1.30),float(9.64),float(0.99),float(0),float(-0.04),float(0))
	# She's involved in a Corellian leg of the Legacy Quest, Working for the government. http://swg.wikia.com/wiki/Gallura_Handerin
	
	corsec_agent00 = stcSvc.spawnObject('corsec_agent', 'corellia',cityHall.getCellByCellNumber(3),float(-0.30),float(0.30),float(-2.77),float(0.02),float(0),float(0.99),float(0))
	
	thaleDustrunner = stcSvc.spawnObject('thale_dustrunner', 'corellia', cityHall.getCellByCellNumber(3),float(-0.04),float(3.07),float(-11.05),float(0.99),float(0),float(-0.051),float(0))
	# He's involved in the Borvo the Hutt quest or Bluffing CorSec per swg wiki ?????   http://swg.wikia.com/wiki/Thale_Dustrunner

#---Coronet-StarPort-interrior
	starPort = core.objectService.getObject(long(1855671))
	
	cor_btdiplmt00 = stcSvc.spawnObject('bothan_diplomat', 'corellia', starPort.getCellByCellNumber(7), float(56.57),float(-0.52),float(34.03),float(0.03),float(0),float(0.99),float(0))
	
	cor_btdiplmt01 = stcSvc.spawnObject('bothan_diplomat', 'corellia', starPort.getCellByCellNumber(1), float(8.66),float(0.63),float(74.34),float(0.99),float(0),float(-0.01),float(0))
	
	
	corsec_cadt00 = stcSvc.spawnObject('corsec_cadet', 'corellia', starPort.getCellByCellNumber(7),float(37.15),float(0.63),float(40.69),float(0.066),float(0),float(0.99),float(0))
	
	farmer02 = stcSvc.spawnObject('farmer', 'corellia', starPort.getCellByCellNumber(12),float(-62.71),float(2.63),float(40.51),float(0.99),float(0),float(-0.06),float(0))
	
	gambler00 = stcSvc.spawnObject('gambler', 'corellia', starPort.getCellByCellNumber(4),float(-4.65),float(0.63),float(67.85),float(0.019),float(0),float(0.99),float(0))
		
	cor_hunter00 = stcSvc.spawnObject('hunter', 'corellia', starPort.getCellByCellNumber(1),float(8.69),float(0.63),float(75.62),float(0.05),float(0),float(0.99),float(0))

	jessbConvorr = stcSvc.spawnObject('jesseb_convorr', 'corellia', starPort.getCellByCellNumber(13),float(50.23),float(0.97),float(20.08),float(0.97),float(0),float(-0.23),float(0))
	#quest Coronet Starport Vandalism (Meatlumps) http://swg.wikia.com/wiki/Coronet_Starport_Vandalism_(Meatlumps)
		
	SScor_merc00 = stcSvc.spawnObject('mercenary', 'corellia', starPort.getCellByCellNumber(12),float(-62.78),float(2.63),float(41.80),float(0.15),float(0),float(0.98),float(0))
		
	cor_miner00 = stcSvc.spawnObject('miner', 'corellia', starPort.getCellByCellNumber(7),float(37.10),float(0.63),float(39.60),float(0.99),float(0),float(-0.051),float(0))
		
	cor_scintst00 = stcSvc.spawnObject('scientist', 'corellia', starPort.getCellByCellNumber(7), float(56.72),float(-0.52),float(32.51),float(0.98),float(0),float(-0.15),float(0))
	

	cor_slice00 = stcSvc.spawnObject('slicer', 'corellia', starPort.getCellByCellNumber(4),float(-4.73),float(0.63),float(66.79),float(0.99),float(0),float(-0.00),float(0))
	
	#-Coronet-Legacy NPCs
	stregand = stcSvc.spawnObject('stregand', 'corellia', long(0), float(-33), float(28), float(-4424), float(0.67), float(0.21))
	# Engineered Blood ??? http://swg.wikia.com/wiki/Engineered_Blood
	
	borgan = stcSvc.spawnObject('borgan', 'corellia', long(0), float(-34.4), float(28), float(-4418.2), float(0.75), float(0.21))
	#------------ He's involved in Legacy quests Corporate Insecurities Part 1 and  Corporate Insecurities Part 2
	
	
	#Lt. Joth - wrong appearance - looking for a bearded guy with BLUE CorSec Jacket
	# He's involved in several Corellia legs of the Legacy Quest 
	#Speak to Lt. Joth
	#Coronet Murmurs: Tracking down rumors
	#Coronet Murmurs: Administrative Secrets
	#Coronet Murmurs: Agents of Destruction
	#Coronet Murmurs: Researching Manipulation
	#Coronet Murmurs: The Head Doctor
	#Coronet Murmurs: Clear Out the Zoo
	#Coronet Murmurs: Shut Them Down
	ltJoth = stcSvc.spawnObject('lt_joth', 'corellia', long(0), float(-283), float(28), float(-4697), float(0.07), float(0.11))
	
	
	mackJasper = stcSvc.spawnObject('lieutenant_jasper', 'corellia', long(0), float(-68.2), float(28), float(-4631.8), float(-0.97), float(0.21))
	# working for CorSec Legacy Quest
	
#-Cantina NPCS
	
	cantina = core.objectService.getObject(long(8105493))
	
	cor_bartener = stcSvc.spawnObject('bartender', 'corellia', cantina.getCellByCellNumber(long(3)), float(8.1353), float(-0.8950), float(1.5667), float(0.9553), float(0.0000), float(0.2956), float(0.0000))
	cor_bartener.setCustomName('Zobipeque Xuquix')
	
	Bg1 = stcSvc.spawnObject('bodyguard', 'corellia', cantina.getCellByCellNumber(long(3)), float(2.5151), float(-0.8950), float(11.4996), float(0.0585), float(0.0000), float(0.9983), float(0.0000))
	Bg2 = stcSvc.spawnObject('bodyguard', 'corellia', cantina.getCellByCellNumber(long(3)), float(4.0869), float(-0.8950), float(-10.1664), float(0.9978), float(0.0000), float(-0.0665), float(0.0000))
	
	Et1 = stcSvc.spawnObject('entertainer', 'corellia', cantina.getCellByCellNumber(long(3)), float(2.5601), float(-0.8950), float(9.6660), float(0.9992), float(0.0000), float(-0.0405), float(0.0000))
	
	busMan1 = stcSvc.spawnObject('businessman', 'corellia', cantina.getCellByCellNumber(long(7)), float(1.1382), float(-0.8950), float(21.3510), float(0.3171), float(0.0000), float(0.9484), float(0.0000))
	busMan1.setCustomName('Jukiyghim Xutib')
	busMan2 = stcSvc.spawnObject('businessman', 'corellia', cantina.getCellByCellNumber(long(3)), float(-8.5771), float(-0.8950), float(-4.8615), float(-0.0944), float(0.0000), float(0.9955), float(0.0000))
		
	Rog_Cor_TP1 = stcSvc.spawnObject('corellia_corsec_rogue', 'corellia', cantina.getCellByCellNumber(long(3)), float(3.8123), float(-0.8950), float(-8.3781), float(0.0430), float(0.0000), float(0.9991), float(0.0000))
	
	rancher1  = stcSvc.spawnObject('farmer_rancher', 'corellia', cantina.getCellByCellNumber(long(3)), float(5.0703), float(-0.8950), float(-8.3478), float(-0.1828), float(0.0000), float(0.9831), float(0.0000))
		
	Cor_Major1 = stcSvc.spawnObject('corsec_major', 'corellia', cantina.getCellByCellNumber(long(3)), float(-8.5268), float(-0.8950), float(-5.7584), float(0.9991), float(0.0000), float(-0.0418), float(0.0000))
	
	criminal1 = stcSvc.spawnObject('criminal', 'corellia', cantina.getCellByCellNumber(long(8)), float(-5.6670), float(-0.8950), float(-20.5518), float(0.9560), float(0.0000), float(-0.2932), float(0.0000))
	
	#-MedCenter
	
	medCenter =  core.objectService.getObject(long(1855529))
	
	#surgical_Droid1 = stcSvc.spawnObject('storyteller_surgical_droid_21b', 'corellia', medCenter.getCellByCellNumber(long(6)), float(-25.4808), float(0.2600), float(-3.4815), float(0.9988), float(0.0000), float(-0.0488), float(0.0000))

	
	


	
	
	
#-Coronet Misc NPCS
	#Hotel
	hotel = core.objectService.getObject(long(1855544))
	
	cor_Times_Inv1 = stcSvc.spawnObject('corellia_times_investigator', 'corellia', hotel.getCellByCellNumber(4),float(7.22),float(1),float(-8.60),float(0.99),float(0),float(-0.04),float(0))
	
	cor_agent1 = stcSvc.spawnObject('corsec_agent', 'corellia', hotel.getCellByCellNumber(4),float(7.19),float(1),float(-6.62),float(0.062),float(0),float(0.99),float(0))
	
	cor_Sergeant1 = stcSvc.spawnObject('corsec_sergeant', 'corellia', hotel.getCellByCellNumber(5),float(25.09),float(1.28),float(7.57),float(0.99),float(0),float(-0.05),float(0))
	
	bodyguard = stcSvc.spawnObject('bodyguard', 'corellia', hotel.getCellByCellNumber(5),float(24.89),float(1.28),float(9.35),float(0.01),float(0),float(0.99),float(0))
	
	
	Kuhyofl = stcSvc.spawnObject('kuhyofl_plizaquaz', 'corellia', hotel.getCellByCellNumber(5),float(18.05),float(1.28),float(-11.17),float(0.06),float(0),float(0.99),float(0))
	
									
	#Profession Counselor
	stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', 'corellia', long(0), float(-129), float(28), float(-4758), float(-0.3327), float(0.9288))
	
	#Junk Dealer
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-118), float(28), float(-4792), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(22), float(28), float(-4773), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-42), float(28), float(-4612), float(0), float(0))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-68), float(28), float(-4585), float(0), float(1))
	
	#--- Mouse Droid Patrol Cnet
	patrolpoints = Vector()
	
	patrolpoints.add(Point3D(float(-175.62), float(28.00), float(-4730.13)))
	patrolpoints.add(Point3D(float(-147.57), float(28.00), float(-4724.04)))
	patrolpoints.add(Point3D(float(-146.53), float(28.00), float(-4687.68)))
	patrolpoints.add(Point3D(float(-193.39), float(28.00), float(-4677.59)))
	patrolpoints.add(Point3D(float(-200.36), float(28.00), float(-4712.80)))
	
	
	
	aiSvc.setPatrol(mouseDroid1, patrolpoints)
	
	return
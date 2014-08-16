
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	#--- Outside
	noble1 = stcSvc.spawnObject('noble', 'corellia', long(0), float(6723.4336), float(330.0000), float(-5909.1641), float(0.0630), float(0.0000), float(0.9980), float(0.0000))
	
	r4_08_unit1 = stcSvc.spawnObject('r4_08', 'corellia', long(0), float(6715.3071), float(330.0000), float(-5903.5723), float(0.9274), float(0.0000), float(-0.3740), float(0.0000))
	
	
	
	
	cor_trooper1 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0), float(6846.6514), float(315.0000), float(-5838.7563), float(0.9998), float(0.0000), float(0.0198), float(0.0000))
	cor_trooper2 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0), float(6851.5649), float(315.0000), float(-5837.4224), float(0.9953), float(0.0000), float(-0.0972), float(0.0000))
	cor_trooper3 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0), float(6855.2886), float(315.0000), float(-5836.2983), float(0.9892), float(0.0000), float(-0.1469), float(-0.0000))
	cor_trooper4 = stcSvc.spawnObject('corsec_trooper', 'corellia', long(0), float(6861.2837), float(315.0000), float(-5832.1499), float(0.8722), float(0.0000), float(-0.4892), float(0.0000))
	
	cll8binarylifter = stcSvc.spawnObject('cll8binarylifter', 'corellia',long(0), float(6826.0396), float(315.0000), float(-5778.1929), float(0.0372), float(0.0000), float(0.9993), float(0.0000))
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6807.5962), float(315.0000), float(-5778.4189), float(0.7450), float(0.0000), float(0.6671), float(0.0000))
	commoner02 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6828.7139), float(315.0000), float(-5744.6572), float(0.0337), float(0.0000), float(0.9994), float(0.0000))
	commoner03 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6798.5410), float(315.0000), float(-5731.2930), float(0.8232), float(0.0000), float(-0.5677), float(0.0000))
	commoner04 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6759.1470), float(315.0000), float(-5733.0542), float(1.0000), float(0.0000), float(-0.0097), float(-0.0000))
	commoner05 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6760.8481), float(315.0000), float(-5695.0220), float(-0.6955), float(0.0000), float(0.7185), float(0.0000))
	commoner06 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6739.9331), float(315.0000), float(-5673.3970), float(0.7401), float(0.0000), float(0.6725), float(0.0000))
	commoner07 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6754.8086), float(314.9264), float(-5625.9463), float(0.8304), float(0.0000), float(0.5571), float(0.0000))
	commoner08 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6820.6880), float(314.9993), float(-5610.7295), float(-0.4453), float(0.0000), float(0.8954), float(0.0000))
	commoner09 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6845.9448), float(315.0000), float(-5588.3232), float(0.8312), float(0.0000), float(-0.5560), float(0.0000))
	commoner10 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6833.7129), float(315.0000), float(-5577.2773), float(0.9970), float(0.0000), float(0.0779), float(0.0000))
	commoner11 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6896.7720), float(330.0000), float(-5576.5103), float(0.9343), float(0.0000), float(0.3564), float(0.0000))
	commoner12 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(6898.8350), float(330.0000), float(-5601.7188), float(0.9944), float(0.0000), float(-0.1060), float(0.0000))
	
	#-Cantina NPCS
	
	cantina = core.objectService.getObject(long(3375352))
	
	Bela_bartener = stcSvc.spawnObject('bartender', 'corellia', cantina.getCellByCellNumber(long(3)), float(8.5142), float(-0.8950), float(0.0679), float(0.7670), float(0.0000), float(0.6417), float(0.0000))
	
	busMan01 = stcSvc.spawnObject('businessman', 'corellia', cantina.getCellByCellNumber(long(3)), float(-9.4123), float(-0.8958), float(6.7791), float(0.9999), float(0.0000), float(0.0132), float(0.0000))
	busMan02 = stcSvc.spawnObject('businessman', 'corellia', cantina.getCellByCellNumber(long(9)), float(-6.0319), float(-0.8950), float(21.6557), float(0.5267), float(0.0000), float(0.8501), float(0.0000))
	
	bounty_hunter01 = stcSvc.spawnObject('bounty_hunter', 'corellia', cantina.getCellByCellNumber(long(4)), float(22.5401), float(-0.8950), float(-19.0297), float(0.9712), float(0.0000), float(-0.2382), float(0.0000))
	
	info_broker01 = stcSvc.spawnObject('info_broker', 'corellia', cantina.getCellByCellNumber(long(4)), float(21.7652), float(-0.8950), float(-17.6669), float(0.3496), float(0.0000), float(0.9369), float(0.0000))
	
	noble01 = stcSvc.spawnObject('noble', 'corellia', cantina.getCellByCellNumber(long(3)), float(6.8384), float(-0.8950), float(-5.4765), float(0.0658), float(0.0000), float(0.9978), float(0.0000))
	
	medic011 = stcSvc.spawnObject('medic', 'corellia', cantina.getCellByCellNumber(long(15)), float(-42.8729), float(0.1050), float(-22.7996), float(0.9334), float(0.0000), float(-0.3588), float(0.0000))
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',cantina.getCellByCellNumber(long(15)), float(-43.1796), float(0.1050), float(-21.4446), float(0.3888), float(0.0000), float(0.9213), float(0.0000))

	comm_operator01 = stcSvc.spawnObject('comm_operator', 'corellia',cantina.getCellByCellNumber(long(15)), float(-41.5536), float(0.1050), float(-22.3357), float(0.8597), float(0.0000), float(-0.5108), float(0.0000))
	
	both_info_bk01 = stcSvc.spawnObject('bothan_information_broker', 'corellia',cantina.getCellByCellNumber(long(9)), float(-4.3663), float(-0.8950), float(22.3027), float(-0.0269), float(0.0000), float(0.9996), float(0.0000))
	
	miner01 = stcSvc.spawnObject('miner', 'corellia',cantina.getCellByCellNumber(long(9)), float(-4.4127), float(-0.8950), float(20.7158), float(0.9999), float(0.0000), float(0.0122), float(0.0000))
	
	
	

	#junkdealer 
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6840), float(315), float(-5630), float(0.707), float(-0.707))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6852), float(315), float(-5802), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6756), float(315), float(-5778), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6970), float(330), float(-5588), float(0.71), float(0.71))
	return	

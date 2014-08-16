
import sys
from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	commoner01 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5571.2476), float(23.4000), float(-6128.8330), float(0.9654), float(0.0000), float(0.2609), float(0.0000))
	commoner02 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5693.7012), float(14.6000), float(-6176.9048), float(0.6511), float(0.0000), float(0.7590), float(0.0000))
	commoner03 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5708.2847), float(14.6000), float(-6155.8193), float(0.5607), float(0.0000), float(0.8280), float(0.0000))
	commoner04 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5685.8755), float(14.6000), float(-6148.0039), float(-0.5229), float(0.0000), float(0.8524), float(0.0000))
	commoner05 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5504.6504), float(23.4000), float(-6118.2178), float(-0.6740), float(0.0000), float(0.7387), float(0.0000))
	commoner06 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5549.9136), float(23.4000), float(-6188.6597), float(0.9997), float(0.0000), float(-0.0261), float(0.0000))
	commoner07 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5551.2480), float(23.4000), float(-6222.4028), float(0.7347), float(0.0000), float(0.6784), float(0.0000))
	commoner08 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5519.1284), float(23.4000), float(-6224.6265), float(0.8971), float(0.0000), float(-0.4418), float(-0.0000))
	commoner09 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5488.5420), float(23.8985), float(-6241.9492), float(0.7093), float(0.0000), float(0.7049), float(0.0000))
	commoner10 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5504.5947), float(23.4000), float(-6211.2524), float(-0.0628), float(0.0000), float(0.9980), float(0.0000))
	commoner11 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5495.8662), float(23.4000), float(-6189.6348), float(0.2365), float(0.0000), float(0.9716), float(0.0000))
	commoner12 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5467.7988), float(23.4000), float(-6144.2690), float(0.9842), float(0.0000), float(0.1771), float(0.0000))
	commoner13 = stcSvc.spawnObject('commoner', 'corellia',long(0), float(-5384.9771), float(24.0000), float(-6238.9502), float(0.3242), float(0.0000), float(0.9460), float(0.0000))
	
	scientist1 = stcSvc.spawnObject('scientist', 'corellia',long(0), float(-5557.0527), float(23.4000), float(-6203.0054), float(0.7772), float(0.0000), float(0.6292), float(0.0000))
	
	EG_6_p_droid1 = stcSvc.spawnObject('eg6_power_droid', 'corellia',long(0), float(-5690.0708), float(14.6000), float(-6154.8433), float(0.9932), float(0.0000), float(-0.1165), float(0.0000))
	EG_6_p_droid2 = stcSvc.spawnObject('eg6_power_droid', 'corellia',long(0), float(-5691.0776), float(14.6000), float(-6151.0254), float(-0.6601), float(0.0000), float(0.7512), float(0.0000))
	
	R2_unit = stcSvc.spawnObject('r2', 'corellia',long(0), float(-5527.5488), float(23.4000), float(-6195.2075), float(0.7647), float(0.0000), float(0.6443), float(0.0000))

	
	cll8binarylifter1 = stcSvc.spawnObject('eg6_power_droid', 'corellia',long(0), float(-5696.4463), float(14.6000), float(-6153.2695), float(0.3365), float(0.0000), float(0.9417), float(0.0000))
	
	#rebel_recruiter = stcSvc.spawnObject('rebel_recruiter', 'corellia',long(0), float(-5544.2920), float(16.0139), float(-6070.4609), float(0.7586), float(0.0000), float(-0.6516), float(0.0000))

	#rebel_soldier1 = stcSvc.spawnObject('crackdown_rebel_soldier_25', 'corellia', long(0), float(-5664.8369), float(14.6000), float(-6178.5034), float(0.5333), float(0.0000), float(0.8459), float(0.0000))
	#rebel_soldier2 = stcSvc.spawnObject('crackdown_rebel_soldier_25', 'corellia', long(0), float(-5715.1826), float(14.6000), float(-6153.5142), float(-0.6689), float(0.0000), float(0.7433), float(0.0000))
	#rebel_soldier3 = stcSvc.spawnObject('crackdown_rebel_soldier_25', 'corellia', long(0), float(-5397.4897), float(24.1948), float(-6243.6470), float(0.7284), float(0.0000), float(0.6852), float(0.0000))
	
	#Rebel_security_guard1 = stcSvc.spawnObject('rebel_security_guard', 'corellia', long(0), float(-5405.4038), float(25.2491), float(-6219.5327), float(0.9927), float(0.0000), float(0.1202), float(0.0000))
	

	#rebel_liberator1 = stcSvc.spawnObject('rebel_liberator', 'corellia', long(0), float(-5715.7700), float(14.6000), float(-6147.1191), float(-0.6609), float(0.0000), float(0.7505), float(0.0000))
	#rebel_liberator2 = stcSvc.spawnObject('rebel_liberator', 'corellia', long(0), float(-5548.1470), float(23.4000), float(-6202.3511), float(0.9100), float(0.0000), float(-0.4147), float(0.0000))
	
	#rebel_comm_op1 = stcSvc.spawnObject('rebel_comm_op_14', 'corellia', long(0), float(-5549.4761), float(23.4000), float(-6216.0449), float(-0.3250), float(0.0000), float(0.9457), float(0.0000))


	#inexper_rebel_cadet1 = stcSvc.spawnObject('inexperienced_rebel_cadet_29', 'corellia', long(0), float(-5533.5054), float(23.4000), float(-6202.7207), float(0.9116), float(0.0000), float(0.4112), float(0.0000))

	
	
	
	
	#rebel_guard1 = stcSvc.spawnObject('rebel_guard_25', 'corellia', long(0), float(-5538.0576), float(16.4902), float(-6054.8652), float(0.0324), float(0.0000), float(0.9995), float(0.0000))
	#rebel_guard2 = stcSvc.spawnObject('rebel_guard_25', 'corellia', long(0), float(-5653.4590), float(16.7374), float(-6185.5439), float(0.9106), float(0.0000), float(0.4133), float(0.0000))
	#rebel_guard3 = stcSvc.spawnObject('rebel_guard_25', 'corellia', long(0), float(-5534.9146), float(23.4000), float(-6216.7935), float(0.4746), float(0.0000), float(0.8802), float(0.0000))
	#rebel_guard4 = stcSvc.spawnObject('rebel_guard_25', 'corellia', long(0), float(-5443.3027), float(24.0000), float(-6242.8916), float(-0.5526), float(0.0000), float(0.8335), float(0.0000))
	


	
	
	
	#-Hotel interrior
	hotel = core.objectService.getObject(long(2775409))
	
	cor_Invest1 = stcSvc.spawnObject('corsec_investigator', 'corellia',hotel.getCellByCellNumber(long(4)), float(7.3184), float(1.0000), float(10.1357), float(0.0271), float(0.0000), float(0.9996), float(0.0000))
	
	corsec_detective1 = stcSvc.spawnObject('corsec_detective', 'corellia',hotel.getCellByCellNumber(long(4)), float(7.7559), float(1.0000), float(8.3799), float(0.9959), float(0.0000), float(-0.0910), float(0.0000))
	
	farmer1 = stcSvc.spawnObject('farmer', 'corellia',hotel.getCellByCellNumber(long(6)), float(-22.0171), float(1.6000), float(4.4185), float(0.0928), float(0.0000), float(0.9957), float(0.0000))

	gambler1 = stcSvc.spawnObject('gambler', 'corellia',hotel.getCellByCellNumber(long(6)), float(-22.0376), float(1.5963), float(2.2065), float(0.9980), float(0.0000), float(-0.0632), float(0.0000))
	
	karrek_flim = stcSvc.spawnObject('karrek_flim', 'corellia',hotel.getCellByCellNumber(long(8)), float(-7.4946), float(1.6000), float(-7.6260), float(0.0406), float(0.0000), float(0.9992), float(0.0000))
	
	corsec_inspector_sergeant1 = stcSvc.spawnObject('corsec_inspector_sergeant', 'corellia',hotel.getCellByCellNumber(long(5)), float(23.6011), float(1.2831), float(4.4629), float(0.9963), float(0.0000), float(-0.0854), float(0.0000))
	
	corsec_master_sergeant1 = stcSvc.spawnObject('corsec_master_sergeant', 'corellia',hotel.getCellByCellNumber(long(5)), float(23.4888), float(1.2831), float(6.6792), float(0.0526), float(0.0000), float(0.9986), float(0.0000))
	

	
	

	
	#vreni
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-5542), float(23.4), float(-6228.7), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-5440.3169), float(23.9197), float(-6228.0771), float(-0.6117), float(0.0000), float(0.7911), float(0.0000))

	return	

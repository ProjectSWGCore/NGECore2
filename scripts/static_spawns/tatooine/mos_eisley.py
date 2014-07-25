# Project SWG:   Mos Eisley:  Static Spawns
# (C)2014 ProjectSWG

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
	
	# TODO:  Add NPCs sitting at Cantina tables with SittingOnChair bitmask.
	#Eisley Ship Controller
	stcSvc.spawnObject('object/mobile/shared_distant_ship_controller.iff', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91))
	
	#Cantina Interior
	building = core.objectService.getObject(long(1082874)) 
	wuher = stcSvc.spawnObject('wuher', 'tatooine', building.getCellByCellNumber(3), float(8.6), float(-0.9), float(0.4), float(0), float(0.71), float(0), float(0.71)) 
	
	chadraFanFemale = stcSvc.spawnObject('chadrafanfemale', 'tatooine', building.getCellByCellNumber(3), float(10.6), float(-0.9), float(-1.5), float(0), float(0.42), float(0), float(0.90))
	
	chadraFanMale = stcSvc.spawnObject('chadrafanmale', 'tatooine', building.getCellByCellNumber(3), float(10.7), float(-0.9), float(-0.3), float(0), float(0.64), float(0), float(0.77))
	
	muftak = stcSvc.spawnObject('muftak', 'tatooine', building.getCellByCellNumber(3), float(20.3), float(-0.9), float(4.9), float(0), float(0.82), float(0), float(0.57)) 
	
	chissMale1 = stcSvc.spawnObject('chissmale', 'tatooine', building.getCellByCellNumber(3), float(1.7), float(-0.9), float(-5.0), float(0), float(0.71), float(0), float(0.71)) 
	
	chissMale2 = stcSvc.spawnObject('chissmale', 'tatooine', building.getCellByCellNumber(3), float(3.4), float(-0.9), float(-4.8), float(0), float(0), float(0), float(0)) 
	
	cantinaStorm = stcSvc.spawnObject('staticstorm', 'tatooine', building.getCellByCellNumber(3), float(2.9), float(-0.9), float(-6.5), float(0), float(0.71), float(0), float(0.71)) 
	
	cantinaStormL = stcSvc.spawnObject('staticstorml', 'tatooine', building.getCellByCellNumber(3), float(3.6), float(-0.9), float(-7.9), float(0), float(0.71), float(0), float(0.71)) 
			
	businessman1 = stcSvc.spawnObject('businessman', 'tatooine', building.getCellByCellNumber(3), float(11.0), float(-0.9), float(2.1), float(0), float(0.38), float(0), float(-0.92)) 
	
	commoner1 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(10.3), float(-0.9), float(2.7), float(0), float(0.82), float(0), float(0.57)) 
	
	entertainer1 = stcSvc.spawnObject('entertainer', 'tatooine', building.getCellByCellNumber(3), float(9.4), float(-0.9), float(3.9), float(0), float(0.38), float(0), float(-0.92)) 
	
	noble1 = stcSvc.spawnObject('noble', 'tatooine', building.getCellByCellNumber(3), float(8.6), float(-0.9), float(4.8), float(0), float(0.82), float(0), float(0.57)) 

	commoner2 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(4.1), float(-0.9), float(5.7), float(0), float(1), float(0), float(0)) 
	
	commoner3 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(3.1), float(-0.9), float(5.9), float(0), float(1), float(0), float(0)) 
	
	commoner4 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(1.7), float(-0.9), float(6.0), float(0), float(1), float(0), float(0)) 
	
	commoner5 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(-0.4), float(-0.9), float(5.9), float(0), float(1), float(0), float(0)) 
	
	commoner6 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(16.0), float(-0.9), float(4.1), float(0), float(0), float(0), float(0)) 
	
	commoner7 = stcSvc.spawnObject('patron', 'tatooine', building.getCellByCellNumber(3), float(8.8), float(-0.9), float(-6.0), float(0), float(0.98), float(0), float(-0.22)) 
	
	commoner8 = stcSvc.spawnObject('patron', 'tatooine', building.getCellByCellNumber(3), float(6.8), float(-0.9), float(-6.5), float(0), float(0.98), float(0), float(-0.22)) 
	
	commoner9 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(1.1), float(-0.9), float(-7.7), float(0), float(0.42), float(0), float(0.91)) 
	
	commoner10 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(2.1), float(-0.9), float(-8.4), float(0), float(0.42), float(0), float(0.91)) 

	anetiaKahryn = stcSvc.spawnObject('anetiakahyn', 'tatooine', building.getCellByCellNumber(4), float(19.8), float(-0.9), float(-21), float(0), float(-0.03), float(0), float(0.99)) 
	
	figrinDan = stcSvc.spawnObject('firgindan', 'tatooine', building.getCellByCellNumber(6), float(3.7), float(-0.9), float(-14.4), float(0), float(0.42), float(0), float(0.91)) 
	
	techMor = stcSvc.spawnObject('techmor', 'tatooine', building.getCellByCellNumber(6), float(4.0), float(-0.9), float(-17.0), float(0), float(0.42), float(0), float(0.91)) 
	
	doikkNats = stcSvc.spawnObject('doikknats', 'tatooine', building.getCellByCellNumber(6), float(2.2), float(-0.9), float(-16.4), float(0), float(0.42), float(0), float(0.91)) 
	
	tednDahai = stcSvc.spawnObject('tedndahai', 'tatooine', building.getCellByCellNumber(6), float(1.3), float(-0.9), float(-15.2), float(0), float(0.42), float(0), float(0.91)) 
	
	nalanCheel = stcSvc.spawnObject('nalancheel', 'tatooine', building.getCellByCellNumber(6), float(0.5), float(-0.9), float(-17.1), float(0), float(0.42), float(0), float(0.91)) 
	
	dravis = stcSvc.spawnObject('dravis', 'tatooine', building.getCellByCellNumber(12), float(-21.7), float(-0.9), float(25.5), float(0), float(0.99), float(0), float(0.03)) 

	talonKarrde = stcSvc.spawnObject('talonkarrde', 'tatooine', building.getCellByCellNumber(13), float(-25.7), float(-0.5), float(8.8), float(0), float(0.21), float(0), float(0.97)) 
	
	#Miscellaneous Building Interiors

	#Outside
	businessman2 = stcSvc.spawnObject('businessman', 'tatooine', long(0), float(3663.3), float(4.0), float(-4738.6), float(0), float(0)) 
	
	noble2 = stcSvc.spawnObject('noble', 'tatooine', long(0), float(3542.3), float(5.0), float(-4826.0), float(0.42), float(0.91)) 
	
	commoner11 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3529.1), float(5.0), float(-4900.4), float(0.42), float(0.91)) 

	businessman3 = stcSvc.spawnObject('businessman', 'tatooine', long(0), float(3595.7), float(5.0), float(-4740.1), float(0), float(0)) 
	
	jawa1 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3663.3), float(5.0), float(-4858.6), float(0), float(0)) 
	
	commoner12 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3490.3), float(5.0), float(-4799.4), float(0), float(0)) 
	commoner12.setCustomName('a Scientist')
	
	commoner13 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3559.7), float(5.0), float(-4725.9), float(0), float(0)) 
	
	commoner14 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3527.7), float(5.0), float(-4721.1), float(0.71), float(0.71)) 
	
	commoner15 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3514.9), float(5.0), float(-4737.8), float(0), float(0)) 
	
	jawa2 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3485.2), float(4.9), float(-4859.2), float(0), float(0)) 

	jawa3 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3465.3), float(5.0), float(-4860.1), float(0.71), float(-0.71)) 
	
	jawa4 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3486.8), float(5.0), float(-4884.7), float(0.43051), float(-0.9025)) 

	jawa5 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3487.1), float(5.0), float(-4886.0), float(0.95105), float(0.3090)) 
	
	jawa6 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3488.8), float(5.0), float(-4884.4), float(0.3255), float(-0.9455)) 
	
	jawa7 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3472.2), float(5.0), float(-4918.4), float(0), float(0)) 
	
	jawa8 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3471.4), float(5.0), float(-4919.5), float(0), float(0)) 
	
	jawa9 = stcSvc.spawnObject('staticjawa', 'tatooine', long(0), float(3470.3), float(5.0), float(-4918.7), float(0), float(0)) 
	
	bib = stcSvc.spawnObject('bibfortunaeisley', 'tatooine', long(0), float(3552.4), float(5.0), float(-4933.2), float(0.31730), float(-0.9483))
	
	commoner16 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3398.2), float(4.0), float(-4654.2), float(0.42), float(0.91)) 

	noble3 = stcSvc.spawnObject('noble', 'tatooine', long(0), float(3396.3), float(4.0), float(-4774.1), float(0.42), float(0.91)) 
	
	entertainer1 = stcSvc.spawnObject('entertainer', 'tatooine', long(0), float(3305.7), float(5.6), float(-4771.7), float(0), float(0)) 

	r3m6 = stcSvc.spawnObject('r3m6', 'tatooine', long(0), float(3460.1), float(4.0), float(-4898.2), float(0.38), float(-0.92)) 
	
	eg1 = stcSvc.spawnObject('eg6powerdroid', 'tatooine', long(0), float(3463.8), float(4.0), float(-4882.6), float(-0.38), float(0.92)) 

	commoner17 = stcSvc.spawnCommoner('commoner', 'tatooine', long(0), float(3452.6), float(4.0), float(-4937.1), float(0), float(0)) 

	lifter1 = stcSvc.spawnObject('cll8binarylifter', 'tatooine', long(0), float(3547), float(5.0), float(-4768.9), float(0), float(0)) 

	r3j7 = stcSvc.spawnObject('r3j7', 'tatooine', long(0), float(3311.1), float(4.0), float(-4820.2), float(0.38), float(-0.92)) 

	noble4 = stcSvc.spawnObject('noble', 'tatooine', long(0), float(3255.3), float(4.0), float(-4848.1), float(0.42), float(0.91)) 
	
	byxlePedette = stcSvc.spawnObject('byxlepedette', 'tatooine', long(0), float(3365), float(5), float(-4639), float(0.99), float(0.12)) 
	
	errikDarksider = stcSvc.spawnObject('errikdarksider', 'tatooine', long(0), float(3381), float(4.6), float(-4498), float(0.91), float(0.40)) 
	
	gendra = stcSvc.spawnObject('gendra', 'tatooine', long(0), float(3308), float(5.6), float(-4785), float(0.84), float(0.53)) 
		
	lurval = stcSvc.spawnObject('lurval', 'tatooine', long(0), float(3387), float(5), float(-4791), float(-0.4), float(0.91)) 
	
	matildaCarson = stcSvc.spawnObject('matildacarson', 'tatooine', long(0), float(3490.2), float(5), float(-4778), float(0.87), float(0.48)) 
	
	vanvi = stcSvc.spawnObject('vanvihotn', 'tatooine', long(0), float(3312), float(5), float(-4655), float(0.95), float(-0.28)) 
	
	harburik = stcSvc.spawnObject('haburik', 'tatooine', long(0), float(3486), float(5), float(-4740), float(0), float(-0.71), float(0), float(0.71)) 
	
	trehla = stcSvc.spawnObject('trehla', 'tatooine', long(0), float(3484), float(5), float(-4808), float(0), float(0.92), float(0), float(-0.38)) 
	
	#Eisley Legacy Quest NPCs
	
	vourk = stcSvc.spawnObject('vourk', 'tatooine', long(0), float(3520.0), float(5.0), float(-4821.0), float(0.42), float(0.91))
	
	building = core.objectService.getObject(long(1279956)) 
	mayor = stcSvc.spawnObject('mos_eisley_mayor', 'tatooine', building.getCellByCellNumber(4), float(1.2), float(2.5), float(5.4), float(0), float(0), float(0), float(0))
	
	enthaKandela = stcSvc.spawnObject('enthakandela', 'tatooine', long(0), float(3511), float(5.0), float(-4785), float(0.70), float(0.71))

	purvis = stcSvc.spawnObject('purvisarrison', 'tatooine', long(0), float(3512.4), float(5.0), float(-4764.9), float(0.38), float(-0.92))
	
	building = core.objectService.getObject(long(1189630)) 
	peawpRdawc = stcSvc.spawnObject('peawprdawc', 'tatooine', building.getCellByCellNumber(9), float(-11.8), float(1.1), float(-10.1), float(0), float(0), float(0), float(0))
	
	nikoBrehe = stcSvc.spawnObject('nikobrehe', 'tatooine', long(0), float(3506.7), float(5.0), float(-4795.8), float(0.70), float(0.71))
	
	dunir = stcSvc.spawnObject('dunir', 'tatooine', long(0), float(3520.7), float(5.0), float(-4683.7), float(0.99), float(-0.08))

	#Profession Counselor
	#stcSvc.spawnObject('professioncounselor', 'tatooine', long(0), float(3533.14), float(5), float(-4788.86), float(-0.3327), float(0.9288))
	
	#Junk Dealers
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3355), float(5), float(-4823), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3412), float(5), float(-4713), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3500.96), float(5.5), float(-4961.4), float(-0.050), float(0.998))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3496.29), float(5.5), float(-4926.52), float(0.995), float(0.090))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3377), float(5), float(-4524), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3271), float(5), float(-4704), float(0), float(0))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3476.9), float(5), float(-460.35), float(0.189), float(0.981))

	patrolpoints = Vector()
	patrolpoints.add(Point3D(float(3547), float(5), float(-4768.9)))
	patrolpoints.add(Point3D(float(3556.2), float(5), float(-4757.1)))
	patrolpoints.add(Point3D(float(3558.4), float(4.8), float(-4739.2)))
	patrolpoints.add(Point3D(float(3552.7), float(4), float(-4728)))
	patrolpoints.add(Point3D(float(3562.9), float(4.1), float(-4720.9)))
	patrolpoints.add(Point3D(float(3584.5), float(5), float(-4726.1)))
	patrolpoints.add(Point3D(float(3609.3), float(5), float(-4730.6)))
	patrolpoints.add(Point3D(float(3584.5), float(5), float(-4726.1)))
	patrolpoints.add(Point3D(float(3562.9), float(4.1), float(-4720.9)))
	patrolpoints.add(Point3D(float(3552.7), float(4), float(-4728)))
	patrolpoints.add(Point3D(float(3558.4), float(4.8), float(-4739.2)))
	patrolpoints.add(Point3D(float(3556.2), float(5), float(-4757.1)))
	patrolpoints.add(Point3D(float(3547), float(5), float(-4768.9)))
	
	patrolpoints1 = Vector()
	patrolpoints1.add(Point3D(float(3465.3), float(5), float(-4860.1)))
	patrolpoints1.add(Point3D(float(3473.4), float(4), float(-4860.7)))
	patrolpoints1.add(Point3D(float(3482.1), float(5), float(-4861.3)))
	patrolpoints1.add(Point3D(float(3473.4), float(4), float(-4860.7)))
	patrolpoints1.add(Point3D(float(3465.3), float(5), float(-4860.1)))
	
	aiSvc.setPatrol(lifter1, patrolpoints)
	aiSvc.setPatrol(jawa3, patrolpoints1)
	
	aiSvc.setLoiter(r3m6, float(1), float(3))
	aiSvc.setLoiter(eg1, float(1), float(3))
	aiSvc.setLoiter(r3j7, float(1), float(3))
	return
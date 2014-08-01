# Project SWG:   Mos Eisley Cantina :  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State
from resources.datatables import StateStatus
from protocol.swg.objectControllerObjects import SitOnObject
from protocol.swg import ObjControllerMessage

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO:  Add NPCs sitting at Cantina tables with SittingOnChair bitmask.
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
	
	allura = stcSvc.spawnObject('allura', 'tatooine', building.getCellByCellNumber(2), float(31.35), float(0.105), float(0.871), float(0.706), float(0), float(0.708), float(0))
	
	
	figrinDan = stcSvc.spawnObject('firgindan', 'tatooine', building.getCellByCellNumber(6), float(3.7), float(-0.9), float(-14.4), float(0), float(0.42), float(0), float(0.91)) 
	
	techMor = stcSvc.spawnObject('techmor', 'tatooine', building.getCellByCellNumber(6), float(4.0), float(-0.9), float(-17.0), float(0), float(0.42), float(0), float(0.91)) 
	
	doikkNats = stcSvc.spawnObject('doikknats', 'tatooine', building.getCellByCellNumber(6), float(2.2), float(-0.9), float(-16.4), float(0), float(0.42), float(0), float(0.91)) 
	
	tednDahai = stcSvc.spawnObject('tedndahai', 'tatooine', building.getCellByCellNumber(6), float(1.3), float(-0.9), float(-15.2), float(0), float(0.42), float(0), float(0.91)) 
	
	nalanCheel = stcSvc.spawnObject('nalancheel', 'tatooine', building.getCellByCellNumber(6), float(0.5), float(-0.9), float(-17.1), float(0), float(0.42), float(0), float(0.91)) 
	
	dravis = stcSvc.spawnObject('dravis', 'tatooine', building.getCellByCellNumber(12), float(-21.7), float(-0.9), float(25.5), float(0), float(0.99), float(0), float(0.03)) 

	talonKarrde = stcSvc.spawnObject('talonkarrde', 'tatooine', building.getCellByCellNumber(13), float(-25.7), float(-0.5), float(8.8), float(0), float(0.21), float(0), float(0.97)) 
	
	commoner11 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(2), float(30.96), float(0.105), float(-8.33), float(0.999), float(0), float(0.042), float(0)) 

	patron1 = stcSvc.spawnObject('patron', 'tatooine', building.getCellByCellNumber(2), float(29.95), float(0.105), float(-7.26), float(0.861), float(0), float(0.509), float(0)) 
	
	commoner12 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(2), float(29.89), float(0.105), float(-6.30), float(0.574), float(0), float(0.819), float(0)) 

	commoner13 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(2), float(35.78), float(0.105), float(0.955), float(0.865), float(0), float(-0.501), float(0))
	
	commoner14 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(2), float(35.45), float(0.105), float(2.77), float(-0.148), float(0), float(0.989), float(0)) 
	
	nikto = stcSvc.spawnObject('nikto', 'tatooine', building.getCellByCellNumber(3), float(22.94), float(-0.895), float(4.72), float(-0.566), float(0), float(0.824), float(0)) 
	
	commoner15 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(22.12), float(-0.895), float(3.62), float(0.981), float(0), float(-0.192), float(0))
	commoner16 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(16.63), float(-0.895), float(6.43), float(-0.297), float(0), float(0.955), float(0)) 
	commoner17 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(15.45), float(-0.895), float(2.91), float(-0.053), float(0), float(0.999), float(0)) 
	commoner18 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(24.79), float(-0.895), float(-7.86), float(0.934), float(0), float(0.358), float(0)) 
	commoner19 = stcSvc.spawnObject('commoner', 'tatooine', building.getCellByCellNumber(3), float(25.93), float(-0.895), float(-7.82), float(0.883), float(0), float(-0.469), float(0)) 
	return
	
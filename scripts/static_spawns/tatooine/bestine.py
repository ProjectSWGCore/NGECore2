# Project SWG:   Bestine:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	# City hall
	cityhall = core.objectService.getObject(long(926472))
	
	indigoSiyan = stcSvc.spawnObject('indigosiyan', 'tatooine', cityhall.getCellByCellNumber(11), float(24.7), float(3.2), float(-30.7), float(0.99), float(0), float(-0.06), float(0))

	keanna = stcSvc.spawnObject('keanna', 'tatooine', cityhall.getCellByCellNumber(8), float(-18.7), float(3.2), float(20.6), float(0.2), float(0), float(-0.97), float(0))

	oberhaur = stcSvc.spawnObject('oberhaur', 'tatooine',  cityhall.getCellByCellNumber(8), float(-21.9), float(3.2), float(26.9), float(0.01), float(0), float(0.99), float(0))

	seanTrenwell = stcSvc.spawnObject('seantrenwell', 'tatooine', cityhall.getCellByCellNumber(11), float(19.4), float(3.2), float(-36), float(0.99), float(0), float(-0.06), float(0))

	talmont = stcSvc.spawnObject('prefecttalmont', 'tatooine',  cityhall.getCellByCellNumber(3), float(-1.9), float(3.1), float(-10.3), float(0), float(0), float(0.99), float(0))
	
	tourAryon = stcSvc.spawnObject('touraryon', 'tatooine',  cityhall.getCellByCellNumber(7), float(-36.8), float(1.3), float(0.3), float(0.59), float(0), float(0.8), float(0))

	victorVisalis = stcSvc.spawnObject('victorvisalis', 'tatooine',  cityhall.getCellByCellNumber(8), float(-26.7), float(3.2), float(20.8), float(0.24), float(0), float(0.96), float(0))

	wilhalmSkrim = stcSvc.spawnObject('wilhalmskrim', 'tatooine', cityhall.getCellByCellNumber(10), float(28.9), float(1.3), float(-6.0), float(0.97), float(0), float(0.23), float(0))
	
	pilotguildhall = core.objectService.getObject(long(1028590))
	#Miscellaneous Building Interiors
	akalColzet = stcSvc.spawnObject('akalcolzet', 'tatooine', pilotguildhall.getCellByCellNumber(10), float(0.7), float(1.8), float(-14), float(0.99), float(0), float(0), float(0))
	
	fariousgletchhouse = core.objectService.getObject(long(1278979))
	fariousGletch = stcSvc.spawnObject('fariousgletch', 'tatooine', fariousgletchhouse.getCellByCellNumber(10), float(2.0), float(-0.4), float(-5.7), float(0.98), float(0), float(-0.15), float(0))
	
	hillbase = core.objectService.getObject(long(1279918))
	pfilbeeJhorn = stcSvc.spawnObject('pfilbee', 'tatooine', hillbase.getCellByCellNumber(5), float(5.1), float(0.1), float(-3.8), float(0.71), float(0), float(-0.7), float(0))
	
	#Hotel interior
	hotel = core.objectService.getObject(long(1223845))
	gilBurtin = stcSvc.spawnObject('gilburtin', 'tatooine', hotel.getCellByCellNumber(5), float(20.3), float(1.6), float(12.8), float(0), float(0), float(0.99), float(0))
	
	# Outside
	barak = stcSvc.spawnObject('brokerbarak', 'tatooine', long(0), float(-1049), float(5.0), float(-3537), float(0.97), float(0.23))
	
	barrezz = stcSvc.spawnObject('barezz', 'tatooine', long(0), float(-1146.8), float(98.0), float(-3892.1), float(0.75), float(0.65))
	
	jasha = stcSvc.spawnObject('jasha', 'tatooine', long(0), float(-1128), float(98.0), float(-3900), float(-0.42), float(0.90))

	dkrn = stcSvc.spawnObject('dkrn', 'tatooine', long(0), float(-1160), float(5.0), float(-3525), float(0.90), float(-0.41))
	
	gunham = stcSvc.spawnObject('gunham', 'tatooine', long(0), float(-1125), float(12.2), float(-3620), float(0.9), float(0.42))
	
	kormundThrylle = stcSvc.spawnObject('kormundthrylle', 'tatooine', long(0), float(-1043), float(10.0), float(-3530), float(0.99), float(0.06))
	
	calebKnolar = stcSvc.spawnObject('calebknolar', 'tatooine', long(0), float(-1149), float(98.0), float(-3903), float(0.42), float(0.91))
	
	#Junk Dealers
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1290.23), float(12.5278), float(-3655.77), float(0.850), float(0.526))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1257.72), float(12.5278), float(-3673.51), float(-0.525), float(0.851))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1025.98), float(10), float(-3558.69), float(0.830), float(-0.558))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1260), float(12), float(-3573), float(0.864), float(-0.503))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1126), float(12), float(-3674), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-1118), float(12), float(-3687), float(0.932), float(-0.364))
	return
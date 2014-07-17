import sys
# Project SWG:   MosEisley Rat Spawn:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService


	# City Sewer Swamprats
	city_sewer = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3216), float(8), float(-4465), float(0), float(0), float(0), float(0), 30)	
	city_sewer1 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3212), float(8), float(-4465), float(0), float(0), float(0), float(0), 30)	
	city_sewer2 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3208), float(8), float(-4467), float(0), float(0), float(0), float(0), 30)	
	city_sewer3 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3218), float(8), float(-4463), float(0), float(0), float(0), float(0), 30)	
	city_sewer4 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3220), float(8), float(-4462), float(0), float(0), float(0), float(0), 30)	
	city_sewer5 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3224), float(8), float(-4469), float(0), float(0), float(0), float(0), 30)	
	city_sewer6 = stcSvc.spawnObject('city_sewer_swamprat', 'tatooine', long(0), float(3218), float(8), float(-4467), float(0), float(0), float(0), float(0), 30)	

	
	
	# Sickly Womprats
	sickly_womprat = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3115), float(9), float(-4758), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat1 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3111), float(9), float(-4758), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat2 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3103), float(9), float(-4758), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat3 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3115), float(9), float(-4754), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat4 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3115), float(9), float(-4750), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat5 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3117), float(9), float(-4760), float(0), float(0), float(0), float(0), 30)		
	sickly_womprat6 = stcSvc.spawnObject('sickly_womprat', 'tatooine', long(0), float(3121), float(9), float(-4766), float(0), float(0), float(0), float(0), 30)		
	
	# Normal Womprats
	womprat = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3587), float(56), float(-4354), float(0), float(0), float(0), float(0), 30)		
	womprat1 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3583), float(56), float(-4354), float(0), float(0), float(0), float(0), 30)		
	womprat2 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3579), float(56), float(-4354), float(0), float(0), float(0), float(0), 30)		
	womprat3 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3587), float(56), float(-4350), float(0), float(0), float(0), float(0), 30)		
	womprat4 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3587), float(56), float(-4348), float(0), float(0), float(0), float(0), 30)		
	womprat5 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3587), float(56), float(-4358), float(0), float(0), float(0), float(0), 30)		
	womprat6 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3590), float(56), float(-4360), float(0), float(0), float(0), float(0), 30)		
	
	
	womprat2 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3841), float(49), float(-5072), float(0), float(0), float(0), float(0), 30)	
	womprat21 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3832), float(49), float(-5072), float(0), float(0), float(0), float(0), 30)	
	womprat22 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3823), float(49), float(-5072), float(0), float(0), float(0), float(0), 30)	
	womprat23 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3841), float(49), float(-5068), float(0), float(0), float(0), float(0), 30)	
	womprat24 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3841), float(49), float(-5060), float(0), float(0), float(0), float(0), 30)	
	womprat25 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3841), float(49), float(-5056), float(0), float(0), float(0), float(0), 30)	
	womprat26 = stcSvc.spawnObject('womprat', 'tatooine', long(0), float(3839), float(49), float(-5082), float(0), float(0), float(0), float(0), 30)	

	return

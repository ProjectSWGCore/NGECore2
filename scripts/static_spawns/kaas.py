import sys
# Project SWG:   Dromund Kaas:  Static Spawns
# (C)2014 ProjectSWG

# Do not make ANY changes to this script without direct approval from Levarris!

from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
#Dark Temple Area
	#Structures
	stcSvc.spawnObject('object/building/military/shared_military_base_police_station_imperial_lok_otto.iff', 'kaas', long(0), float(-5117.8), float(80.0), float(-2314.1), float(0), float(0))
	stcSvc.spawnObject('object/building/player/shared_player_house_corellia_medium_style_01.iff', 'kaas', long(0), float(-5078.6), float(80.0), float(-2302.5), float(0), float(0))
	stcSvc.spawnObject('object/building/general/shared_bunker_imperial_weapons_research_facility_01.iff', 'kaas', long(0), float(-5159.5), float(80.0), float(-2298.7), float(0), float(0))
	stcSvc.spawnObject('object/building/content/aurilia/shared_aurilia_pyramid_hut.iff', 'kaas', long(0), float(-5163.9), float(79.0), float(-2369.6), float(0.71), float(0.71))
	stcSvc.spawnObject('object/building/content/aurilia/shared_aurilia_pyramid_hut.iff', 'kaas', long(0), float(-5163.9), float(79.0), float(-2351.6), float(0.71), float(0.71))
	stcSvc.spawnObject('object/building/content/aurilia/shared_aurilia_pyramid_hut.iff', 'kaas', long(0), float(-5072.1), float(79.0), float(-2369.6), float(-0.71), float(0.71))
	stcSvc.spawnObject('object/building/content/aurilia/shared_aurilia_pyramid_hut.iff', 'kaas', long(0), float(-5072.1), float(79.0), float(-2351.6), float(-0.71), float(0.71))
	
	#Decor
	stcSvc.spawnObject('object/static/vehicle/shared_static_lambda_shuttle.iff', 'kaas', long(0), float(-5078.1), float(80.0), float(-2256.0), float(-0.70), float(0.70))
	stcSvc.spawnObject('object/building/content/aurilia/shared_aurilia_crystal_centerpiece.iff', 'kaas', long(0), float(-5121.5), float(80.0), float(-2360.5), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/tatooine/shared_antenna_tatt_style_1.iff', 'kaas', long(0), float(-5146.7), float(80.0), float(-2301.7), float(0), float(0))
	#Streetlamps
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5109.9), float(80.0), float(-2289.9), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5125.9), float(80.0), float(-2289.9), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5115.0), float(80.0), float(-2346.6), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5120.8), float(80.0), float(-2346.6), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5127.3), float(80.0), float(-2219.0), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5111.2), float(80.0), float(-2219.0), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5162.8), float(80.0), float(-2285.9), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5067.3), float(80.0), float(-2294.9), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5068.6), float(80.0), float(-2228.1), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5078.5), float(80.0), float(-2360.5), float(0), float(0))
	stcSvc.spawnObject('object/static/structure/general/shared_streetlamp_large_blue_style_01_on.iff', 'kaas', long(0), float(-5157.5), float(80.0), float(-2360.5), float(0), float(0))
	
	
	#Dark Temple Interior
	stcSvc.spawnObject('object/static/structure/content/shared_exar_kun_torch_01.iff', 'kaas', long(468319), float(0.0), float(-1.3), float(20.8), float(0), float(0))
	
	
#Village of the Descendants Area
	stcSvc.spawnObject('object/building/general/shared_cave_01.iff', 'kaas', long(0), float(3348.1), float(110), float(2562.1), float(0), float(0))

#Kaas City Ruins

#Tomb of Vitiate	
	return
	

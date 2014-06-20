import sys
# Project SWG:   Jabba TP Alkharan Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5454.6), float(38.9), float(-6122), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5436.6), float(39), float(-6122.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5431.7), float(37.8), float(-6104.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5439.2), float(39), float(-6100.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5459.8), float(38.4), float(-6109.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5412.5), float(36.5), float(-6105.3), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5449.5), float(38.7), float(-6103), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5440.4), float(37.5), float(-6112.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5425.2), float(37.4), float(-6120.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5419.6), float(37), float(-6112.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5416.9), float(39.2), float(-6122.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5452.6), float(40.1), float(-6127.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('alkhara_bandit', 'tatooine', long(0), float(-5445.4), float(39.4), float(-6124.9), float(0), float(0), float(0), float(0), 45)
	return
	

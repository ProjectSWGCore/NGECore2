import sys
# Project SWG:   MosEisley Quest:  Static Spawns
# (C)2014 ProjectSWG

# This script is intended for any quest conversation mobiles, or single attackable npcs for a quest, within Mos Eisley
def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Talk to Pall
	pall = stcSvc.spawnObject('majolnir', 'tatooine', long(0), float(3526), float(5), float(-4619), float(0), float(0), float(0), float(0), 1)	
	
	# Bib's Offer
	vourk = stcSvc.spawnObject('vourk', 'tatooine', long(0), float(3521.0), float(5.0), float(-4821.0), float(0.42), float(0.91))

	return
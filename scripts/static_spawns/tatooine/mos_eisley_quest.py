import sys
# Project SWG:   MosEisley Quest:  Static Spawns
# (C)2014 ProjectSWG

from resources.datatables import Options
from resources.datatables import State

# This script is intended for any quest conversation mobiles, or single attackable npcs for a quest, within Mos Eisley
def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Pall - Talk to Pall
	pall = stcSvc.spawnObject('majolnir', 'tatooine', long(0), float(3526), float(5), float(-4619), float(0), float(0), float(0), float(0), 1)	
	
	return
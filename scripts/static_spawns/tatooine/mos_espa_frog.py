# Project SWG:   Mos Espa:  Frogbuilding
# (C)2014 ProjectSWG

import sys

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(-3308), float(5.4), float(2174), float(0.70), float(0.71))
	return
	

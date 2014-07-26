# Project SWG:   Anchorhead:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	#building = core.objectService.getObject(long(-466404040703447347)) 
	#stcSvc.spawnObject('object/mobile/shared_han_solo.iff', 'tatooine', building.getCellByCellNumber(9), float(32.3), float(7.0), float(1.6), float(0), float(0), float(0), float(0)) 
	
	# Disabled for now. She seems to be the cause for quite a few crashes (but not the only reason for the crashes)
	#valarian = stcSvc.spawnObject('object/mobile/shared_lady_valarian.iff', 'tatooine', building.getCellByCellNumber(4), float(-21.5), float(9.0), float(0.5), float(0), float(0.70), float(0), float(-0.71)) 
	#valarian.setCustomName('Lady Valarian')
	#valarian.setOptionsBitmask(256)

	return
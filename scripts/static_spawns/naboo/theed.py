import sys
# Project SWG:   Theed:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	#Profession Counselor
	stcSvc.spawnObject('object/mobile/shared_respec_seller_f_1.iff', 'naboo', long(0), float(-4821), float(6), float(4140), float(-0.84), float(0.33))
	
	return
	

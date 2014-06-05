
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	#Junkdealer
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_03.iff', 'naboo', long(0), float(4318.83), float(5.15), float(-4797.57), float(0), float(1))
	
	return	

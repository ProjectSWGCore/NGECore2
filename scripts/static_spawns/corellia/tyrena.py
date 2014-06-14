
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService

	#junkdealer will be added as soon as i find coords
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-5088), float(21.3), float(-2384), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-5125.2), float(21.3), float(-2384), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-5278), float(21), float(-2510), float(0), float(0))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-5469), float(21), float(-2657), float(0), float(1))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'corellia', long(0), float(-5502), float(21), float(-2709), float(0), float(1))
	return	

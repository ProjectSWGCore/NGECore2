import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	#junkdealer 
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5694), float(6.5), float(4182), float(0.707), float(-0.707))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5717), float(6.5), float(4159), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5086), float(6), float(4142), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5147), float(6.5), float(4158), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5114), float(6.5), float(4161), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5222), float(6), float(4217), float(0.71), float(-0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5127), float(6), float(4239), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5761), float(6.6), float(4234), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5475), float(6), float(4105), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-4999), float(6), float(4119), float(0.71), float(0.71))
	stcSvc.spawnObject('object/mobile/shared_junk_dealer_m_01.iff', 'naboo', long(0), float(-5883), float(6), float(4214), float(0.71), float(0.71))

	return	

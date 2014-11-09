import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D


def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService

	# Stormtrooper Gate Guards
	guard1 = stcSvc.spawnObject('vic_dark_trooper_80', 'dathomir', long(0), float(-5775), float(510), float(-6553), float(0.36), float(0.85))
	guard2 = stcSvc.spawnObject('vic_dark_trooper_80', 'dathomir', long(0), float(-5785), float(510), float(-6545), float(0.43), float(0.85))
	gateofficer1 = stcSvc.spawnObject('vet_imperial_army_captain', 'dathomir', long(0), float(-5780), float(510), float(-6550), float(0.40), float(0.85))
	return	

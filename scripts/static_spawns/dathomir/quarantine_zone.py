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
	guard1 = stcSvc.spawnObject('vic_dark_trooper_80', 'dathomir', long(0), float(-5775), float(510), float(-6555), float(0.25), float(0.85))
	guard2 = stcSvc.spawnObject('vic_dark_trooper_80', 'dathomir', long(0), float(-5785), float(510), float(-6545), float(0.43), float(0.85))
	return	

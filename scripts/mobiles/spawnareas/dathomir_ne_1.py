# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('dathomir_bolma')
	dynamicGroups.add('dathomir_purbole')
	dynamicGroups.add('dathomir_rancor')
	dynamicGroups.add('dathomir_rhoa')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 5000, 5000, 4000, 'dathomir')
	return

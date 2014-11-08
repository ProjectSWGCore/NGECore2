# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('dathomir_nightsister')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -4055, -81, 2300, 'dathomir')
	return

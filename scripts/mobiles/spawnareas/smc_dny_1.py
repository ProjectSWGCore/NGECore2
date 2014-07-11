# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('smc_dyn_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 512, 4792, 1966, 'dathomir')
	return

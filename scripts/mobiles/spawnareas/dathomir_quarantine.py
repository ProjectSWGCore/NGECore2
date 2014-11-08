import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('dathomir_level_5_deathtroopers')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5700, -6478, 100, 'dathomir')
	return

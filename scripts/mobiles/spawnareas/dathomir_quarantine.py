import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('dathomir_level_5_deathtroopers')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5700, -6500, 100, 'dathomir')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5640, -6550, 100, 'dathomir')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5850, -6400, 100, 'dathomir')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5800, -6500, 100, 'dathomir')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5600, -6525, 100, 'dathomir')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5850, -6725, 530, 'dathomir')
	return

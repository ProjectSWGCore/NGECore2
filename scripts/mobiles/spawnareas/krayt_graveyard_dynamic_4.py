import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('krayt_dragon_ancient_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 6794, 4307, 10, 'tatooine')
	return
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('grand_canyon_krayt_dragon_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 7431, 4762, 120, 'tatooine')
	return
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mos_eisley_womprats_01')
	dynamicGroups.add('mos_eisley_mound_mites_01')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3587, -4354, 100, 'tatooine')
	return
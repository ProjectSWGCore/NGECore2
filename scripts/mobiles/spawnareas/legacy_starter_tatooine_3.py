import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mos_eisley_tame_worrts_01')
	dynamicGroups.add('mos_eisley_rock_mites_01')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3153, -4945, 100, 'tatooine')
	return
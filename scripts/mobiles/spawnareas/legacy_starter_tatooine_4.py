import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mos_eisley_tempest_rills_01')
	dynamicGroups.add('mos_eisley_mound_mites_01')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3841, -4345, 100, 'tatooine')
	return
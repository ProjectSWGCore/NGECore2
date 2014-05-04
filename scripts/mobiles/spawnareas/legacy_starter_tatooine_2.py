import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mos_eisley_tame_worrts_01')
	dynamicGroups.add('mos_eisley_sickly_womp_rats_01')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3115, -4758, 100, 'tatooine')
	return
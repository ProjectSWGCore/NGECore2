# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('piket_plains_walker_group')
	dynamicGroups.add('quenker_relic_reaper_group')
	dynamicGroups.add('thune_group_2')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 1403, 3575, 5406, 'dantooine')
	return

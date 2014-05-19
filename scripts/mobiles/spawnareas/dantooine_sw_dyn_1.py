# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mokk_group_1')
	dynamicGroups.add('voritors_wild_1')
	dynamicGroups.add('quenker_relic_reaper_group')
	dynamicGroups.add('graul_mauler_nw_group_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -7546, -2425, 4464, 'dantooine')
	return

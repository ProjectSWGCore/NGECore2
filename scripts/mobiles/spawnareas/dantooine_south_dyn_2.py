# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('jantas_nw_group_1')
	dynamicGroups.add('graul_mauler_nw_group_1')
	dynamicGroups.add('quenker_relic_reaper_group')
	dynamicGroups.add('thune_group_2')
	dynamicGroups.add('bol_group_1')
	dynamicGroups.add('voritors_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -2109, -7045, 2600, 'dantooine')
	return

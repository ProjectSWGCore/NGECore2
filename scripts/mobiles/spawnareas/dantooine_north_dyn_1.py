# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('force_sensitives_wild_1')
	dynamicGroups.add('graul_mauler_nw_group_1')
	dynamicGroups.add('voritors_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -1299, 8101, 3993, 'dantooine')
	return

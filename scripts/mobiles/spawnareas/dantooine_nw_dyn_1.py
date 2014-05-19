# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('voritor_nw_group_1')
	dynamicGroups.add('graul_mauler_nw_group_1')
	dynamicGroups.add('jantas_nw_group_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -7464, 7651, 6492, 'dantooine')
	return

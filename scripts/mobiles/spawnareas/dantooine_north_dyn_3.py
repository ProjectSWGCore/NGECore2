# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mokk_group_1')
	dynamicGroups.add('huurton_group_1')
	dynamicGroups.add('bol_group_1')
	dynamicGroups.add('quenker_group_south_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 297, 4886, 2457, 'dantooine')
	return

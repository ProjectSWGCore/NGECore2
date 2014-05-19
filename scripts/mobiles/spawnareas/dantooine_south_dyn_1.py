# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('huurton_group_1')
	dynamicGroups.add('quenker_group_south_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -112, -2117, 4485, 'dantooine')
	return

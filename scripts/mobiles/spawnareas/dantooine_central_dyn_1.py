# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('frenzied_graul_1')
	dynamicGroups.add('thunes_1')
	dynamicGroups.add('kunga_central_group_1')
	dynamicGroups.add('huurton_group_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 1567, -1585, 2293, 'dantooine')
	return

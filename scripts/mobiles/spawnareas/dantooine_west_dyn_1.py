# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('kunga_central_group_1')
	dynamicGroups.add('piket_longhorn_group_1')
	dynamicGroups.add('thunes_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -7832, 1507, 3993, 'dantooine')
	return

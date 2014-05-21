# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('voritors_east_group_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 5713, -1146, 2416, 'dantooine')
	return

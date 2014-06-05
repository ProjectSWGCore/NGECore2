# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('voritors_east_group_1')
	mixedGroups.add('voritor_lair_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, 5672, -1146, 2396, 'dantooine')
	return

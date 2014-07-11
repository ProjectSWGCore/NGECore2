# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('voritor_lair_group_2')
	core.spawnService.addMixedSpawnArea(mixedGroups, 5345, -5795, 2560, 'dantooine')
	return

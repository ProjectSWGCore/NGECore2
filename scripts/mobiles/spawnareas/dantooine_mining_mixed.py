# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('piket_plains_walker_group')
	mixedGroups.add('mixed_lair_group_1')
	mixedGroups.add('naboo_starter')
	core.spawnService.addMixedSpawnArea(mixedGroups, 0, 2355, 1024, 'dantooine')
	return

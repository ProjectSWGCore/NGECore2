# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('quenker_lair_group_1')
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('quenker_group_south_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, 1495, -3502, 2396, 'dantooine')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('mokk_group_1')
	mixedGroups.add('piket_longhorn_lair_group_1')
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('bol_lair_group_1')
	mixedGroups.add('huurton_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -1740, -3297, 1822, 'dantooine')
	return

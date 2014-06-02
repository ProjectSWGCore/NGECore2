# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('force_sensitives_wild_1')
	mixedGroups.add('graul_mauler_lair_group_1')
	mixedGroups.add('graul_mauler_nw_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, 20, 6574, 1966, 'dantooine')
	return

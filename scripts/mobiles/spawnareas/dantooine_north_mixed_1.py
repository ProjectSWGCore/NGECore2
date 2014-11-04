# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_force_sensitive')
	mixedGroups.add('graul_mauler_lair_group_1')
	mixedGroups.add('dantooine_graul_mauler')
	core.spawnService.addMixedSpawnArea(mixedGroups, 20, 6574, 1966, 'dantooine')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_jantas')
	mixedGroups.add('graul_mauler_lair_group_1')
	mixedGroups.add('dantooine_quenker_relic')
	mixedGroups.add('voritor_lair_group_1')
	mixedGroups.add('thune_lair_group_1')
	mixedGroups.add('bol_lair_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -2109, -6983, 2580, 'dantooine')
	return

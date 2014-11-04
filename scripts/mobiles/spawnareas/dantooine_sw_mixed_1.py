# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_mokk')
	mixedGroups.add('voritor_lair_group_1')
	mixedGroups.add('graul_mauler_lair_group_1')
	mixedGroups.add('dantooine_quenker_relic')
	core.spawnService.addMixedSpawnArea(mixedGroups, -5877, -4239, 2170, 'dantooine')
	return

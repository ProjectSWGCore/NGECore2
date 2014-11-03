# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('quenker_lair_group_1')
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('bol_lair_group_1')
	mixedGroups.add('dantooine_mokk')
	core.spawnService.addMixedSpawnArea(mixedGroups, 757, 4321, 1167, 'dantooine')
	return

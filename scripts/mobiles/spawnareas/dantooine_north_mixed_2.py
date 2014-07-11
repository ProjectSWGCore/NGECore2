# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('bol_lair_group_1')
	mixedGroups.add('mokk_group_1')
	mixedGroups.add('quenker_lair_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -1454, 3829, 1044, 'dantooine')
	return

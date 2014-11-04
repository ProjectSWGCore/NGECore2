# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_kunga')
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('thune_lair_group_1')
	mixedGroups.add('graul_lair_group_1')
	mixedGroups.add('dantooine_huurton')
	core.spawnService.addMixedSpawnArea(mixedGroups, 1044, -798, 2252, 'dantooine')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_kunga')
	mixedGroups.add('dantooine_piket')
	mixedGroups.add('thune_lair_group_1')
	mixedGroups.add('dantooine_piket')
	core.spawnService.addMixedSpawnArea(mixedGroups, -6410, 0, 1986, 'dantooine')
	return

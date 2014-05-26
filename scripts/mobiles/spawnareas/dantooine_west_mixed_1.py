# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('kunga_central_group_1')
	mixedGroups.add('piket_longhorn_lair_group_1')
	mixedGroups.add('thune_lair_group_1')
	mixedGroups.add('piket_longhorn_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -6410, 0, 1986, 'dantooine')
	return

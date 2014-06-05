# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('dantooine_nw_lair_group_1')
	mixedGroups.add('jantas_nw_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -4751, 4935, 3200, 'dantooine')
	return

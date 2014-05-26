# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('piket_plains_walker_lair_group')
	mixedGroups.add('quenker_relic_reaper_group')
	mixedGroups.add('thune_lair_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, 3440, 1310, 2641, 'dantooine')
	return

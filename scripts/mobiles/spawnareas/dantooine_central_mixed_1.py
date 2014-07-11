# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	mixedGroups = Vector()
	mixedGroups.add('huurton_lair_group_1')
	mixedGroups.add('piket_longhorn_lair_group_1')
	mixedGroups.add('bol_lair_group_1')
	core.spawnService.addMixedSpawnArea(mixedGroups, -2580, 737, 2068, 'dantooine')
	return

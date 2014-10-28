# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('yavin4_black_sun')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -4680, 4330, 150, 'endor')
	return

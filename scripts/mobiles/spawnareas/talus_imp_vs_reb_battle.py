# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('adolescent_krayt_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -2595, 3724, 500, 'talus')
	return

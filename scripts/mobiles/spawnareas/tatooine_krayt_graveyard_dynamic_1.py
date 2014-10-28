# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('adolescent_krayt_wild_1')
	dynamicGroups.add('juvenile_krayts_wild_1')
	dynamicGroups.add('canyon_krayt_wild_1')
	dynamicGroups.add('giant_canyon_krayt_wild_1')
	dynamicGroups.add('bantha')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 6471, 4267, 1024, 'tatooine')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('adolescent_krayt_wild_1')
	dynamicGroups.add('lok_kimogila')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3091, -4638, 500, 'lok')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('imperial')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -1814, -3086, 700, 'lok')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('rebel')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -6508, 5985, 300, 'corellia')
	return

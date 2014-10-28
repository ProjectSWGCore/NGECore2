# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('rebel')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3689, -6403, 150, 'rori')
	return

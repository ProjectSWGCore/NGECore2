# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('rori_borgle')
	dynamicGroups.add('rori_torton')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -5000, -5000, 3500, 'rori')
	return

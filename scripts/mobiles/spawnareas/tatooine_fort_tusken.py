# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('tatooine_tusken')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -4000, 6250, 300, 'tatooine')
	return

# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('tatooine_tusken_wild')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 4310, -4786, 250, 'tatooine')
	return

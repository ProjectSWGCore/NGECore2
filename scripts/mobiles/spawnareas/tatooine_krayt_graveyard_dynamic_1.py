# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('tatooine_adolescent_krayt')
	dynamicGroups.add('tatooine_juvenile_krayt')
	dynamicGroups.add('tatooine_canyon_krayt')
	dynamicGroups.add('tatooine_giant_canyon_krayt')
	dynamicGroups.add('tatooine_bantha')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 6471, 4267, 1024, 'tatooine')
	return

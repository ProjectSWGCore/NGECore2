# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('lok_kimogila')
	dynamicGroups.add('dark_jedi_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -70, 2650, 500, 'lok')
	return

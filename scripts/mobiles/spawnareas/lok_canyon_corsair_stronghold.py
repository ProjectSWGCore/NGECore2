# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('dark_jedi_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -3840, -3858, 500, 'lok')
	return

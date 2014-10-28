# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('talus_aakuan')
	dynamicGroups.add('talus_lost_aqualish')
	dynamicGroups.add('talus_chunker')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 6000, -500, 2500, 'talus')
	return

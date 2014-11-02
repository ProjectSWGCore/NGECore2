# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('corellia_corsec')
	dynamicGroups.add('corellia_hidden_daggers')
	dynamicGroups.add('corellia_cor_butterfly')
	dynamicGroups.add('corellia_meatlump')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 6000, 6000, 2500, 'corellia')
	return

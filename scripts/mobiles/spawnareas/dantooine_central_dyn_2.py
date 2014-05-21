# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('bol_group_1')
	dynamicGroups.add('piket_longhorn_group_1')
	dynamicGroups.add('huurton_group_2')
	dynamicGroups.add('kunga_central_group_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -2539, 716, 2109, 'dantooine')
	return

import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('force_sensitives_wild_1')
	dynamicGroups.add('dark_jedi_wild_1')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 4083, 5780, 2048, 'dantooine')
	return
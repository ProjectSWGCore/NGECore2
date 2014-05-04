import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('mos_eisley_carrion_kreetle_01')
	dynamicGroups.add('mos_eisley_city_sewer_rats_01')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, 3216, -4465, 100, 'tatooine')
	return
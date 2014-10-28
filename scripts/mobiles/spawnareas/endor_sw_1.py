# Spawn Area file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector

def addSpawnArea(core):
	dynamicGroups = Vector()
	dynamicGroups.add('endor_bark_mite')
	dynamicGroups.add('endor_bloodseeker')
	dynamicGroups.add('endor_bolle')
	dynamicGroups.add('endor_donkuwah')
	dynamicGroups.add('endor_jinda')
	core.spawnService.addDynamicSpawnArea(dynamicGroups, -6000, -4000, 2500, 'endor')
	return

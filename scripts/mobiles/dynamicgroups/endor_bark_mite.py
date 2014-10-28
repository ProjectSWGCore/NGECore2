# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('bark_mite_burrower')
	mobileTemplates.add('bark_mite_burrower_collector')
	mobileTemplates.add('bark_mite_burrower_drone')
	mobileTemplates.add('bark_mite_burrower_swarmling')
	mobileTemplates.add('bark_mite_burrower_worker')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('endor_bark_mite')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('endor_bark_mite', dynamicGroup)
	return

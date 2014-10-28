# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('corellian_butterfly')
	mobileTemplates.add('corellian_butterfly_defender')
	mobileTemplates.add('corellian_butterfly_drone')
	mobileTemplates.add('corellian_butterfly_monarch')
	mobileTemplates.add('corellian_butterfly_warrior')
	mobileTemplates.add('corellian_butterfly_worker')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('corellia_cor_butterfly')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('corellia_cor_butterfly', dynamicGroup)
	return

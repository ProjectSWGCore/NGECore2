# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('jinda_chief')
	mobileTemplates.add('jinda_cub')
	mobileTemplates.add('jinda_loremaster')
	mobileTemplates.add('jinda_outcast')
	mobileTemplates.add('jinda_shaman')
	mobileTemplates.add('jinda_veteran')
	mobileTemplates.add('jinda_warrior')
	mobileTemplates.add('jinda_worker')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('endor_jinda')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('endor_jinda', dynamicGroup)
	return

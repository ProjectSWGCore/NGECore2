# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('singing_mountain_clan_sentry')
	mobileTemplates.add('singing_mtn_clan_dragoon')
	mobileTemplates.add('singing_mtn_clan_guardian')
	mobileTemplates.add('singing_mtn_clan_huntress')
	mobileTemplates.add('singing_mtn_clan_initiate')
	mobileTemplates.add('singing_mtn_clan_outcast')
	mobileTemplates.add('singing_mtn_clan_scout')
	mobileTemplates.add('singing_mtn_clan_slave')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setName('smc_dyn_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('smc_dyn_1', dynamicGroup)
	return

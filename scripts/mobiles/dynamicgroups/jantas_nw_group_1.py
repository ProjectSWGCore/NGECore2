# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('janta_clan_leader')
	mobileTemplates.add('janta_harvester')
	mobileTemplates.add('janta_herbalist')
	mobileTemplates.add('janta_hunter')
	mobileTemplates.add('janta_loreweaver')
	mobileTemplates.add('janta_primalist')
	mobileTemplates.add('janta_rockshaper')
	mobileTemplates.add('janta_scout')
	mobileTemplates.add('janta_shaman')
	mobileTemplates.add('janta_soothsayer')
	mobileTemplates.add('janta_tribesman')
	mobileTemplates.add('janta_warrior')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('jantas_nw_group_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('jantas_nw_group_1', dynamicGroup)
	return

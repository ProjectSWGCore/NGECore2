# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('mokk_clan_leader')
	mobileTemplates.add('mokk_clan_primalist')
	mobileTemplates.add('mokk_harvester')
	mobileTemplates.add('mokk_herbalist')
	mobileTemplates.add('mokk_hunter')
	mobileTemplates.add('mokk_loreweaver')
	mobileTemplates.add('mokk_rockshaper')
	mobileTemplates.add('mokk_scout')
	mobileTemplates.add('mokk_shaman')
	mobileTemplates.add('mokk_soothsayer')
	mobileTemplates.add('mokk_tribesman')
	mobileTemplates.add('mokk_warrior')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('mokk_group_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('mokk_group_1', dynamicGroup)
	return

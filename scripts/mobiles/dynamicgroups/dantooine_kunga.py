# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('kunga_clan_leader')
	mobileTemplates.add('kunga_clan_primalist')
	mobileTemplates.add('kunga_harvester')
	mobileTemplates.add('kunga_herbalist')
	mobileTemplates.add('kunga_hunter')
	mobileTemplates.add('kunga_loreweaver')
	mobileTemplates.add('kunga_rockshaper')
	mobileTemplates.add('kunga_scout')
	mobileTemplates.add('kunga_shaman')
	mobileTemplates.add('kunga_soothsayer')
	mobileTemplates.add('kunga_tribesman')
	mobileTemplates.add('kunga_warrior')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('dantooine_kunga')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dantooine_kunga', dynamicGroup)
	return

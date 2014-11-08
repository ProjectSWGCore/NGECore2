# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('desert_demon')
	mobileTemplates.add('desert_demon_bodyguard')
	mobileTemplates.add('desert_demon_brawler')
	mobileTemplates.add('desert_demon_leader')
	mobileTemplates.add('desert_demon_marksman')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('tatooine_desert_demon')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_desert_demon', dynamicGroup)
	return

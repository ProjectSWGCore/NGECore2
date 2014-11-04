# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('choku')
	mobileTemplates.add('choku_female')
	mobileTemplates.add('choku_hunter')
	mobileTemplates.add('choku_male')
	mobileTemplates.add('choku_packmaster')
	mobileTemplates.add('choku_pup')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('yavin4_choku')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('yavin4_choku', dynamicGroup)
	return

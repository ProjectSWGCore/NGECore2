# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('skreeg_adolescent')
	mobileTemplates.add('skreeg_female')
	mobileTemplates.add('skreeg_gatherer')
	mobileTemplates.add('skreeg_hunter')
	mobileTemplates.add('skreeg_infant')
	mobileTemplates.add('skreeg_male')
	mobileTemplates.add('skreeg_scout')
	mobileTemplates.add('skreeg_warrior')
	mobileTemplates.add('skreeg_warrior_elite')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(15)
	dynamicGroup.setName('yavin4_skreeg')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('yavin4_skreeg', dynamicGroup)
	return

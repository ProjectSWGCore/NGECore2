# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('aakuan_champion')
	mobileTemplates.add('aakuan_defender')
	mobileTemplates.add('aakuan_follower')
	mobileTemplates.add('aakuan_guardian')
	mobileTemplates.add('aakuan_keeper')
	mobileTemplates.add('aakuan_sentinal')
	mobileTemplates.add('aakuan_steward')
	mobileTemplates.add('aakuan_warder')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('talus_aakuan')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('talus_aakuan', dynamicGroup)
	return

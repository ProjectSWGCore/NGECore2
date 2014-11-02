# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('blood_thirsty_borgle')
	mobileTemplates.add('borgle_harvester')
	mobileTemplates.add('borgle')
	mobileTemplates.add('borgle_matriarch')
	mobileTemplates.add('borgle_protector')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('rori_borgle')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('rori_borgle', dynamicGroup)
	return

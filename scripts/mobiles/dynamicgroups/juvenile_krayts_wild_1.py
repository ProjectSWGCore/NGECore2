import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('juvenile_canyon_krayt_dragon')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(6)
	dynamicGroup.setName('juvenile_krayts_wild_1')
	dynamicGroup.setMaxSpawns(1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('juvenile_krayts_wild_1', dynamicGroup)
	return
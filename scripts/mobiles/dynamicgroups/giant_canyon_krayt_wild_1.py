import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('giant_canyon_krayt_dragon')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(1)
	dynamicGroup.setName('giant_canyon_krayt_dragon_1')
	dynamicGroup.setMaxSpawns(1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('giant_canyon_krayt_dragon_1', dynamicGroup)
	return
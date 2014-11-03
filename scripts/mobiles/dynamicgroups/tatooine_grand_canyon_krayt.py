import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('grand_canyon_krayt_dragon')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(1)
	dynamicGroup.setName('tatooine_grand_canyon_krayt')
	dynamicGroup.setMaxSpawns(1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('tatooine_grand_canyon_krayt', dynamicGroup)
	return
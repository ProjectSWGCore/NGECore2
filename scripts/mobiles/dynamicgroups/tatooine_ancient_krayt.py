import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('krayt_dragon_ancient')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(1)
	dynamicGroup.setName('tatooine_ancient_krayt')
	dynamicGroup.setMaxSpawns(1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_ancient_krayt', dynamicGroup)
	return
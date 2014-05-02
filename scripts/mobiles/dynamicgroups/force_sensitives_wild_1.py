import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('tusken_raider')
	mobileTemplates.add('tusken_raider')
	mobileTemplates.add('tusken_raider')
	dynamicGroup.setMobiles(mobileTemplates)
	memberCLs = Vector()
	memberCLs.add(72)
	memberCLs.add(90)
	memberCLs.add(90)
	dynamicGroup.setmemberCLs(memberCLs)
	dynamicGroup.setGroupMemberNumber(3)
	dynamicGroup.setName('force_sensitives_wild_1')
	dynamicGroup.setMaxSpawns(20)
	dynamicGroup.setMinSpawnDistance(2)
	dynamicGroup.setMaxSpawnDistance(4)
	core.spawnService.addDynamicGroup('force_sensitives_wild_1', dynamicGroup)
	return
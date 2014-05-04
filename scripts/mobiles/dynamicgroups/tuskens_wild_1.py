import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('tusken_raider')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setName('tuskens_wild_1')
	dynamicGroup.setMaxSpawns(20)
	dynamicGroup.setMinSpawnDistance(25)
	core.spawnService.addDynamicGroup('tuskens_wild_1', dynamicGroup)
	return
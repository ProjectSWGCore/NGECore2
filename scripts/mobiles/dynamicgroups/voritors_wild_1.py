import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('slinking_voritor_hunter')
	mobileTemplates.add('horned_voritor_jungle_lizard')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(1)
	dynamicGroup.setName('voritors_wild_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('voritors_wild_1', dynamicGroup)
	return
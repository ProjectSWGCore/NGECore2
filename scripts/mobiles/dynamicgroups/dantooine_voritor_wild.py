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
	dynamicGroup.setName('dantooine_voritor_wild')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dantooine_voritor_wild', dynamicGroup)
	return
# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('horned_voritor_jungle_lizard')
	mobileTemplates.add('vexed_voritor_lizard')
	mobileTemplates.add('voritor_dasher')
	mobileTemplates.add('voritor_lizard')
	mobileTemplates.add('spiked_slasher')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('voritors_east_group_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('voritors_east_group_1', dynamicGroup)
	return

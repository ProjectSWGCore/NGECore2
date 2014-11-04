# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('kimogila')
	mobileTemplates.add('kimogila_aged')
	mobileTemplates.add('kimogila_dwarf')
	mobileTemplates.add('kimogila_hatchling')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(2)
	dynamicGroup.setName('lok_kimogila')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('lok_kimogila', dynamicGroup)
	return

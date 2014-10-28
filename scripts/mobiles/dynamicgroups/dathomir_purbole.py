# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('purbole')
	mobileTemplates.add('purbole_elder')
	mobileTemplates.add('purbole_hunter')
	mobileTemplates.add('purbole_scout')
	mobileTemplates.add('purbole_stalker')
	mobileTemplates.add('purbole_youth')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('dathomir_purbole')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dathomir_purbole', dynamicGroup)
	return

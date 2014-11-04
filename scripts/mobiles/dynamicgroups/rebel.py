# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('rebel_elite_heavy_trooper_88')
	mobileTemplates.add('decorated_rebel_colonel_88')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	mobileTemplates.add('rebel_dark_trooper_hunter_80')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setName('rebel')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('rebel', dynamicGroup)
	return

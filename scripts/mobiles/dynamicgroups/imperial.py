# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('ace_imp_storm_com_87')
	mobileTemplates.add('decorated_imperial_colonel_87')
	mobileTemplates.add('fbase_at_st')
	mobileTemplates.add('imp_stormtrooper_80')
	mobileTemplates.add('imp_stormtrooper_80')
	mobileTemplates.add('imp_stormtrooper_80')
	mobileTemplates.add('imp_stormtrooper_80')
	mobileTemplates.add('imp_stormtrooper_80')
	mobileTemplates.add('imp_stormtrooper_80')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setName('imperial')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(80)
	core.spawnService.addDynamicGroup('imperial', dynamicGroup)
	return

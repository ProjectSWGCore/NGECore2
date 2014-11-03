# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('nightsister_initiate')
	mobileTemplates.add('nightsister_outcast')
	mobileTemplates.add('nightsister_protector')
	mobileTemplates.add('nightsister_ranger')
	mobileTemplates.add('nightsister_sentinel')
	mobileTemplates.add('nightsister_sentry')
	mobileTemplates.add('nightsister_spell_weaver')
	mobileTemplates.add('nightsister_stalker')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setName('dathomir_nightsister')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(100)
	core.spawnService.addDynamicGroup('dathomir_nightsister', dynamicGroup)
	return

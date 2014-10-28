# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('rhoa_kwi')
	mobileTemplates.add('rhoa_kwi_guardian')
	mobileTemplates.add('rhoa_kwi_hunter')
	mobileTemplates.add('rhoa_kwi_pack_leader')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('dathomir_rhoa')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dathomir_rhoa', dynamicGroup)
	return

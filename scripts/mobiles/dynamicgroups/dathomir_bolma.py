# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('bolma')
	mobileTemplates.add('bolma_calf')
	mobileTemplates.add('bolma_female')
	mobileTemplates.add('bolma_male')
	mobileTemplates.add('bolma_pack_master')
	mobileTemplates.add('bolma_prime')
	mobileTemplates.add('bolma_youth')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('dathomir_bolma')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dathomir_bolma', dynamicGroup)
	return

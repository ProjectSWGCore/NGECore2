# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('bolle_bol_bark_biter')
	mobileTemplates.add('bolle_bol_calf')
	mobileTemplates.add('bolle_bol_colt')
	mobileTemplates.add('bolle_bol_female')
	mobileTemplates.add('bolle_bol_stomper')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('endor_bolle')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('endor_bolle', dynamicGroup)
	return

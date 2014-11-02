# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('bloodseeker_mite')
	mobileTemplates.add('bloodseeker_mite_drone')
	mobileTemplates.add('bloodseeker_mite_guardian')
	mobileTemplates.add('bloodseeker_mite_hunter')
	mobileTemplates.add('bloodseeker_mite_queen')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('endor_bloodseeker')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('endor_bloodseeker', dynamicGroup)
	return

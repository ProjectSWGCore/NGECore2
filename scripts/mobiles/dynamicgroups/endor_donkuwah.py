# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('donkuwah_battlelord')
	mobileTemplates.add('donkuwah_cub')
	mobileTemplates.add('donkuwah_scout')
	mobileTemplates.add('donkuwah_shaman')
	mobileTemplates.add('donkuwah_spiritmaster')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('endor_donkuwah')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('endor_donkuwah', dynamicGroup)
	return

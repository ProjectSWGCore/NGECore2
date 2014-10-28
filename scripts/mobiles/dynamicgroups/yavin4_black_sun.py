# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('mand_bunker_blksun_assassin')
	mobileTemplates.add('mand_bunker_blksun_guard')
	mobileTemplates.add('mand_bunker_blksun_henchman')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('yavin4_black_sun')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('yavin4_black_sun', dynamicGroup)
	return

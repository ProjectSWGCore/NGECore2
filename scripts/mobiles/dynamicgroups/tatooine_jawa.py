# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('jawa')
	mobileTemplates.add('jawa_avenger')
	mobileTemplates.add('jawa_engineer')
	mobileTemplates.add('jawa_healer')
	mobileTemplates.add('jawa_henchman')
	mobileTemplates.add('jawa_leader')
	mobileTemplates.add('jawa_protector')
	mobileTemplates.add('jawa_thief')
	mobileTemplates.add('jawa_warlord')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(15)
	dynamicGroup.setName('tatooine_jawa')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_jawa', dynamicGroup)
	return

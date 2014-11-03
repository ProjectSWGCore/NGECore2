# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('tusken_captain')
	mobileTemplates.add('tusken_blood_champion')
	mobileTemplates.add('tusken_elite_guard')
	mobileTemplates.add('tusken_executioner')
	mobileTemplates.add('tusken_sniper')
	mobileTemplates.add('tusken_warlord')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('tatooine_tusken')
	dynamicGroup.setMaxSpawns(15)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_tusken', dynamicGroup)
	return

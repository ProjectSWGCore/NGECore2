# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('dune_stalker_brawler')
	mobileTemplates.add('dune_stalker_enforcer')
	mobileTemplates.add('dune_stalker_leader')
	mobileTemplates.add('dune_stalker_marksman')
	mobileTemplates.add('dune_stalker_scavenger')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('tatooine_dune_stalker')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_dune_stalker', dynamicGroup)
	return

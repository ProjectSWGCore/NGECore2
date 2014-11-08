# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('bantha')
	mobileTemplates.add('bantha_low')
	mobileTemplates.add('bantha_matriarch')
	mobileTemplates.add('dune_bantha')
	mobileTemplates.add('dwarf_bantha')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-5)
	dynamicGroup.setName('tatooine_bantha')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('tatooine_bantha', dynamicGroup)
	return

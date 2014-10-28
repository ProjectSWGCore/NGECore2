# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('meatlump_buffoon')
	mobileTemplates.add('meatlump_clod')
	mobileTemplates.add('meatlump_cretin')
	mobileTemplates.add('meatlump_dunder')
	mobileTemplates.add('meatlump_fool')
	mobileTemplates.add('meatlump_loon')
	mobileTemplates.add('meatlump_lunk')
	mobileTemplates.add('meatlump_oaf')
	mobileTemplates.add('meatlump_stooge')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('corellia_meatlump')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('corellia_meatlump', dynamicGroup)
	return

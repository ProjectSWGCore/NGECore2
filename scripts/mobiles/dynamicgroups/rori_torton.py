# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('tattered_torton')
	mobileTemplates.add('torton')
	mobileTemplates.add('torton_pygmy_juvenile')
	mobileTemplates.add('torton_pygmy_matriarch')
	mobileTemplates.add('torton_pygmy_mature')
	mobileTemplates.add('torton_pygmy_protector')
	mobileTemplates.add('torton_voracious_patriarch')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('rori_torton')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('rori_torton', dynamicGroup)
	return

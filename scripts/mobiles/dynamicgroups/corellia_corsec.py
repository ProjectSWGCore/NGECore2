# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('corsec_cadet')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_trooper')
	mobileTemplates.add('corsec_special_ops_captain')
	mobileTemplates.add('corsec_special_ops_inspector')
	mobileTemplates.add('corsec_sergeant')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('corellia_corsec')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('corellia_corsec', dynamicGroup)
	return

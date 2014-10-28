# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('lost_aqualish_bomber')
	mobileTemplates.add('lost_aqualish_captain')
	mobileTemplates.add('lost_aqualish_commando')
	mobileTemplates.add('lost_aqualish_infiltrator')
	mobileTemplates.add('lost_aqualish_lookout')
	mobileTemplates.add('lost_aqualish_marksman')
	mobileTemplates.add('lost_aqualish_marshal')
	mobileTemplates.add('lost_aqualish_outrider')
	mobileTemplates.add('lost_aqualish_scout')
	mobileTemplates.add('lost_aqualish_soldier')
	mobileTemplates.add('lost_aqualish_warchief')
	mobileTemplates.add('lost_aqualish_warrior')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(15)
	dynamicGroup.setName('talus_lost_aqualish')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('talus_lost_aqualish', dynamicGroup)
	return

# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('hidden_daggers_activist')
	mobileTemplates.add('hidden_daggers_dissident')
	mobileTemplates.add('hidden_daggers_extremist')
	mobileTemplates.add('hidden_daggers_leader')
	mobileTemplates.add('hidden_daggers_lieutenant')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(3)
	dynamicGroup.setName('corellia_hidden_daggers')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(60)
	core.spawnService.addDynamicGroup('corellia_hidden_daggers', dynamicGroup)
	return

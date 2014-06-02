# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('huurton')
	mobileTemplates.add('huurton_bloodhunter')
	mobileTemplates.add('huurton_howler')
	mobileTemplates.add('huurton_huntress')
	mobileTemplates.add('huurton_matron')
	mobileTemplates.add('huurton_reaper')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('huurton_group_1')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('huurton_group_1', dynamicGroup)
	return

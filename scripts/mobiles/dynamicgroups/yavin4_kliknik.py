# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('kliknik')
	mobileTemplates.add('kliknik_dark_defender')
	mobileTemplates.add('kliknik_dark_hunter')
	mobileTemplates.add('kliknik_dark_queen')
	mobileTemplates.add('kliknik_dark_warrior')
	mobileTemplates.add('kliknik_hatchling')
	mobileTemplates.add('kliknik_hunter')
	mobileTemplates.add('kliknik_mantis')
	mobileTemplates.add('kliknik_scout')
	mobileTemplates.add('kliknik_shredder_guardian')
	mobileTemplates.add('kliknik_warrior')
	mobileTemplates.add('kliknik_worker')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('yavin4_klinik')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('yavin4_klinik', dynamicGroup)
	return

# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from java.util import Vector
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()
	mobileTemplates = Vector()
	mobileTemplates.add('chunker_braggart')
	mobileTemplates.add('chunker_bruiser')
	mobileTemplates.add('chunker_bully')
	mobileTemplates.add('chunker_goon')
	mobileTemplates.add('chunker_mooch')
	mobileTemplates.add('chunker_nitwit')
	mobileTemplates.add('chunker_pest')
	mobileTemplates.add('chunker_punk')
	mobileTemplates.add('chunker_swindler')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(5)
	dynamicGroup.setName('talus_chunker')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('talus_chunker', dynamicGroup)
	return

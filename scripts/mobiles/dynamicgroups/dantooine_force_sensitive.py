import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('force_sensitive_crypt_crawler')
	mobileTemplates.add('force_sensitive_renegade')
	mobileTemplates.add('forsaken_force_drifter')
	mobileTemplates.add('novice_force_mystic')
	mobileTemplates.add('untrained_wielder_of_the_dark_side')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(-3)
	dynamicGroup.setName('dantooine_force_sensitive')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(150)
	core.spawnService.addDynamicGroup('dantooine_force_sensitive', dynamicGroup)
	return
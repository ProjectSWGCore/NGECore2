import sys
from services.spawn import DynamicSpawnGroup
from services.spawn import MobileTemplate
from java.util import Vector

def addDynamicGroup(core):
	dynamicGroup = DynamicSpawnGroup()	
	mobileTemplates = Vector()
	mobileTemplates.add('carrion_kreetle')
	dynamicGroup.setMobiles(mobileTemplates)
	dynamicGroup.setGroupMembersNumber(1)
	dynamicGroup.setName('mos_eisley_carrion_kreetle_01')
	dynamicGroup.setMaxSpawns(-1)
	dynamicGroup.setMinSpawnDistance(5)
	core.spawnService.addDynamicGroup('mos_eisley_carrion_kreetle_01', dynamicGroup)
	return
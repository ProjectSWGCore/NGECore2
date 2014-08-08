import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('corellian_butterfly')
	mobileTemplates.add('corellian_butterfly_monarch')
	mobileTemplates.add('corellian_butterfly_worker')
	
	core.spawnService.addLairTemplate('corellia_corellian_butterfly_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_insecthill_small_evil_fire_green.iff')
	return
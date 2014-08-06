import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('corellian_butterfly_worker')
	mobileTemplates.add('corellian_butterfly_defender')
	mobileTemplates.add('corellian_butterfly_drone')
	
	core.spawnService.addLairTemplate('corellia_corellian_butterfly_lair_4', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_insecthill_small_evil_fire_small.iff')
	return
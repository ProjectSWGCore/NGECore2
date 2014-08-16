import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('blood_thirsty_monarch')
	mobileTemplates.add('corellian_butterfly_monarch')
	mobileTemplates.add('corellian_butterfly_warrior')
	
	core.spawnService.addLairTemplate('corellia_corellian_butterfly_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_insecthill_small_fog_red.iff')
	return
import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('crazed_durni')
	
	core.spawnService.addLairTemplate('corellia_crazed_durni_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_fog_gray.iff')
	return
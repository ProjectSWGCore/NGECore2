import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('krahbu')
		
	core.spawnService.addLairTemplate('corellia_krahbu_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_thicket_small.iff')
	return
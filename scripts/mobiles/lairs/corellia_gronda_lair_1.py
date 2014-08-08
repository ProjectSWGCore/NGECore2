import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('gronda')
	mobileTemplates.add('dwarf_gronda')
		
	core.spawnService.addLairTemplate('corellia_gronda_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_large.iff')
	return
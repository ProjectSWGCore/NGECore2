import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('piket_longhorn')
	mobileTemplates.add('piket_longhorn_female')
	
	core.spawnService.addLairTemplate('dantooine_piket_longhorn_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_large.iff')
	return
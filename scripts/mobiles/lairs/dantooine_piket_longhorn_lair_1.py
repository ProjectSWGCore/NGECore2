import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('piket_longhorn')
	mobileTemplates.add('piket_longhorn_female')
	mobileTemplates.add('piket_plains_walker')
	
	core.spawnService.addLairTemplate('dantooine_piket_longhorn_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/shared_earthmound_light.iff')
	return
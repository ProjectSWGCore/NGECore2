import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('ancient_graul')

	
	core.spawnService.addLairTemplate('dantooine_ancient_graul_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/cave_small_light.iff')
	return
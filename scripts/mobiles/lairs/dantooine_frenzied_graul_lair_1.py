import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('frenzied_graul')

	
	core.spawnService.addLairTemplate('dantooine_frenzied_graul_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/cave_small_dark.iff')
	return
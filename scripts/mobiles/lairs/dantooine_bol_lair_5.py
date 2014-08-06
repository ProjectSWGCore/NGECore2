import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('lesser_plains_bol')
	
	core.spawnService.addLairTemplate('dantooine_bol_lair_5', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_small.iff')
	return
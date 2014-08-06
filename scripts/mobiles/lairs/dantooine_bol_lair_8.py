import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('seething_bol_crusher')
	
	core.spawnService.addLairTemplate('dantooine_bol_lair_6', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_large.iff')
	return
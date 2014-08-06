import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('voritor_lizard') 
	
	core.spawnService.addLairTemplate('dantooine_voritor_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_thicket_small.iff')
	return
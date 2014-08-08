import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('horned_voritor_jungle_lizard') 
	mobileTemplates.add('spiked_slasher') 	
	
	core.spawnService.addLairTemplate('dantooine_voritor_jungle_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_leaf_small_fog_red.iff')
	return
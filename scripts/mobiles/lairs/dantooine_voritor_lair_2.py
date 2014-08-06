import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('voritor_lizard')
	mobileTemplates.add('horned_voritor_jungle_lizard') 
	mobileTemplates.add('vexed_voritor_lizard') 
	mobileTemplates.add('voritor_dasher') 
	mobileTemplates.add('spiked_slasher') 	
	
	core.spawnService.addLairTemplate('dantooine_voritor_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/shared_poi_all_lair_brambles_small.iff')
	return
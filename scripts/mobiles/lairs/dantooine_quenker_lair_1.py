import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('quenker')
	mobileTemplates.add('quenker_ravager')
	mobileTemplates.add('quenker_relic_reaper')
	
	core.spawnService.addLairTemplate('dantooine_quenker_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/shared_poi_all_lair_brambles_small.iff')
	return
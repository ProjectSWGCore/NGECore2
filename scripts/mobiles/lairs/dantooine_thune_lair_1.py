import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('thune')
	mobileTemplates.add('thune_grassland_guardian')
	mobileTemplates.add('thune_herd_leader')
	core.spawnService.addLairTemplate('dantooine_thune_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/shared_earthmound_light.iff')
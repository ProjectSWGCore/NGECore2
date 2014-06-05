import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','imp_recruiter')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('imperial_recruiter')
	
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:imperial', object.getPosition().x, object.getPosition().z, 46, 48, 0)
	return

import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','reb_recruiter')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('rebel_recruiter')
	
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:rebel', object.getPosition().x, object.getPosition().z, 45, 47, 0)
	
	return

import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','junk_dealer')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('junk_dealer_special')
	
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:junkshop', object.getPosition().x, object.getPosition().z, 26, 81, 0)
	return
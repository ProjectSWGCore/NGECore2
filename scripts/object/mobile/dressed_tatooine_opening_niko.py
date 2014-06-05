import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','niko')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('tatooine_opening_niko')
	return

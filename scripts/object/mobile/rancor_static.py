import sys
from resources.datatables import Options

def setup(core, object):
	#object.setAttachment('radial_filename', 'object/conversation');
	#object.setAttachment('conversationFile','junk_dealer')
	object.setOptionsBitmask(Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('jabbas_palace_rancor')
	return
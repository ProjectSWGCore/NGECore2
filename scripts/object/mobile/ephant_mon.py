import sys
from resources.datatables import Options

def setup(core, object):
	#object.setAttachment('radial_filename', 'object/conversation');
	#object.setAttachment('conversationFile','junk_dealer')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE | Options.QUEST)
	object.setStfFilename('mob/creature_names')
	object.setStfName('ephant_mon')
	return
	
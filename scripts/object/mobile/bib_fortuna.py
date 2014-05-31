import sys
from resources.datatables import Options

def setup(core, object):
	#object.setAttachment('radial_filename', 'object/conversation');
	#object.setAttachment('conversationFile','junk_dealer')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('bib_fortuna')
	return
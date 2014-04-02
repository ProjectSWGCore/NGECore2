import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'conversation')
	object.setAttachment('conversationFile', 'respec')
	object.setOptions(Options.CONVERSABLE, True)
	object.setOptions(Options.INVULNERABLE, True)
	return
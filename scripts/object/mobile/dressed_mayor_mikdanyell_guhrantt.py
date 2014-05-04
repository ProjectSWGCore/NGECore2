import sys
from resources.datatables import Options

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','mayor_mikdanyel_guhrantt')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	object.setStfFilename('mob/creature_names')
	object.setStfName('mos_eisley_mayor')
	return

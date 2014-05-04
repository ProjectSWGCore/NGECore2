from resources.datatables import Options
import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/conversation');
	object.setAttachment('conversationFile','missions/bounty_droid_probot')
	object.setOptionsBitmask(Options.CONVERSABLE | Options.INVULNERABLE)
	return
import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_guildhall_tatooine_style_02.iff')
	object.setStructureTemplate('object/tangible/deed/guild_deed/shared_tatooine_guild_style_02_deed.iff')
	object.setLotRequirement(5)
	object.setBMR(100)
	return

def use(core, actor, object):
	return
import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_house_generic_large_style_01.iff')
	object.setStructureTemplate('object/tangible/deed/player_house_deed/shared_generic_house_large_deed.iff')
	object.setLotRequirement(5)
	object.setBMR(26)
	return

def use(core, actor, object):
	return
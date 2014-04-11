import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_house_corellia_small_style_01.iff')
	object.setStructureTemplate('object/tangible/deed/player_house_deed/shared_generic_house_small_deed.iff')
	object.setLotRequirement(1)
	object.setBMR(15)
	return

def use(core, actor, object):
	return
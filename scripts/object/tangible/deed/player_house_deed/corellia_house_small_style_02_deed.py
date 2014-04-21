import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_house_corellia_small_style_02.iff')
	object.setStructureTemplate('object/tangible/deed/player_house_deed/shared_corellia_house_small_style_02_deed.iff')
	object.setLotRequirement(2)
	object.setBMR(8)
	return

def use(core, actor, object):
	return
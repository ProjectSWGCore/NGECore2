import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_player_house_corellia_medium_style_02.iff')
	object.setStructureTemplate('object/tangible/deed/player_house_deed/shared_corellia_house_medium_style_02_deed.iff')
	object.setLotRequirement(2)
	object.setBMR(18)
	return

def use(core, actor, object):
	return
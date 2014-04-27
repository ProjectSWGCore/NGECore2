import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_structure.iff')
	object.setStructureTemplate('object/tangible/deed/player_house_deed/shared_player_house_tcg_8_bespin_house_deed.iff')
	object.setLotRequirement(2)
	object.setBMR(8)
	return

def use(core, actor, object):
	return
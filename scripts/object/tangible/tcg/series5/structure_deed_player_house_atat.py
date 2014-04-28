import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_house_atat.iff')
	object.setStructureTemplate('object/tangible/tcg/series5/shared_structure_deed_player_house_atat.iff')
	object.setLotRequirement(3)
	object.setBMR(8)
	return

def use(core, actor, object):
	return
	
	
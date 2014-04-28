import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/shared_construction_structure.iff')
	object.setStructureTemplate('object/tangible/saga_system/rewards/shared_structure_deed_player_house_sandcrawler.iff')
	object.setLotRequirement(5)
	object.setBMR(26)
	return

def use(core, actor, object):
	return
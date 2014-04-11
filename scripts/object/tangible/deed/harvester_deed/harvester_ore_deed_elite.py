import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'harvesterDeed')
	object.setConstructorTemplate('object/installation/mining_ore/construction/shared_construction_mining_ore_harvester_style_heavy.iff')
	object.setStructureTemplate('object/installation/mining_ore/shared_mining_ore_harvester_elite.iff')
	object.setLotRequirement(3)
	object.setBMR(120)
	return
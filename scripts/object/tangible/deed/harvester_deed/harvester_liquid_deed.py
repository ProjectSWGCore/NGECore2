import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/harvesterDeed')
	object.setAttachment('ConstructorTemplate', 'object/installation/mining_ore/construction/shared_construction_mining_ore_harvester_style_1.iff')
	object.setAttachment('StructureTemplate', 'object/installation/mining_liquid/shared_mining_liquid_harvester_style_1.iff')
	object.setAttachment('LotRequirement', 1)
	object.setIntAttribute('examine_maintenance_rate', 16)
	return
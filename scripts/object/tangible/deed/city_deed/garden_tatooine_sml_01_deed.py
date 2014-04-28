import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/shared_construction_structure.iff')
	object.setStructureTemplate('object/tangible/deed/city_deed/shared_garden_tatooine_sml_01_deed.iff')
	#object.setLotRequirement(5)
	object.setBMR(5)
	return

def use(core, actor, object):
	return
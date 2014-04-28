import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/structureDeed')
	object.setConstructorTemplate('object/building/player/construction/shared_construction_player_jedi_meditation_room.iff')
	object.setStructureTemplate('object/tangible/tcg/series3/shared_structure_deed_jedi_meditation_room_deed.iff')
	object.setLotRequirement(3)
	object.setBMR(8)
	return

def use(core, actor, object):
	return
	
	
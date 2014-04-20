import sys


def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', -13.7, 3, 9.1, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', -10.5, 0.7, 4.5, 0.707108, -0.707108, 1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
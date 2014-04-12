import sys

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', -7.39, 2.36, 2, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', -5, 0.74, -1.81, 0.707107, -0.707107, 1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
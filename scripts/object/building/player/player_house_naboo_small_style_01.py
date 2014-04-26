import sys

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', -1.7, 3, -8.2, 0, 1, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', 0, 0.7, 6.2, 1, 0, 1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
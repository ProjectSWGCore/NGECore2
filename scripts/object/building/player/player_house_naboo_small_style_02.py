import sys

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', 8.4, 3, 7.2, 0.2, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', 0.4, 0.5, 6.2, 1, 0, 2)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
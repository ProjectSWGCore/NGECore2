import sys

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/shared_streetsign_upright_style_01.iff', 3.5, -0.3, 3.5, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', -3.4, 0.5, 0, 0.7, 0.7, 1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
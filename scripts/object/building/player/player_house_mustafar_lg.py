import sys

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', 1.5, 2.86, 13.0, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', -4.1, 0.55, 6.6, 0.714 ,0.714, 1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	
	elevatorterminal1 = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_elevator_down.iff', 0, 0.8, -0.655, -0.103, 0.998, 10)
	
	elevatorterminal2 = core.objectService.createChildobject(object, 'object/tangible/terminal/shared_terminal_elevator.iff', 0, -3, -0.655, 0.994, -0.103, 10)
	
	elevatorterminal3 = core.objectService.createChildobject(object, 'object/tangible/terminal/shared_terminal_elevator_up.iff', 0, -6, -0.655, 0.994, -0.103, 10)
	return
import sys
from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', Point3D(float(10.4), float(3), float(-11)), Quaternion(float(0), float(0), float(0), float(0)), -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	#structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', -8.5, 0.7, 8.5, 0.707108, -0.707108, 1)
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(8.6), float(0.5), float(2.5)), Quaternion(float(0), float(0), float(1), float(0)),1)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal').
	structureterminal.setAttachment('housing_parentstruct', object)
	return
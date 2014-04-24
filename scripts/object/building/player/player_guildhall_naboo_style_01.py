import sys
from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', 6,3, 18.4, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(18.9), float(2.5), float(10)), Quaternion(float(1), float(1), float(1), float(0)),7)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
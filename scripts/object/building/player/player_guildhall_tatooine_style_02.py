import sys
from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', 5.5,4, 12.7, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(4.0), float(2.2), float(9)), Quaternion(float(0), float(0), float(0), float(0)),1)
	#structureterminal.setAttachment('radial_filename', 'structure/structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	return
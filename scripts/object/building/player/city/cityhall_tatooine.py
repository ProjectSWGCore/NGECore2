import sys
from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(18.9), float(1.5), float(10)), Quaternion(float(1), float(1), float(1), float(0)),7)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	
	cityterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_city.iff', Point3D(float(0), float(2.6), float(0)), Quaternion(float(1), float(1), float(1), float(0)),3)
	#cityterminal.setAttachment('housing_parentstruct', object)
	
	return
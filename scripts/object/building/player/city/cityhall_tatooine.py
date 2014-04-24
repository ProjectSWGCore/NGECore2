import sys
from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
<<<<<<< HEAD
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(18.9), float(1.5), float(10)), Quaternion(float(1), float(1), float(1), float(0)),7)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	
	cityterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_city.iff', Point3D(float(0), float(2.6), float(0)), Quaternion(float(1), float(1), float(1), float(0)),3)
=======
	sign = core.objectService.createChildObject(object, 'object/tangible/sign/player/shared_house_address.iff', -13.7, 3, 9.1, -1, 0, -1)
	print(sign)
	object.setAttachment("structureSign", sign)
	
	structureterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_player_structure.iff', Point3D(float(-12.2), float(1.5), float(-4.2)), Quaternion(float(0), float(0), float(0), float(0)),4)
	#structureterminal.setAttachment('radial_filename', 'structure_management_terminal')
	structureterminal.setAttachment('housing_parentstruct', object)
	
	cityterminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_city.iff', Point3D(float(16), float(2), float(-9)), Quaternion(float(1), float(1), float(1), float(0)),5)
	#cityterminal.setAttachment('housing_parentstruct', object)
	
	city_vote_terminal = core.objectService.createChildObject(object, 'object/tangible/terminal/shared_terminal_city_vote.iff', Point3D(float(0), float(1.85), float(-8.5)), Quaternion(float(1), float(1), float(1), float(0)),3)
>>>>>>> upstream/master
	#cityterminal.setAttachment('housing_parentstruct', object)
	
	return
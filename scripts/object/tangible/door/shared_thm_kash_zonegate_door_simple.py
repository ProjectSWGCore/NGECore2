import sys

from engine.resources.scene import Point3D
from engine.resources.scene import Quaternion

def setup(core, object):
	object.setAttachment('radial_filename', 'a_gate_door')
	return

#TODO: Solve problem with displayed ObjectID from buildout not being proper unsigned int.
#-56576470324869753 rryatt
#-56576470324869754 blackscale
#-56576470324869755 ettyy
#kkowir exit coord -746 18 256

def use(core, actor, object):
	if object.getObjectID() == long(-56576470324869752):
		core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName('kashyyyk_dead_forest'), Point3D(float(86.9), float(29.4), float(-473)), Quaternion(float(0), float(0), float(0), float(1)), None)
		return
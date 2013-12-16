import sys

def TraderWeapon(core, object):
	pistol = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_cdef_noob.iff', object.getPlanet())
	object._add(pistol)
import sys

def ForceSensetiveWeapon(core, object):
	sword = core.objectService.createObject('object/weapon/melee/2h_sword/shared_2h_sword_katana.iff', object.getPlanet())
	object._add(sword)
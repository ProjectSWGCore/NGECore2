import sys

def SmugglerWeapon(core, object):
	pistol = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_cdef_noob.iff', object.getPlanet())
	pistol.setCustomName('Issued Pistol')
	pistol.setDamageType('@obj_attr_n:armor_eff_energy') 
	pistol.setStringAttribute('cat_wpn_damage.damage', '0-0')
	pistol.setMinDamage(10)
	pistol.setMaxDamage(26)
	pistol.setElementalDamage(0)
	pistol.setIntAttribute('cat_wpn_damage.weapon_dps', pistol.getDamagePerSecond())
	object._add(pistol)
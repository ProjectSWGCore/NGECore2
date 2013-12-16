import sys

def ForceSensetiveWeapon(core, object):
	sword = core.objectService.createObject('object/weapon/melee/polearm/shared_polearm_vibro_axe.iff', object.getPlanet())
	sword.setCustomName('Jedi Training Polearm')
	sword.setDamageType('@obj_attr_n:armor_eff_kinetic') 
	sword.setStringAttribute('cat_wpn_damage.damage', '0-0')
	sword.setMinDamage(43)
	sword.setMaxDamage(75)
	sword.setElementalDamage(0)
	sword.setIntAttribute('cat_wpn_damage.weapon_dps', sword.getDamagePerSecond())
	object._add(sword)
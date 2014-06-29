import sys

def exceptionalPistol(core, object, owner):
	object = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_dl44.iff', owner.getPlanet())
	object.setStringAttribute('required_skill', 'None')
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setAttackSpeed(0.4);
	object.setCustomName('Exceptional Rebel DL44 Pistol');
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(275);
	object.setMaxDamage(518);
	object.setWeaponType(2);
	inventory = owner.getSlottedObject('inventory')
	inventory.add(object)
	return
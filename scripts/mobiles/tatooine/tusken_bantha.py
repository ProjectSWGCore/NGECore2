import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('feeder_tusken_bantha')
	mobileTemplate.setLevel(30)
	mobileTemplate.setMinLevel(29)
	mobileTemplate.setMaxLevel(31)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Herbivore Meat")
	mobileTemplate.setMeatAmount(475)	
	mobileTemplate.setHideType("Wooly Hide")
	mobileTemplate.setHideAmount(350)
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setBoneAmount(375)
	mobileTemplate.setSocialGroup("tusken raider")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_bantha_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	attacks.add('bm_bite_2')
	attacks.add('bm_dampen_pain_2')
	attacks.add('bm_charge_2')
	attacks.add('bm_stomp_2')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('tusken_bantha', mobileTemplate)
	return
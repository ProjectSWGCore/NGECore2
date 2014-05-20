import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('swift_charging_bol')
	mobileTemplate.setLevel(39)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(0)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(300)	
	mobileTemplate.setBoneType("Mammal Bone")
	mobileTemplate.setHideAmount(180)
	mobileTemplate.setSocialGroup("bol")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)	

	templates = Vector()
	templates.add('object/mobile/shared_bol_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('swift_charging_bol', mobileTemplate)
	return
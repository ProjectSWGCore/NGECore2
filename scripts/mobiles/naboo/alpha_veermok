import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('alpha veermok')
	mobileTemplate.setLevel(19)
	mobileTemplate.setMinLevel(19)
	mobileTemplate.setMaxLevel(20)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1.2)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(150)
	mobileTemplate.setHideType("Bristley Hide")
	mobileTemplate.setHideAmount(150)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(40)
	mobileTemplate.setSocialGroup("veermok")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)

	templates = Vector()
	templates.add('object/mobile/shared_veermok_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('alpha_veermok', mobileTemplate)
	return
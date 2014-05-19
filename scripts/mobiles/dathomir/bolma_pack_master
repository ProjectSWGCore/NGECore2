import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('bolma pack master')
	mobileTemplate.setLevel(61)
	mobileTemplate.setMinLevel(61)
	mobileTemplate.setMaxLevel(61)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1.2)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(160)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(150)
	mobileTemplate.setSocialGroup("bolma")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)

	templates = Vector()
	templates.add('object/mobile/shared_bolma_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('bolma_pack_leader', mobileTemplate)
	return
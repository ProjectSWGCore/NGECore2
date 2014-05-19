import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('ancient tulrus')
	mobileTemplate.setLevel(82)
	mobileTemplate.setMinLevel(82)
	mobileTemplate.setMaxLevel(83)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1.2)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(33)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(24)
	mobileTemplate.setSocialGroup("tulrus")
	mobileTemplate.setAssistRange(24)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE)

	templates = Vector()
	templates.add('object/mobile/shared_bark_mite_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('ancient_tulrus', mobileTemplate)
	return
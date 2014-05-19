import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('carrion spat corpsereaver')
	mobileTemplate.setLevel(10)
	mobileTemplate.setMinLevel(10)
	mobileTemplate.setMaxLevel(10)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(260)
	mobileTemplate.setBoneType("Avian Bones")
	mobileTemplate.setBoneAmount(180)
	mobileTemplate.setSocialGroup("carrion spat")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE)

	templates = Vector()
	templates.add('object/mobile/shared_carrion_spat_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('carrion_spat_corpsereaver', mobileTemplate)
	return
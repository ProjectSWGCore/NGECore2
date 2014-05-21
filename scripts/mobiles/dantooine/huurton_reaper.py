import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('huurton_reaper')
	mobileTemplate.setLevel(65)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(25)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(10)	
	mobileTemplate.setBoneType("Mammal Bone")
	mobileTemplate.setHideAmount(25)
	mobileTemplate.setSocialGroup("huurton")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)	
	mobileTemplate.setOptionsBitmask(192)

	templates = Vector()
	templates.add('object/mobile/shared_huurton.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('huurton_reaper', mobileTemplate)
	return
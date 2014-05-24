import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('fearful_fynock_youth')
	mobileTemplate.setLevel(30)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(.5)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(3)
	mobileTemplate.setBoneType("Avian Bone")
	mobileTemplate.setHideAmount(3)
	mobileTemplate.setSocialGroup("fynock")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)	

	templates = Vector()
	templates.add('object/mobile/shared_fynock.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('fearful_fynock_youth', mobileTemplate)
	return
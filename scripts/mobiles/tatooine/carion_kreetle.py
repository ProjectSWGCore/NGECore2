import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('fragile_kreetle')
	mobileTemplate.setLevel(2)
	mobileTemplate.setMinLevel(2)
	mobileTemplate.setMaxLevel(7)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Insect Meat")
	mobileTemplate.setMeatAmount(1)
	mobileTemplate.setHideType("Scaley Hide")
	mobileTemplate.setBoneAmount(2)	
	mobileTemplate.setBoneType("")
	mobileTemplate.setHideAmount(0)
	mobileTemplate.setSocialGroup("kreetle")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)		
	
	templates = Vector()
	templates.add('object/mobile/shared_kreetle.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('carrion_kreetle', mobileTemplate)
	return
import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('elite_mountain_dewback')
	mobileTemplate.setLevel(22)
	mobileTemplate.setMinLevel(22)
	mobileTemplate.setMaxLevel(23)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(330)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(240)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(170)
	mobileTemplate.setSocialGroup("dewback mountain")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)	
	
	templates = Vector()
	templates.add('object/mobile/shared_dewback.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('enraged_mountain_dewback', mobileTemplate)
	return
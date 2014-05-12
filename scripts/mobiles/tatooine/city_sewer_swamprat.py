import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('city_sewer_swamprat')
	mobileTemplate.setLevel(3)
	mobileTemplate.setMinLevel(3)
	mobileTemplate.setMaxLevel(5)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(2)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(2)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(1)
	mobileTemplate.setSocialGroup("womprat")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)	

	
	
	templates = Vector()
	templates.add('object/mobile/shared_womp_rat.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('city_sewer_swamprat', mobileTemplate)
	return
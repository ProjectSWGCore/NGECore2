import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('malignant_squill')
	mobileTemplate.setLevel(50)
	mobileTemplate.setMinLevel(48)
	mobileTemplate.setMaxLevel(52)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(25)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(25)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(20)
	mobileTemplate.setSocialGroup("squill")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_squill.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('malignant_squill', mobileTemplate)
	return
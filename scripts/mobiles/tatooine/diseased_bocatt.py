import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('bocatt_diseased')
	mobileTemplate.setLevel(5)
	mobileTemplate.setMinLevel(5)
	mobileTemplate.setMaxLevel(6)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(6)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(5)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(4)
	mobileTemplate.setSocialGroup("bocatt")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(True)	
	
	templates = Vector()
	templates.add('object/mobile/shared_bocatt.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('diseased_bocatt', mobileTemplate)
	return
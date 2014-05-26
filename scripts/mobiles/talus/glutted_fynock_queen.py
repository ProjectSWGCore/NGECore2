import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('glutted_fynock_queen')
	mobileTemplate.setLevel(14)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1.2)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(30)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(25)
	mobileTemplate.setBoneType("Avian Bones")
	mobileTemplate.setBoneAmount(20)
	mobileTemplate.setSocialGroup("fynock")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_fynock_hue.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('glutted_fynock_queen', mobileTemplate)
	return
import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('bolle_bol_colt')
	mobileTemplate.setLevel(57)
	mobileTemplate.setMinLevel(57)
	mobileTemplate.setMaxLevel(57)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(.5)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(300)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(180)
	mobileTemplate.setSocialGroup("bolle bol")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_bolle_bol.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('bolle_bol_colt', mobileTemplate)
	return
import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('guf drolg')
	mobileTemplate.setLevel(42)
	mobileTemplate.setMinLevel(42)
	mobileTemplate.setMaxLevel(43)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptile Meat")
	mobileTemplate.setMeatAmount(350)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(275)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(200)
	mobileTemplate.setSocialGroup("guf drolg")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_guf_drolg_hue.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('guf_drolg', mobileTemplate)
	return
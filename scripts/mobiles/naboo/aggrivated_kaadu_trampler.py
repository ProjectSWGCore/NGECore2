import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('aggrivated_kaadu_trampler')
	mobileTemplate.setLevel(15)
	mobileTemplate.setMinLevel(15)
	mobileTemplate.setMaxLevel(17)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(120)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(85)
	mobileTemplate.setBoneType("Avian Bones")
	mobileTemplate.setBoneAmount(70)
	mobileTemplate.setSocialGroup("kaadu")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_kaadu.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('aggrivated_kaadu_trampler', mobileTemplate)
	return
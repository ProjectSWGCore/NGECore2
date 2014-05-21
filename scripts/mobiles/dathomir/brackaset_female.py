import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('brackaset_female')
	mobileTemplate.setLevel(64)
	mobileTemplate.setMinLevel(64)
	mobileTemplate.setMaxLevel(64)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(100)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(90)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(80)
	mobileTemplate.setSocialGroup("brackaset")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_brackaset_hue.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('brackaset_female', mobileTemplate)
	return
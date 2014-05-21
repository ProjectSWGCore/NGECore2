import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('krayt_dragon_grand')
	mobileTemplate.setLevel(87)
	mobileTemplate.setMinLevel(80)
	mobileTemplate.setMaxLevel(89)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(2.6)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(1700)
	mobileTemplate.setHideType("Bristly Hide")
	mobileTemplate.setBoneAmount(950)	
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setHideAmount(905)
	mobileTemplate.setSocialGroup("krayt dragon")
	mobileTemplate.setAssistRange(24)
	mobileTemplate.setStalker(True)	
	mobileTemplate.setOptionsBitmask(192)	
	
	templates = Vector()
	templates.add('object/mobile/shared_canyon_krayt_dragon.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('grand_krayt_dragon', mobileTemplate)
	return
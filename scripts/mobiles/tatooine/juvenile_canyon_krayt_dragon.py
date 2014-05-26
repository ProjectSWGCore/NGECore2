import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('juvenile_canyon_krayt')
	mobileTemplate.setLevel(82)
	mobileTemplate.setMinLevel(78)
	mobileTemplate.setMaxLevel(83)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setAttackRange(10)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(0.55)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(750)
	mobileTemplate.setHideType("Bristly Hide")
	mobileTemplate.setBoneAmount(500)	
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setHideAmount(410)
	mobileTemplate.setSocialGroup("krayt dragon")
	mobileTemplate.setAssistRange(24)
	mobileTemplate.setStalker(False)	
	mobileTemplate.setOptionsBitmask(192)	
	
	templates = Vector()
	templates.add('object/mobile/shared_krayt_dragon.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('juvenile_canyon_krayt_dragon', mobileTemplate)
	return
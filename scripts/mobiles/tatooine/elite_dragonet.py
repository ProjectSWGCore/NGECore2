import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('elite_dragonet')
	mobileTemplate.setLevel(24)
	mobileTemplate.setMinLevel(24)
	mobileTemplate.setMaxLevel(26)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(15)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(125)
	mobileTemplate.setHideType("Bristly Hide")
	mobileTemplate.setBoneAmount(75)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(68)
	mobileTemplate.setSocialGroup("dragonet")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(True)	
	
	templates = Vector()
	templates.add('object/mobile/shared_dragonet.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_large_toxicgreen.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('elite_dragonet', mobileTemplate)
	return
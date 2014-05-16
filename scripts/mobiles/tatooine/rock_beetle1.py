import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('rock_beetle')
	mobileTemplate.setLevel(19)
	mobileTemplate.setMinLevel(18)
	mobileTemplate.setMaxLevel(19)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Insect Meat")
	mobileTemplate.setMeatAmount(6)
	mobileTemplate.setHideType("Scaley Hide")
	mobileTemplate.setHideAmount(8)	
	mobileTemplate.setSocialGroup("rock beetle")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_rock_beetle.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_small_toxicgreen.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	attacks.add('bm_bite_2')
	attacks.add('bm_bolster_armor_2')
	attacks.add('bm_enfeeble_2')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rock_beetle_1', mobileTemplate)
	return
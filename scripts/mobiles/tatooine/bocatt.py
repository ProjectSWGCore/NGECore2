import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('bocatt')
	mobileTemplate.setLevel(17)
	mobileTemplate.setMinLevel(17)
	mobileTemplate.setMaxLevel(18)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(100)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(60)	
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setHideAmount(35)
	mobileTemplate.setSocialGroup("bocatt")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_bocatt.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_small_toxicgreen.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	attacks.add('bm_bite_1')
	attacks.add('bm_bolster_armor_1')
	attacks.add('bm_damage_disease_1')
	attacks.add('bm_enfeeble_1')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('bocatt', mobileTemplate)
	return
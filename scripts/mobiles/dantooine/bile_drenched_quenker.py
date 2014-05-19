import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('bile_drenched_quenker')
	mobileTemplate.setLevel(63)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Herbivore Meat")
	mobileTemplate.setMeatAmount(0)
	mobileTemplate.setHideType("Wooly Hide")
	mobileTemplate.setBoneAmount(0)	
	mobileTemplate.setBoneType("Mammal Bone")
	mobileTemplate.setHideAmount(0)
	mobileTemplate.setSocialGroup("quenker")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)	

	templates = Vector()
	templates.add('object/mobile/shared_quenker_relic_reaper.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('bile_drenched_quenker', mobileTemplate)
	return
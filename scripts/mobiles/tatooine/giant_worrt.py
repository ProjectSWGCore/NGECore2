import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('giant_worrt')
	mobileTemplate.setLevel(13)
	mobileTemplate.setMinLevel(13)
	mobileTemplate.setMaxLevel(15)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(9)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(9)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(4)
	mobileTemplate.setSocialGroup("worrt")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(False)	
	
	templates = Vector()
	templates.add('object/mobile/shared_giant_worrt.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	attacks.add('bm_bite_1')
	attacks.add('bm_damage_poison_1')
	attacks.add('bm_spit_1')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('giant_worrt', mobileTemplate)
	return
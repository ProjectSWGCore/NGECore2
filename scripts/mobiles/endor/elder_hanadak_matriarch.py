import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('elder hanadak matriarch')
	mobileTemplate.setLevel(61)
	mobileTemplate.setMinLevel(61)
	mobileTemplate.setMaxLevel(61)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1.2)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(35)
	mobileTemplate.setHideType("Bristley Hide")
	mobileTemplate.setHideAmount(35)
	mobileTemplate.setBoneType("Mammal Bones")
	mobileTemplate.setBoneAmount(35)
	mobileTemplate.setSocialGroup("hanadak")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_hanadak_hue.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('elder_hanadak_matriarch', mobileTemplate)
	return
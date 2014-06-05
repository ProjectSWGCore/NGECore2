import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dantari_chief')
	mobileTemplate.setLevel(64)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(24)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(0)
	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('dantari raiders')
	mobileTemplate.setAssistRange(1)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dantari_male.iff')
	
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dantari_chief', mobileTemplate)
	
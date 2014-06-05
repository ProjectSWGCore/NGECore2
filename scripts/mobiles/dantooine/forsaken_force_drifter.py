import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('eow_forsaken_force_drifter')
	mobileTemplate.setLevel(74)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(6)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setSocialGroup('force renegade')
	mobileTemplate.setRespawnTime(300)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_forsaken_force_drifter.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('forsaken_force_drifter', mobileTemplate)
	
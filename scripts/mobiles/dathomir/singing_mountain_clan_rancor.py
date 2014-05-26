import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('singing_mountain_clan_rancor')
	mobileTemplate.setLevel(80)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(4)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(6)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('mountain clan')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_rancor.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('singing_mountain_clan_rancor', mobileTemplate)
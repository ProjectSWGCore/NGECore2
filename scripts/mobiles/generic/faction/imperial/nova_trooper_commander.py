import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('stormtrooper_novatrooper_commander')
	mobileTemplate.setLevel(82)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(15)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("imperial")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("imperial")
	mobileTemplate.setFactionStatus(1)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_stormtrooper_black_black.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_blue.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_gold.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_green.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_grey.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_red.iff')	
	templates.add('object/mobile/shared_dressed_stormtrooper_black_white.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e11.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('novatrooper_commander', mobileTemplate)
	return
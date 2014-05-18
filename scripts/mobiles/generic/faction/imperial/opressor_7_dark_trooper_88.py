import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('fbase_elite_dark_trooper_extreme')
	mobileTemplate.setLevel(88)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(15)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1.1)
	mobileTemplate.setSocialGroup("imperial")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	#mobileTemplate.setOptionsBitmask(192)
	mobileTemplate.setOptionsBitmask(0) # TEMP until pvpstatus is useable for npcs!
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_trooper_black_hole.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e11.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('opp_7_88', mobileTemplate)
	return
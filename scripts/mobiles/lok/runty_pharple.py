import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('runty_pharple')
	mobileTemplate.setLevel(53)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(15)
	mobileTemplate.setHideType("Bristley Hide")
	mobileTemplate.setHideAmount(7)
	mobileTemplate.setBoneType("Avian Bone")
	mobileTemplate.setBoneAmount(5)
	mobileTemplate.setSocialGroup("pharple")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_pharple.iff')
	mobileTemplate.setTemplates(templates)
	
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	
	attacks = Vector()
	attacks.add('bm_claw_5')
	attacks.add('bm_flank_3')
	attacks.add('bm_preperation')
	attacks.add('bm_slash_5')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
		
	core.spawnService.addMobileTemplate('runty_pharple', mobileTemplate)
	return
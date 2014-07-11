import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('restuss_emperors_hand')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.BOSS)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_restuss_emperors_hand.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_mandalorian.iff', WeaponType.ONEHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('restuss_emperors_hand', mobileTemplate)
	return
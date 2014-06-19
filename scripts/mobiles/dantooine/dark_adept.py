import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dark_adept')
	mobileTemplate.setLevel(70)
	mobileTemplate.setMinLevel(60)
	mobileTemplate.setMaxLevel(70)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('dark jedi')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_male_human_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff', WeaponType.ONEHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff', WeaponType.TWOHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff', WeaponType.POLEARMSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dark_adept', mobileTemplate)
	
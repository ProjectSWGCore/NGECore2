import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('forage_goon')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_criminal_assassin_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_assassin_human_male_01.iff')
	templates.add('object/mobile/shared_twilek_male.iff')
	templates.add('object/mobile/shared_dressed_binayre_pirate_zabrak_male_01.iff')
	templates.add('object/mobile/shared_dressed_borvos_thug.iff')
	templates.add('object/mobile/shared_wookiee_male.iff')

	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_dh17.iff', WeaponType.CARBINE, 0.6, 15, 'kinetic')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_dl44.iff', WeaponType.PISTOL, 0.4, 15, 'kinetic')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_dlt20a.iff', WeaponType.RIFLE, 0.8, 24, 'kinetic')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('forage_goon', mobileTemplate)
	
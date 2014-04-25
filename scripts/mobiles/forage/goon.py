import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('forage_goon')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_criminal_assassin_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_assassin_human_male_01.iff')
	templates.add('object/mobile/shared_twilek_male.iff')
	templates.add('object/mobile/shared_dressed_binayre_pirate_zabrak_male_01.iff')
	templates.add('object/mobile/shared_dressed_borvos_thug.iff')
	templates.add('object/mobile/shared_wookiee_male.iff')

	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_dh17.iff', 1, 0.6)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_dl44.iff', 2, 0.4)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_dlt20a.iff', 0, 0.8)
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('forage_goon', mobileTemplate)
	
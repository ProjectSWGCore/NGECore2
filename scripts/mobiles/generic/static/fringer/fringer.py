import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('fringer')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setSocialGroup("mercenary")
	mobileTemplate.setOptionsBitmask(Options.INVULNERABLE)
		
	templates = Vector()
	templates.add('object/mobile/shared_dressed_commoner_tatooine_bith_male_05.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_bith_male_03.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_bith_female_05.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_bith_female_02.iff')
	
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('fringer', mobileTemplate)
	return
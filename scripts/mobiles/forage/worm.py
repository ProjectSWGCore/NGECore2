import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('forage_worm')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	
	templates = Vector()
	templates.add('object/mobile/shared_col_forage_aggravated_worm.iff')
	templates.add('object/mobile/shared_col_forage_angry_worm.iff')
	templates.add('object/mobile/shared_col_forage_brutal_worm.iff')
	templates.add('object/mobile/shared_col_forage_carnivorous_worm.iff')
	templates.add('object/mobile/shared_col_forage_ferocious_worm.iff')
	templates.add('object/mobile/shared_col_forage_fierce_worm.iff')
	templates.add('object/mobile/shared_col_forage_hungry_worm.iff')
	templates.add('object/mobile/shared_col_forage_oozing_worm.iff')
	templates.add('object/mobile/shared_col_forage_savage_worm.iff')
	templates.add('object/mobile/shared_col_forage_territorial_worm.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	#mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('forage_worm', mobileTemplate)
	
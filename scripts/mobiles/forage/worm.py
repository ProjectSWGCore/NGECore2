import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('forage_worm')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	
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
	
	core.spawnService.addMobileTemplate('forage_worm', mobileTemplate)
	
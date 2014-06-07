import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('stormtrooper')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_stormtrooper_m.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_e11.iff', WeaponType.RIFLE, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_t21.iff', WeaponType.RIFLE, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('stormtrooper', mobileTemplate)
	return
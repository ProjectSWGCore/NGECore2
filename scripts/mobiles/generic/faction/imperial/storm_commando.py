import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('storm_commando')
	mobileTemplate.setLevel(71)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_scout_trooper_black_black.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff', WeaponType.PISTOL, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_dx2.iff', WeaponType.PISTOL, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('storm_commando', mobileTemplate)
	
	return

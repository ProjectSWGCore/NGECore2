import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('ep3_general_grievous')
	mobileTemplate.setLevel(80)
	mobileTemplate.setDifficulty(Difficulty.BOSS)

	
	templates = Vector()
	templates.add('object/mobile/ep3/shared_general_grievous.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff', WeaponType.TWOHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('general_grievous', mobileTemplate)
	
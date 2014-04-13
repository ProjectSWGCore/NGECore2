import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('darth_vader')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setAttackRange(6)
	
	templates = Vector()
	templates.add('object/mobile/shared_darth_vader.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weaponTemplates.add('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff')
	mobileTemplate.setWeaponTemplates(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('darth_vader', mobileTemplate)
	
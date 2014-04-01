import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('stormtrooper')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(24)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_stormtrooper_m.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weaponTemplates.add('object/weapon/ranged/rifle/shared_rifle_e11.iff')
	weaponTemplates.add('object/weapon/ranged/rifle/shared_rifle_t21.iff')
	mobileTemplate.setWeaponTemplates(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('stormtrooper', mobileTemplate)
	
import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_axkva_min_static.iff')
	mobileTemplate.setCreatureName('axkva_min')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(99)
	attacks = Vector()
	#weaponTemplates.add('object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen4.iff')
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('axkva_min', mobileTemplate)
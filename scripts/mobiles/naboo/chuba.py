import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	attacks = Vector()
	add.attack('bm_bite_1')
	add.attack('bm_spit_1')
	add.attack('bm_damage_poison_1')
	mobileTemplates.setAttacks(attacks)
	templates.add('object/mobile/shared_chuba_hue.iff')
	mobileTemplate.setCreatureName('chuba')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(5)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('chuba', mobileTemplate)
	
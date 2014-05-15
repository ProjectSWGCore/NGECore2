import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_bite_2')
	attacks.add('bm_damage_poison_2')
	attacks.add('bm_defensive_2')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_hermit_spider.iff')
	mobileTemplate.setCreatureName('hermit spider')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(28)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('hermit_spider', mobileTemplate)
	
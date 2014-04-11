from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_dwarf_spider_droid.iff')
	mobileTemplate.setCreatureName('dwarf_spider_droid')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(50)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('dwarf_spider_droid', mobileTemplate)
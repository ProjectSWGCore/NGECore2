import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('junk_dealer')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setSocialGroup("township")
	mobileTemplate.setOptionsBitmask(Options.INVULNERABLE | Options.CONVERSABLE)
	mobileTemplate.setConversationFileName('junk_dealer')
		
	templates = Vector()
	templates.add('object/mobile/shared_junk_dealer_m_01.iff')
	templates.add('object/mobile/shared_junk_dealer_f_01.iff')
	templates.add('object/mobile/shared_junk_dealer_m_02.iff')
	templates.add('object/mobile/shared_junk_dealer_f_02.iff')
	templates.add('object/mobile/shared_junk_dealer_m_03.iff')
	templates.add('object/mobile/shared_junk_dealer_f_03.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('junkdealer', mobileTemplate)
	return
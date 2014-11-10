import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('outbreak_imperial_officer_gate_keeper')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setOptionsBitmask(Options.INVULNERABLE | Options.CONVERSABLE)
	mobileTemplate.setConversationFileName('outbreak_imperial_officer_gate_keeper')
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_imperial_officer_f.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m_2.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m_3.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m_4.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m_5.iff')
	templates.add('object/mobile/shared_dressed_imperial_officer_m_6.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 0, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleeHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('outbreak_imperial_officer_gate_keeper', mobileTemplate)
	return
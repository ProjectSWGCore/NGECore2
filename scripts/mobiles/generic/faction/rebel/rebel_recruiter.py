import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from resources.datatables import FactionStatus
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('rebel_recruiter')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setOptionsBitmask(Options.INVULNERABLE | Options.CONVERSABLE)
	mobileTemplate.setConversationFileName('reb_recruiter')
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_rebel_recruiter_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_recruiter_human_female_02.iff')
	templates.add('object/mobile/shared_dressed_rebel_recruiter_moncal_male_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_recruiter_twilek_female_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 0, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleeHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rebel_recruiter', mobileTemplate)
	return
import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('heroic_tusken_blood_hunter')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("heroic mos espa citizien")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(128)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_commando_trainer_trandoshan_male_01.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_trandoshan_female_01.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_t21.iff', WeaponType.RIFLE, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotrifle')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('heroic_tusken_blood_hunter', mobileTemplate)
	return
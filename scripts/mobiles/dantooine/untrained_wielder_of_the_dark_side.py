import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('untrained_wielder_of_the_dark_side')
	mobileTemplate.setMinLevel(61)
	mobileTemplate.setMaxLevel(70)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(6)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setSocialGroup('remnants of kun')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_untrained_wielder_of_the_darkside.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/shared_sword_01.iff', WeaponType.ONEHANDEDMELEE, 1.0, 5, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('untrained_wielder_of_the_dark_side', mobileTemplate)
	
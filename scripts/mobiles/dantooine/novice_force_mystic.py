import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('novice_force_mystic')
	mobileTemplate.setLevel(63)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(7)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setSocialGroup('force renegade')
	mobileTemplate.setRespawnTime(300)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_novice_force_mystic.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/shared_sword_01.iff', WeaponType.ONEHANDEDMELEE, 1.0, 5, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('novice_force_mystic', mobileTemplate)
	
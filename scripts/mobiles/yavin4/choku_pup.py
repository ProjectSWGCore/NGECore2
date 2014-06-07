import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('choku_pup')			
	mobileTemplate.setLevel(68)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(.5)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(7)
	mobileTemplate.setBoneType("Avian Bones")
	mobileTemplate.setBoneAmount(5)
	mobileTemplate.setSocialGroup("choku")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(128)
	
	templates = Vector()
	templates.add('object/mobile/shared_choku.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('choku_pup', mobileTemplate)
	return
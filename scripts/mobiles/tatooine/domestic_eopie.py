import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('domestic_eopie')
	mobileTemplate.setLevel(1)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Domesticated Meat")
	mobileTemplate.setMeatAmount(335)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(260)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(190)
	mobileTemplate.setSocialGroup("eopie")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)	
	
	templates = Vector()
	templates.add('object/mobile/shared_eopie.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('domestic_eopie', mobileTemplate)
	return
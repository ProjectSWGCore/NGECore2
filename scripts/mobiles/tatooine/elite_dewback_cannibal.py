import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('elite_dewback_canibal')
	mobileTemplate.setLevel(13)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(340)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(260)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(185)
	mobileTemplate.setSocialGroup("dewback cannibal")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(True)	
	
	templates = Vector()
	templates.add('object/mobile/shared_dewback.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('elite_dewback_cannibal', mobileTemplate)
	return
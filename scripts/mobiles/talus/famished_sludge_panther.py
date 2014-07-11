import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('famished_sludge_panther')
	mobileTemplate.setLevel(46)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(40)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(35)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(35)
	mobileTemplate.setSocialGroup("sludge panther")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(128)	

	templates = Vector()
	templates.add('object/mobile/shared_sludge_panther.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('famished_sludge_panther', mobileTemplate)
	return
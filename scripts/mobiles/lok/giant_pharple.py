import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('giant_pharple')
	mobileTemplate.setLevel(55)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Avian Meat")
	mobileTemplate.setMeatAmount(14)
	mobileTemplate.setBoneType("Bristley Hide")
	mobileTemplate.setBoneAmount(12)	
	mobileTemplate.setBoneType("Avian Bone")
	mobileTemplate.setBoneAmount(12)	
	mobileTemplate.setSocialGroup("self")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_giant_pharple.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	attacks.add('bm_bite_3')
	attacks.add('bm_disease_3')
	attacks.add('bm_enfeeble_3')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('giant_pharple', mobileTemplate)
	return
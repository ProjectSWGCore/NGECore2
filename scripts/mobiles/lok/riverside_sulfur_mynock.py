import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('riverside_sulfur_mynock')
	mobileTemplate.setLevel(39)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Herbivore Meat")
	mobileTemplate.setMeatAmount(60)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(78)
	mobileTemplate.setBoneType("Mammal Bone")
	mobileTemplate.setBoneAmount(30)
	mobileTemplate.setSocialGroup("mynock")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_riverside_sulfur_mynock.iff')
	mobileTemplate.setTemplates(templates)
	
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	
	attacks = Vector()
	attacks.add('bm_flank1')
	attacks.add('bm_wind_buffet_3')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
		
	core.spawnService.addMobileTemplate('riverside_sulfur_mynock', mobileTemplate)
	return
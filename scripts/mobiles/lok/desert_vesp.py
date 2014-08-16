import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('desert_vesp')
	
	mobileTemplate.setMinLevel(30)
	mobileTemplate.setMaxLevel(42)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian  Meat")
	mobileTemplate.setMeatAmount(10)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(5)
	#mobileTemplate.setBoneType("Avian Bone")
	#mobileTemplate.setBoneAmount(10)
	mobileTemplate.setSocialGroup("vesp")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_vesp.iff')
	mobileTemplate.setTemplates(templates)
	
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	
	attacks = Vector()
	attacks.add('bm_bite_3')
	attacks.add('bm_enfeeble_3')
	attacks.add('bm_bolster_armor_3')
	attacks.add('bm_damage_disease_3')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
		
	core.spawnService.addMobileTemplate('desert_vesp', mobileTemplate)
	return
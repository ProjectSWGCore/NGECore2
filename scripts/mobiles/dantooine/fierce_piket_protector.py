import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('fierce_piket_protector')
	mobileTemplate.setLevel(68)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Herbivore Meat")
	mobileTemplate.setMeatAmount(0)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(0)	
	mobileTemplate.setBoneType("Avian Bone")
	mobileTemplate.setHideAmount(0)
	mobileTemplate.setSocialGroup("piket")
	mobileTemplate.setAssistRange(1)
	mobileTemplate.setStalker(False)	

	templates = Vector()
	templates.add('object/mobile/shared_piket_longhorn.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	attacks.add('bm_bite_4')
	attacks.add('bm_bolster_armor_5')
	attacks.add('bm_charge_4')
	attacks.add('bm_shaken_2')
	attacks.add('bm_stomp_4')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('fierce_piket_protector', mobileTemplate)
	return
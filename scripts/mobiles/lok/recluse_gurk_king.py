import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('recluse_gurk_king')
	mobileTemplate.setLevel(39)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	#mobileTemplate.setMeatType("Herbivore  Meat ")
	#mobileTemplate.setMeatAmount(350)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(275)
	mobileTemplate.setBoneType("Mammal Bone")
	mobileTemplate.setBoneAmount(300)
	mobileTemplate.setSocialGroup("gurk")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_gurk.iff')
	mobileTemplate.setTemplates(templates)
	
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	
	attacks = Vector()
	attacks.add('bm_shaken_1')
	attacks.add('bm_stomp_3')
	attacks.add('bm_dampen_pain_3')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('recluse_gurk_king', mobileTemplate)
	return
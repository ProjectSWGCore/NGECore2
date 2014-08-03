import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('screaming_kai_tok')
	mobileTemplate.setLevel(47)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(25)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(41)
	mobileTemplate.setBoneType("Avian Bones")
	mobileTemplate.setBoneAmount(46)
	mobileTemplate.setSocialGroup("kai tok")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_kai_tok.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	attacks.add('bm_claw_3')
	attacks.add('bm_damage_poison_3')
	attacks.add('bm_slash_3')
	attacks.add('bm_wing_buffet_3')
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('screaming_kai_tok', mobileTemplate)
	return
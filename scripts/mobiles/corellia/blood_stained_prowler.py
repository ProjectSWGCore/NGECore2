import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('wrix_bloodstained_prowler')
	mobileTemplate.setLevel(21)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(70)
	mobileTemplate.setHideType("Bristley Hide")
	mobileTemplate.setHideAmount(40)
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setBoneAmount(35)
	mobileTemplate.setSocialGroup("wrix")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_wrix.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	attacks.add('bm_claw_4')
	attacks.add('bm_flank_2')
	attacks.add('bm_slash_4')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)

	core.spawnService.addMobileTemplate('blood_stained_prowler', mobileTemplate)
	return
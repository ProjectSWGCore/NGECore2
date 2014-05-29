import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('alkhara_bandit')
	mobileTemplate.setLevel(15)
	mobileTemplate.setMinLevel(14)
	mobileTemplate.setMaxLevel(16)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(0)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("alkhara")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(True)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_tatooine_alkhara_bandit.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_cdef.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotrifle')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('alkhara_bandit', mobileTemplate)
	return
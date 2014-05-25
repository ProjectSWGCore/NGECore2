import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('chunker_goon')
	mobileTemplate.setLevel(4)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(15)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("chunker gang")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)

	templates = Vector()
	templates.add('object/mobile/shared_dressed_talus_ttlp_soldier_hum_m_01.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_d18.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)

	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('chunker_goon', mobileTemplate)
	return
import sys
 
def CreateStarterWeapon(core, object, Profession):
	profession = object.getSlottedObject('ghost').getProfession()
	if profession == 'force_sensitive_1a':
				core.scriptService.callScript("scripts/starter_items/weapons/", "ForceSensetiveWeapon", "force_sensetive_1a", core, object)
	elif profession == 'smuggler_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "SmugglerWeapon", "smuggler_1a", core, object)
	elif profession == 'officer_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "OfficerWeapon", "officer_1a", core, object)
	elif profession == 'spy_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "SpyWeapon", "spy_1a", core, object)
	elif profession == 'medic_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "MedicWeapon", "medic_1a", core, object)
	elif profession == 'trader_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "TraderWeapon", "trader_1a", core, object)
	elif profession == 'commando_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "CommandoWeapon", "commando_1a", core, object)
	elif profession == 'bounty_hunter_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "BountyHunterWeapon", "bounty_hunter_1a", core, object)
	elif profession == 'entertainer_1a':
			core.scriptService.callScript("scripts/starter_items/weapons/", "EntertainerWeapon", "entertainer_1a", core, object)






	
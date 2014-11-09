#This file is to only be edited by Project SWG Staff.
#Do not commit any changes to this file unless authorized.


from resources.common import RadialOptions
from resources.common import OutOfBand
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.lang import System
from java.lang import Long
from java.util import Map
from java.util import TreeMap
from resources.datatables import WeaponType
from engine.resources.scene import Point3D
import main.NGECore
import sys
import math

core = main.NGECore.getInstance()

def screenOne (core, owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to the Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, screenOneCallback)
	window.addListBoxMenuItem('Character', 0)
	window.addListBoxMenuItem('Items', 1)
	window.addListBoxMenuItem('Resources', 2)
	if core.adminService.getAccessLevelFromDB(owner.getClient().getAccountId()) is not None:
		window.addListBoxMenuItem('Admin', 3)
	core.suiService.openSUIWindow(window);
		
def screenOneCallback(owner, window, eventType, returnList):
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		characterHandler(owner)
		return
	if returnList.get(0)=='1':
		itemsHandler(owner)
		return
	if returnList.get(0)=='2':
		resourceHandler(owner)
		return
	if returnList.get(0)=='3':
		adminHandler(owner)
		return
	return
	
def	characterHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, characterHandlerCallback)
	window.addListBoxMenuItem('Set Combat Level To 90', 0)
	window.addListBoxMenuItem('Give 50,000 Credits', 1)
	window.addListBoxMenuItem('Give Medic Buffs', 2)
	window.addListBoxMenuItem('Reset Combat Level To 1', 3)
	core.suiService.openSUIWindow(window);
	
def characterHandlerCallback(owner, window, eventType, returnList):
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		combatLevel(owner)
		return
	if returnList.get(0)=='1':
		credits(owner)
		return
	if returnList.get(0)=='2':
		buff(owner)
		return
	if returnList.get(0)=='3':
		combatLevelDown(owner)
		return
	return
	
def	itemsHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, itemsHandlerCallback)
	window.addListBoxMenuItem('Weapons', 0)
	window.addListBoxMenuItem('Armor', 1)
	window.addListBoxMenuItem('Misc Equipment', 2)
	core.suiService.openSUIWindow(window);
	
def itemsHandlerCallback(owner, window, eventType, returnList):
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		weaponsHandler(owner)
		return
	if returnList.get(0)=='1':
		armorHandler(owner)
		return
	if returnList.get(0)=='2':
		miscHandler(owner)
		return
	return
	
def	resourceHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, resourceHandlerCallback)
	window.addListBoxMenuItem('Survey Devices', 0)
	core.suiService.openSUIWindow(window);
	
def resourceHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		surveyDevices(owner, inventory)
		return
		
	
def	adminHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, adminHandlerCallback)
	window.addListBoxMenuItem('Vehicle', 0)
	window.addListBoxMenuItem('Crafting', 1)
	window.addListBoxMenuItem('Frog + Travel Terminal', 2)
	core.suiService.openSUIWindow(window);
	
def	adminHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		vehicleHandler(owner, inventory)
		return
		
	if returnList.get(0)=='2':
		cbtHandler(owner, inventory)
		return
	return
	
def	weaponsHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, weaponsHandlerCallback)
	window.addListBoxMenuItem('Melee', 0)
	window.addListBoxMenuItem('Ranged', 1)
	window.addListBoxMenuItem('Lightsabers', 2)
	window.addListBoxMenuItem('Krayt Pearls', 3)
	window.addListBoxMenuItem('Color Crystals', 4)
	core.suiService.openSUIWindow(window);
	
def weaponsHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		meleeWeapons(owner, inventory)
		return
	if returnList.get(0)=='1':
		rangedWeapons(owner, inventory)
		return
	if returnList.get(0)=='2':
		lightsabers(owner, inventory)
		return
	if returnList.get(0)=='3':
		lightsaberPowerCrystals(owner, inventory)
		return
	if returnList.get(0)=='4':
		lightsaberColorCrystals(owner, inventory)
		return	
	
	return
	
def	armorHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, armorHandlerCallback)
	window.addListBoxMenuItem('Shock Trooper', 0)
	window.addListBoxMenuItem('Bone', 1)
	window.addListBoxMenuItem('Bounty Hunter', 2)
	window.addListBoxMenuItem('Chitin', 3)
	window.addListBoxMenuItem('Composite', 4)
	window.addListBoxMenuItem('Clone Trooper', 5)
	window.addListBoxMenuItem('Death Trooper', 6)
	window.addListBoxMenuItem('Galactic Marine', 7)
	window.addListBoxMenuItem('Infiltrator', 8)
	window.addListBoxMenuItem('Ithorian Defender', 9)
	window.addListBoxMenuItem('Kashyyykian Black Mountain', 10)
	window.addListBoxMenuItem('Kashyyykian Ceremonial', 11)
	window.addListBoxMenuItem('Kashyyykian Hunting', 12)
	window.addListBoxMenuItem('Mandalorian', 13)
	window.addListBoxMenuItem('Mandalorian Imperial', 14)
	window.addListBoxMenuItem('Mandalorian Rebel', 15)
	window.addListBoxMenuItem('Marauder Assualt', 16)
	window.addListBoxMenuItem('Marauder Battle', 17)
	window.addListBoxMenuItem('Marauder Recon', 18)
	window.addListBoxMenuItem('Padded', 19)
	window.addListBoxMenuItem('RIS', 20)
	window.addListBoxMenuItem('Rebel snow', 21)
	window.addListBoxMenuItem('Stormtrooper', 22)
	window.addListBoxMenuItem('Scout Trooper', 23)
	window.addListBoxMenuItem('Snow Trooper', 24)
	window.addListBoxMenuItem('Tantel', 25)
	window.addListBoxMenuItem('Ubese', 26)
	window.addListBoxMenuItem('Jedi Master Cloak', 27)
	window.addListBoxMenuItem('Jedi Knight Robes', 28)
	core.suiService.openSUIWindow(window);
	
def armorHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		shocktrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='1':
		boneArmor(owner, inventory)
		return	
	if returnList.get(0)=='2':
		bountyhunterArmor(owner, inventory)
		return
	if returnList.get(0)=='3':
		chitinArmor(owner, inventory)
		return
	if returnList.get(0)=='4':
		compositeArmor(owner, inventory)
		return
	if returnList.get(0)=='5':
		clonetrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='6':
		deathtrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='7':
		rebelmarineArmor(owner, inventory)
		return
	if returnList.get(0)=='8':
		infiltratorArmor(owner, inventory)
		return
	if returnList.get(0)=='9':
		ithoriandefenderArmor(owner, inventory)
		return
	if returnList.get(0)=='10':
		kashyyykianblackmountainArmor(owner, inventory)
		return
	if returnList.get(0)=='11':
		kashyyykianceremonialArmor(owner, inventory)
		return
	if returnList.get(0)=='12':
		kashyyykianhuntingArmor(owner, inventory)
		return
	if returnList.get(0)=='13':
		mandalorianArmor(owner, inventory)
		return
	if returnList.get(0)=='14':
		mandalorianimperialArmor(owner, inventory)
		return
	if returnList.get(0)=='15':
		mandalorianrebelArmor(owner, inventory)
		return
	if returnList.get(0)=='16':
		marauderassaultArmor(owner, inventory)
		return
	if returnList.get(0)=='17':
		marauderbattleArmor(owner, inventory)
		return
	if returnList.get(0)=='18':
		marauderreconArmor(owner, inventory)
		return
	if returnList.get(0)=='19':
		PaddedArmor(owner, inventory)
		return
	if returnList.get(0)=='20':
		risArmor(owner, inventory)
		return
	if returnList.get(0)=='21':
		rebelsnowArmor(owner, inventory)
		return
	if returnList.get(0)=='22':
		stormtrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='23':
		scouttrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='24':
		snowtrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='25':
		tantelArmor(owner, inventory)
		return
	if returnList.get(0)=='26':
		ubeseArmor(owner, inventory)
		return
	if returnList.get(0)=='27':
		jediMasterCloak(owner, inventory)
		return
	if returnList.get(0)=='28':
		jediKnightRobes(owner, inventory)
		return
		
def	miscHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, miscHandlerCallback)
	window.addListBoxMenuItem('Backpack', 0)
	window.addListBoxMenuItem('Heroism Jewelry Set', 1)	
	window.addListBoxMenuItem('Belt of Master Bodo Baas', 2)
	window.addListBoxMenuItem('Starter Equipment Box', 3)
	window.addListBoxMenuItem('GCW Banners', 4)
	window.addListBoxMenuItem('Bounty Droids', 5)
	
	core.suiService.openSUIWindow(window);
	
def miscHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		backpack(owner, inventory)
		return
	if returnList.get(0)=='1':
		heroismJewelry(owner, inventory)
		return
	if returnList.get(0)=='2':
		jediBelt(owner, inventory)
		return
	if returnList.get(0)=='3':
		starterEquipmentBox(owner, inventory)
		return
	if returnList.get(0)=='4':
		GCWBanners(owner, inventory)
		return
	if returnList.get(0)=='5':
		bountyDroids(owner, inventory)
		return
		
	return
	
def combatLevel(owner):
	if (owner.getLevel() < 90):
		core.playerService.grantLevel(owner, 90);
		screenOne(core, owner)
	return
	
def credits(owner):
	owner.setCashCredits(owner.getCashCredits() + 50000);
	owner.sendSystemMessage('The Project SWG team has pitched in and given you ' + "50,000" + ' credits for your service.', 0)
	screenOne(core, owner)
	return
	
def buff(owner):
	core.buffService.addBuffToCreature(owner, 'me_buff_health_2', owner)
	buff = owner.getBuffByName('me_buff_health_2')
	buff.setDuration(10800)
	owner.updateBuff(buff)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_action_3', owner)
	buff1 = owner.getBuffByName('me_buff_action_3')
	buff1.setDuration(10800)
	owner.updateBuff(buff1)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_agility_3', owner)
	buff2 = owner.getBuffByName('me_buff_agility_3')
	buff2.setDuration(10800)
	owner.updateBuff(buff2)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_melee_gb_1', owner)
	buff3 = owner.getBuffByName('me_buff_melee_gb_1')
	buff3.setDuration(10800)
	owner.updateBuff(buff3)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_ranged_gb_1', owner)
	buff4 = owner.getBuffByName('me_buff_ranged_gb_1')
	buff4.setDuration(10800)
	owner.updateBuff(buff4)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_precision_3', owner)
	buff5 = owner.getBuffByName('me_buff_precision_3')
	buff5.setDuration(10800)
	owner.updateBuff(buff5)
	
	core.buffService.addBuffToCreature(owner, 'me_buff_strength_3', owner)
	buff6 = owner.getBuffByName('me_buff_strength_3')
	buff6.setDuration(10800)
	owner.updateBuff(buff6)
	screenOne(core, owner)
	return

def combatLevelDown(owner):
	if (owner.getLevel() == 1):
		screenOne(core, owner)
		return
	if (owner.getLevel() > 1):
		core.playerService.grantLevel(owner, 1)
		screenOne(core, owner)
	return
	
def rangedWeapons(owner, inventory):
	rifle = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_a280.iff', owner.getPlanet())
	rifle.setDamageType('energy')
	rifle.setWeaponType(WeaponType.RIFLE)
	rifle.setAttackSpeed(0.8)
	rifle.setMinDamage(518)
	rifle.setMaxDamage(1035)
	rifle.setMaxRange(64)
	
	pistol = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_intimidator.iff', owner.getPlanet())
	pistol.setDamageType('energy')
	pistol.setWeaponType(WeaponType.PISTOL)
	pistol.setAttackSpeed(0.4)
	pistol.setMinDamage(259)
	pistol.setMaxDamage(518)
	pistol.setMaxRange(35)
	
	carbine = core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_proton.iff', owner.getPlanet())
	carbine.setDamageType('energy')
	carbine.setWeaponType(WeaponType.CARBINE)
	carbine.setAttackSpeed(0.6)
	carbine.setMinDamage(389)
	carbine.setMaxDamage(780)
	carbine.setMaxRange(50)
	
	heavy = core.objectService.createObject('object/weapon/ranged/heavy/shared_heavy_pvp.iff', owner.getPlanet())
	heavy.setDamageType('energy')
	heavy.setWeaponType(WeaponType.HEAVYWEAPON)
	heavy.setAttackSpeed(1)
	heavy.setMinDamage(850)
	heavy.setMaxDamage(1350)
	heavy.setStringAttribute('wpn_elemental_type', 'Heat')
	heavy.setStringAttribute('wpn_elemental_value', '50')
	heavy.setMaxRange(64)
	
	inventory.add(rifle)
	inventory.add(pistol)
	inventory.add(carbine)
	inventory.add(heavy)
	screenOne(core, owner)
	return
	
def meleeWeapons(owner, inventory):
	polearm = core.objectService.createObject('object/weapon/melee/polearm/shared_polearm_vibro_axe.iff', owner.getPlanet())
	polearm.setAttackSpeed(1.0)
	polearm.setMaxRange(5)
	polearm.setDamageType('kinetic')
	polearm.setMinDamage(700)
	polearm.setMaxDamage(990)
	polearm.setWeaponType(WeaponType.POLEARMMELEE)
	
	lance = core.objectService.createObject('object/weapon/melee/polearm/shared_lance_staff_magna_guard.iff', owner.getPlanet())
	lance.setAttackSpeed(1.0)
	lance.setMaxRange(5)
	lance.setDamageType('kinetic')
	lance.setMinDamage(700)
	lance.setMaxDamage(990)
	lance.setWeaponType(WeaponType.POLEARMMELEE)
	
	inventory.add(lance)
	inventory.add(polearm)
	screenOne(core, owner)
	return
	
	
def shocktrooperArmor(owner, inventory):
	assaulttrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_bicep_r.iff", owner.getPlanet())
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	assaulttrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_bicep_l.iff", owner.getPlanet())
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	assaulttrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_bracer_r.iff", owner.getPlanet())
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	assaulttrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_bracer_l.iff", owner.getPlanet())
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	assaulttrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_leggings.iff", owner.getPlanet())
	assaulttrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	assaulttrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_helmet.iff", owner.getPlanet())
	assaulttrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
					
	assaulttrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_chest_plate.iff", owner.getPlanet())
	assaulttrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	assaulttrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	assaulttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	assaulttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	assaulttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	assaulttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	assaulttrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_boots.iff", owner.getPlanet())
	assaulttrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/assault_trooper/shared_armor_assault_trooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(assaulttrooper_bicep_r);
	inventory.add(assaulttrooper_bicep_l);
	inventory.add(assaulttrooper_bracer_r);
	inventory.add(assaulttrooper_bracer_l);
	inventory.add(assaulttrooper_leggings);
	inventory.add(assaulttrooper_helmet);
	inventory.add(assaulttrooper_chest);
	inventory.add(assaulttrooper_boots);
	inventory.add(assaulttrooper_gloves);
	screenOne(core, owner)
	return
	
	
def boneArmor(owner, inventory):
	bone_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bicep_r.iff", owner.getPlanet())
	bone_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bone_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bicep_l.iff", owner.getPlanet())
	bone_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bone_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bracer_r.iff", owner.getPlanet())
	bone_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bone_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bracer_l.iff", owner.getPlanet())
	bone_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bone_leggings = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_leggings.iff", owner.getPlanet())
	bone_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bone_helmet = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_helmet.iff", owner.getPlanet())
	bone_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
					
	bone_chest = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_chest_plate.iff", owner.getPlanet())
	bone_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	bone_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	bone_boots = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_boots.iff", owner.getPlanet())
	bone_gloves = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_gloves.iff", owner.getPlanet())	
											
	inventory.add(bone_bicep_r);
	inventory.add(bone_bicep_l);
	inventory.add(bone_bracer_r);
	inventory.add(bone_bracer_l);
	inventory.add(bone_leggings);
	inventory.add(bone_helmet);
	inventory.add(bone_chest);
	inventory.add(bone_boots);
	inventory.add(bone_gloves);
	screenOne(core, owner)
	return
	
def bountyhunterArmor(owner, inventory):
	bountyhunter_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_bicep_r.iff", owner.getPlanet())
	bountyhunter_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bountyhunter_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_bicep_l.iff", owner.getPlanet())
	bountyhunter_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bountyhunter_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_bracer_r.iff", owner.getPlanet())
	bountyhunter_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bountyhunter_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_bracer_l.iff", owner.getPlanet())
	bountyhunter_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bountyhunter_leggings = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_leggings.iff", owner.getPlanet())
	bountyhunter_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	bountyhunter_helmet = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_helmet.iff", owner.getPlanet())
	bountyhunter_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
					
	bountyhunter_chest = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_chest_plate.iff", owner.getPlanet())
	bountyhunter_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	bountyhunter_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	bountyhunter_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	bountyhunter_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	bountyhunter_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	bountyhunter_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	bountyhunter_boots = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_boots.iff", owner.getPlanet())
	bountyhunter_gloves = core.objectService.createObject("object/tangible/wearables/armor/bounty_hunter/shared_armor_bounty_hunter_crafted_gloves.iff", owner.getPlanet())	
											
	inventory.add(bountyhunter_bicep_r);
	inventory.add(bountyhunter_bicep_l);
	inventory.add(bountyhunter_bracer_r);
	inventory.add(bountyhunter_bracer_l);
	inventory.add(bountyhunter_leggings);
	inventory.add(bountyhunter_helmet);
	inventory.add(bountyhunter_chest);
	inventory.add(bountyhunter_boots);
	inventory.add(bountyhunter_gloves);
	screenOne(core, owner)
	return
	

def chitinArmor(owner, inventory):
	chitin_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_bicep_r.iff", owner.getPlanet())
	chitin_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_bicep_l.iff", owner.getPlanet())
	chitin_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_bracer_r.iff", owner.getPlanet())
	chitin_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_bracer_l.iff", owner.getPlanet())
	chitin_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_leggings = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_leggings.iff", owner.getPlanet())
	chitin_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_helmet = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_helmet.iff", owner.getPlanet())
	chitin_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	chitin_chest = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_chest_plate.iff", owner.getPlanet())
	chitin_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	chitin_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	chitin_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	chitin_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	chitin_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	chitin_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	chitin_boots = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_boots.iff", owner.getPlanet())
	chitin_gloves = core.objectService.createObject("object/tangible/wearables/armor/chitin/shared_armor_chitin_s01_gloves.iff", owner.getPlanet())											
	
	inventory.add(chitin_bicep_r);
	inventory.add(chitin_bicep_l);
	inventory.add(chitin_bracer_r);
	inventory.add(chitin_bracer_l);
	inventory.add(chitin_leggings);
	inventory.add(chitin_helmet);
	inventory.add(chitin_chest);
	inventory.add(chitin_boots);
	inventory.add(chitin_gloves);
	screenOne(core, owner)
	return
	
		
def compositeArmor(owner, inventory):
	comp_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", owner.getPlanet())
	comp_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_l.iff", owner.getPlanet())
	comp_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_r.iff", owner.getPlanet())
	comp_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_l.iff", owner.getPlanet())
	comp_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_leggings = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_leggings.iff", owner.getPlanet())
	comp_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_helmet = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_helmet.iff", owner.getPlanet())
	comp_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	comp_chest = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_chest_plate.iff", owner.getPlanet())
	comp_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	comp_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	comp_boots = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_boots.iff", owner.getPlanet())
	comp_gloves = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_gloves.iff", owner.getPlanet())											
	
	inventory.add(comp_bicep_r);
	inventory.add(comp_bicep_l);
	inventory.add(comp_bracer_r);
	inventory.add(comp_bracer_l);
	inventory.add(comp_leggings);
	inventory.add(comp_helmet);
	inventory.add(comp_chest);
	inventory.add(comp_boots);
	inventory.add(comp_gloves);
	screenOne(core, owner)
	return


def clonetrooperArmor(owner, inventory):
	clonetrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bicep_r.iff", owner.getPlanet())
	clonetrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bicep_l.iff", owner.getPlanet())
	clonetrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bracer_l.iff", owner.getPlanet())
	clonetrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_leggings.iff", owner.getPlanet())
	clonetrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_helmet.iff", owner.getPlanet())
	clonetrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_chest_plate.iff", owner.getPlanet())
	clonetrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_boots.iff", owner.getPlanet())
	clonetrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_gloves.iff", owner.getPlanet())	
											
	inventory.add(clonetrooper_bicep_r);
	inventory.add(clonetrooper_bicep_l);
	inventory.add(clonetrooper_bracer_r);
	inventory.add(clonetrooper_bracer_l);
	inventory.add(clonetrooper_leggings);
	inventory.add(clonetrooper_helmet);
	inventory.add(clonetrooper_chest);
	inventory.add(clonetrooper_boots);
	inventory.add(clonetrooper_gloves);
	screenOne(core, owner)
	return
	
	
def deathtrooperArmor(owner, inventory):
	deathtrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_bicep_r.iff", owner.getPlanet())
	deathtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_bicep_l.iff", owner.getPlanet())
	deathtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_bracer_r.iff", owner.getPlanet())
	deathtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_bracer_l.iff", owner.getPlanet())
	deathtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_leggings.iff", owner.getPlanet())
	deathtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_helmet.iff", owner.getPlanet())
	deathtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	deathtrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_chest_plate.iff", owner.getPlanet())
	deathtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
	deathtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
	deathtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	deathtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	deathtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	deathtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	deathtrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_boots.iff", owner.getPlanet())
	deathtrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/deathtrooper/shared_armor_deathtrooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(deathtrooper_bicep_r);
	inventory.add(deathtrooper_bicep_l);
	inventory.add(deathtrooper_bracer_r);
	inventory.add(deathtrooper_bracer_l);
	inventory.add(deathtrooper_leggings);
	inventory.add(deathtrooper_helmet);
	inventory.add(deathtrooper_chest);
	inventory.add(deathtrooper_boots);
	inventory.add(deathtrooper_gloves);
	screenOne(core, owner)
	return
	
		
def rebelmarineArmor(owner, inventory):
	marine_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_bicep_r.iff", owner.getPlanet())
	marine_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_bicep_l.iff", owner.getPlanet())
	marine_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_bracer_r.iff", owner.getPlanet())
	marine_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_bracer_l.iff", owner.getPlanet())
	marine_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_leggings = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_leggings.iff", owner.getPlanet())
	marine_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_helmet = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_helmet.iff", owner.getPlanet())
	marine_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	marine_chest = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_chest_plate.iff", owner.getPlanet())
	marine_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	marine_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	marine_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	marine_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	marine_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	marine_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	marine_boots = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_boots.iff", owner.getPlanet())
	marine_gloves = core.objectService.createObject("object/tangible/wearables/armor/marine/shared_armor_marine_gloves.iff", owner.getPlanet())	
											
	inventory.add(marine_bicep_r);
	inventory.add(marine_bicep_l);
	inventory.add(marine_bracer_r);
	inventory.add(marine_bracer_l);
	inventory.add(marine_leggings);
	inventory.add(marine_helmet);
	inventory.add(marine_chest);
	inventory.add(marine_boots);
	inventory.add(marine_gloves);
	screenOne(core, owner)
	return
	
	
def infiltratorArmor(owner, inventory):
	infiltrator_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_bicep_r.iff", owner.getPlanet())
	infiltrator_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_bicep_l.iff", owner.getPlanet())
	infiltrator_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_bracer_r.iff", owner.getPlanet())
	infiltrator_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_bracer_l.iff", owner.getPlanet())
	infiltrator_leggings = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_leggings.iff", owner.getPlanet())
	infiltrator_helmet = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_helmet.iff", owner.getPlanet())
	infiltrator_chest = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_chest_plate.iff", owner.getPlanet())
	infiltrator_belt = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_belt.iff", owner.getPlanet())						
	infiltrator_boots = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_boots.iff", owner.getPlanet())
	infiltrator_gloves = core.objectService.createObject("object/tangible/wearables/armor/infiltrator/shared_armor_infiltrator_s01_gloves.iff", owner.getPlanet())
											
	inventory.add(infiltrator_bicep_r);
	inventory.add(infiltrator_bicep_l);
	inventory.add(infiltrator_bracer_r);
	inventory.add(infiltrator_bracer_l);
	inventory.add(infiltrator_leggings);
	inventory.add(infiltrator_helmet);
	inventory.add(infiltrator_chest);
	inventory.add(infiltrator_belt);
	inventory.add(infiltrator_boots);
	inventory.add(infiltrator_gloves);
	screenOne(core, owner)
	return
	
	
def ithoriandefenderArmor(owner, inventory):
	ithoriandefender_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bicep_r.iff", owner.getPlanet())
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bicep_l.iff", owner.getPlanet())
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bracer_r.iff", owner.getPlanet())
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bracer_l.iff", owner.getPlanet())
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bracer_l.iff", owner.getPlanet())
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_leggings = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_leggings.iff", owner.getPlanet())
	ithoriandefender_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_helmet = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_helmet.iff", owner.getPlanet())
	ithoriandefender_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	ithoriandefender_chest = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_chest_plate.iff", owner.getPlanet())
	ithoriandefender_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ithoriandefender_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ithoriandefender_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ithoriandefender_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ithoriandefender_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ithoriandefender_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	ithoriandefender_boots = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_boots.iff", owner.getPlanet())
	ithoriandefender_gloves = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_gloves.iff", owner.getPlanet())	
											
	inventory.add(ithoriandefender_bicep_r);
	inventory.add(ithoriandefender_bicep_l);
	inventory.add(ithoriandefender_bracer_r);
	inventory.add(ithoriandefender_bracer_l);
	inventory.add(ithoriandefender_leggings);
	inventory.add(ithoriandefender_helmet);
	inventory.add(ithoriandefender_chest);
	inventory.add(ithoriandefender_boots);
	inventory.add(ithoriandefender_gloves);
	screenOne(core, owner)
	return
	
	
def kashyyykianblackmountainArmor(owner, inventory):
	kashyyykianblackmountain_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bicep_r.iff", owner.getPlanet())
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bicep_l.iff", owner.getPlanet())
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bracer_r.iff", owner.getPlanet())
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bracer_l.iff", owner.getPlanet())
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_leggings.iff", owner.getPlanet())
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_helmet = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_helmet.iff", owner.getPlanet())
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_chest_plate.iff", owner.getPlanet())
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	kashyyykianblackmountain_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
	
	kashyyykianblackmountain_boots = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_boots.iff", owner.getPlanet())
	kashyyykianblackmountain_gloves = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_gloves.iff", owner.getPlanet())	
											
	inventory.add(kashyyykianblackmountain_bicep_r);
	inventory.add(kashyyykianblackmountain_bicep_l);
	inventory.add(kashyyykianblackmountain_bracer_r);
	inventory.add(kashyyykianblackmountain_bracer_l);
	inventory.add(kashyyykianblackmountain_leggings);
	inventory.add(kashyyykianblackmountain_helmet);
	inventory.add(kashyyykianblackmountain_chest);
	inventory.add(kashyyykianblackmountain_boots);
	inventory.add(kashyyykianblackmountain_gloves);
	screenOne(core, owner)
	return
	
			
def kashyyykianceremonialArmor(owner, inventory):
	kashyyykianceremonial_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bicep_r.iff", owner.getPlanet())
	kashyyykianceremonial_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bicep_l.iff", owner.getPlanet())
	kashyyykianceremonial_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bracer_r.iff", owner.getPlanet())
	kashyyykianceremonial_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bracer_l.iff", owner.getPlanet())
	kashyyykianceremonial_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_leggings.iff", owner.getPlanet())
	kashyyykianceremonial_helmet = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_helmet.iff", owner.getPlanet())
	kashyyykianceremonial_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_chest_plate.iff", owner.getPlanet())
	kashyyykianceremonial_boots = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_boots.iff", owner.getPlanet())
	kashyyykianceremonial_gloves = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_gloves.iff", owner.getPlanet())	
											
	inventory.add(kashyyykianceremonial_bicep_r);
	inventory.add(kashyyykianceremonial_bicep_l);
	inventory.add(kashyyykianceremonial_bracer_r);
	inventory.add(kashyyykianceremonial_bracer_l);
	inventory.add(kashyyykianceremonial_leggings);
	inventory.add(kashyyykianceremonial_helmet);
	inventory.add(kashyyykianceremonial_chest);
	inventory.add(kashyyykianceremonial_boots);
	inventory.add(kashyyykianceremonial_gloves);
	screenOne(core, owner)
	return
	
			
def kashyyykianhuntingArmor(owner, inventory):
	kashyyykianhunting_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_r.iff", owner.getPlanet())
	kashyyykianhunting_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_l.iff", owner.getPlanet())
	kashyyykianhunting_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_r.iff", owner.getPlanet())
	kashyyykianhunting_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_l.iff", owner.getPlanet())
	kashyyykianhunting_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_leggings.iff", owner.getPlanet())
	kashyyykianhunting_helmet = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_helmet.iff", owner.getPlanet())
	kashyyykianhunting_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_chest_plate.iff", owner.getPlanet())
	kashyyykianhunting_boots = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_boots.iff", owner.getPlanet())
	kashyyykianhunting_gloves = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_gloves.iff", owner.getPlanet())	
											
	inventory.add(kashyyykianhunting_bicep_r);
	inventory.add(kashyyykianhunting_bicep_l);
	inventory.add(kashyyykianhunting_bracer_r);
	inventory.add(kashyyykianhunting_bracer_l);
	inventory.add(kashyyykianhunting_leggings);
	inventory.add(kashyyykianhunting_helmet);
	inventory.add(kashyyykianhunting_chest);
	inventory.add(kashyyykianhunting_boots);
	inventory.add(kashyyykianhunting_gloves);
	screenOne(core, owner)
	return
	
	
def mandalorianArmor(owner, inventory):
	mandalorian_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bicep_r.iff", owner.getPlanet())
	mandalorian_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bicep_l.iff", owner.getPlanet())
	mandalorian_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bracer_r.iff", owner.getPlanet())
	mandalorian_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bracer_l.iff", owner.getPlanet())
	mandalorian_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_leggings = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_leggings.iff", owner.getPlanet())
	mandalorian_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_helmet = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_helmet.iff", owner.getPlanet())
	mandalorian_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mandalorian_chest = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_chest_plate.iff", owner.getPlanet())
	mandalorian_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mandalorian_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mandalorian_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mandalorian_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mandalorian_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mandalorian_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	mandalorian_shoes = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_shoes.iff", owner.getPlanet())
	mandalorian_gloves = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_gloves.iff", owner.getPlanet())	
											
	inventory.add(mandalorian_bicep_r);
	inventory.add(mandalorian_bicep_l);
	inventory.add(mandalorian_bracer_r);
	inventory.add(mandalorian_bracer_l);
	inventory.add(mandalorian_leggings);
	inventory.add(mandalorian_helmet);
	inventory.add(mandalorian_chest);
	inventory.add(mandalorian_shoes);
	inventory.add(mandalorian_gloves);
	screenOne(core, owner)
	return
	
	
def mandalorianimperialArmor(owner, inventory):
	mandalorianimperial_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_bicep_r.iff", owner.getPlanet())
	mandalorianimperial_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_bicep_l.iff", owner.getPlanet())
	mandalorianimperial_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_bracer_r.iff", owner.getPlanet())
	mandalorianimperial_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_bracer_l.iff", owner.getPlanet())
	mandalorianimperial_leggings = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_leggings.iff", owner.getPlanet())
	mandalorianimperial_helmet = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_helmet.iff", owner.getPlanet())
	mandalorianimperial_chest = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_chest_plate.iff", owner.getPlanet())
	mandalorianimperial_boots = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_boots.iff", owner.getPlanet())
	mandalorianimperial_gloves = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_imperial/shared_armor_mandalorian_imperial_gloves.iff", owner.getPlanet())	
											
	inventory.add(mandalorianimperial_bicep_r);
	inventory.add(mandalorianimperial_bicep_l);
	inventory.add(mandalorianimperial_bracer_r);
	inventory.add(mandalorianimperial_bracer_l);
	inventory.add(mandalorianimperial_leggings);
	inventory.add(mandalorianimperial_helmet);
	inventory.add(mandalorianimperial_chest);
	inventory.add(mandalorianimperial_boots);
	inventory.add(mandalorianimperial_gloves);
	screenOne(core, owner)
	return			
	
	
def mandalorianrebelArmor(owner, inventory):
	mandalorianrebel_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_bicep_r.iff", owner.getPlanet())
	mandalorianrebel_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_bicep_l.iff", owner.getPlanet())
	mandalorianrebel_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_bracer_r.iff", owner.getPlanet())
	mandalorianrebel_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_bracer_l.iff", owner.getPlanet())
	mandalorianrebel_leggings = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_leggings.iff", owner.getPlanet())
	mandalorianrebel_helmet = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_helmet.iff", owner.getPlanet())
	mandalorianrebel_chest = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_chest_plate.iff", owner.getPlanet())
	mandalorianrebel_boots = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_boots.iff", owner.getPlanet())
	mandalorianrebel_gloves = core.objectService.createObject("object/tangible/wearables/armor/mandalorian_rebel/shared_armor_mandalorian_rebel_gloves.iff", owner.getPlanet())	
											
	inventory.add(mandalorianrebel_bicep_r);
	inventory.add(mandalorianrebel_bicep_l);
	inventory.add(mandalorianrebel_bracer_r);
	inventory.add(mandalorianrebel_bracer_l);
	inventory.add(mandalorianrebel_leggings);
	inventory.add(mandalorianrebel_helmet);
	inventory.add(mandalorianrebel_chest);
	inventory.add(mandalorianrebel_boots);
	inventory.add(mandalorianrebel_gloves);
	screenOne(core, owner)
	return		
	
	
def marauderassaultArmor(owner, inventory):
	marauderassault_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_bicep_r.iff", owner.getPlanet())
	marauderassault_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_bicep_l.iff", owner.getPlanet())
	marauderassault_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_bracer_r.iff", owner.getPlanet())
	marauderassault_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_bracer_l.iff", owner.getPlanet())
	marauderassault_leggings = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_leggings.iff", owner.getPlanet())
	marauderassault_helmet = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_helmet.iff", owner.getPlanet())
	marauderassault_chest = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_chest_plate.iff", owner.getPlanet())
	marauderassault_boots = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_boots.iff", owner.getPlanet())
	marauderassault_gloves = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s01_gloves.iff", owner.getPlanet())	
											
	inventory.add(marauderassault_bicep_r);
	inventory.add(marauderassault_bicep_l);
	inventory.add(marauderassault_bracer_r);
	inventory.add(marauderassault_bracer_l);
	inventory.add(marauderassault_leggings);
	inventory.add(marauderassault_helmet);
	inventory.add(marauderassault_chest);
	inventory.add(marauderassault_boots);
	inventory.add(marauderassault_gloves);
	screenOne(core, owner)
	return	
	
		
def marauderbattleArmor(owner, inventory):
	marauderbattle_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bicep_r.iff", owner.getPlanet())
	marauderbattle_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bicep_l.iff", owner.getPlanet())
	marauderbattle_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bracer_r.iff", owner.getPlanet())
	marauderbattle_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bracer_l.iff", owner.getPlanet())
	marauderbattle_leggings = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_leggings.iff", owner.getPlanet())
	marauderbattle_helmet = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_helmet.iff", owner.getPlanet())
	marauderbattle_chest = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_chest_plate.iff", owner.getPlanet())
	marauderbattle_boots = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_boots.iff", owner.getPlanet())
	marauderbattle_gloves = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_gloves.iff", owner.getPlanet())	
											
	inventory.add(marauderbattle_bicep_r);
	inventory.add(marauderbattle_bicep_l);
	inventory.add(marauderbattle_bracer_r);
	inventory.add(marauderbattle_bracer_l);
	inventory.add(marauderbattle_leggings);
	inventory.add(marauderbattle_helmet);
	inventory.add(marauderbattle_chest);
	inventory.add(marauderbattle_boots);
	inventory.add(marauderbattle_gloves);
	screenOne(core, owner)
	return	
	
		
def marauderreconArmor(owner, inventory):
	marauderrecon_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_bicep_r.iff", owner.getPlanet())
	marauderrecon_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_bicep_l.iff", owner.getPlanet())
	marauderrecon_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_bracer_r.iff", owner.getPlanet())
	marauderrecon_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_bracer_l.iff", owner.getPlanet())
	marauderrecon_leggings = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_leggings.iff", owner.getPlanet())
	marauderrecon_helmet = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_helmet.iff", owner.getPlanet())
	marauderrecon_chest = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_chest_plate.iff", owner.getPlanet())
	marauderrecon_boots = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_boots.iff", owner.getPlanet())
	marauderrecon_gloves = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s03_gloves.iff", owner.getPlanet())	
											
	inventory.add(marauderrecon_bicep_r);
	inventory.add(marauderrecon_bicep_l);
	inventory.add(marauderrecon_bracer_r);
	inventory.add(marauderrecon_bracer_l);
	inventory.add(marauderrecon_leggings);
	inventory.add(marauderrecon_helmet);
	inventory.add(marauderrecon_chest);
	inventory.add(marauderrecon_boots);
	inventory.add(marauderrecon_gloves);
	screenOne(core, owner)
	return	
	
	
def paddedArmor(owner, inventory):
	padded_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_bicep_r.iff", owner.getPlanet())
	padded_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_bicep_l.iff", owner.getPlanet())
	padded_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_bracer_r.iff", owner.getPlanet())
	padded_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_bracer_l.iff", owner.getPlanet())
	padded_leggings = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_leggings.iff", owner.getPlanet())
	padded_helmet = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_helmet.iff", owner.getPlanet())
	padded_chest = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_chest_plate.iff", owner.getPlanet())
	padded_boots = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_boots.iff", owner.getPlanet())
	padded_gloves = core.objectService.createObject("object/tangible/wearables/armor/padded/shared_armor_padded_s01_gloves.iff", owner.getPlanet())	
											
	inventory.add(padded_bicep_r);
	inventory.add(padded_bicep_l);
	inventory.add(padded_bracer_r);
	inventory.add(padded_bracer_l);
	inventory.add(padded_leggings);
	inventory.add(padded_helmet);
	inventory.add(padded_chest);
	inventory.add(padded_boots);
	inventory.add(padded_gloves);
	screenOne(core, owner)
	return	
	
		
def risArmor(owner, inventory):
	ris_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_bicep_r.iff", owner.getPlanet())
	ris_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_bicep_l.iff", owner.getPlanet())
	ris_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_bracer_r.iff", owner.getPlanet())
	ris_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_bracer_l.iff", owner.getPlanet())
	ris_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_leggings = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_leggings.iff", owner.getPlanet())
	ris_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_helmet = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_helmet.iff", owner.getPlanet())
	ris_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ris_chest = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_chest_plate.iff", owner.getPlanet())
	ris_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ris_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ris_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ris_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ris_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ris_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	ris_boots = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_boots.iff", owner.getPlanet())
	ris_gloves = core.objectService.createObject("object/tangible/wearables/armor/ris/shared_armor_ris_gloves.iff", owner.getPlanet())	
											
	inventory.add(ris_bicep_r);
	inventory.add(ris_bicep_l);
	inventory.add(ris_bracer_r);
	inventory.add(ris_bracer_l);
	inventory.add(ris_leggings);
	inventory.add(ris_helmet);
	inventory.add(ris_chest);
	inventory.add(ris_boots);
	inventory.add(ris_gloves);
	screenOne(core, owner)
	return
	
	
def rebelsnowArmor(owner, inventory):
	rebelsnow_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bicep_r.iff", owner.getPlanet())
	rebelsnow_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bicep_l.iff", owner.getPlanet())
	rebelsnow_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bracer_r.iff", owner.getPlanet())
	rebelsnow_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bracer_l.iff", owner.getPlanet())
	rebelsnow_leggings = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_leggings.iff", owner.getPlanet())
	rebelsnow_helmet = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_helmet.iff", owner.getPlanet())
	rebelsnow_chest = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_chest_plate.iff", owner.getPlanet())
	rebelsnow_boots = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_boots.iff", owner.getPlanet())
	rebelsnow_gloves = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_gloves.iff", owner.getPlanet())
	rebelsnow_belt = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_belt.iff", owner.getPlanet())		
											
	inventory.add(rebelsnow_bicep_r);
	inventory.add(rebelsnow_bicep_l);
	inventory.add(rebelsnow_bracer_r);
	inventory.add(rebelsnow_bracer_l);
	inventory.add(rebelsnow_leggings);
	inventory.add(rebelsnow_helmet);
	inventory.add(rebelsnow_chest);
	inventory.add(rebelsnow_boots);
	inventory.add(rebelsnow_gloves);
	inventory.add(rebelsnow_belt);
	screenOne(core, owner)
	return
	
	
def stormtrooperArmor(owner, inventory):
	stormtrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_r.iff", owner.getPlanet())
	stormtrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_l.iff", owner.getPlanet())
	stormtrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_r.iff", owner.getPlanet())
	stormtrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_l.iff", owner.getPlanet())
	stormtrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_leggings.iff", owner.getPlanet())
	stormtrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_helmet.iff", owner.getPlanet())
	stormtrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_chest_plate.iff", owner.getPlanet())
	stormtrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_boots.iff", owner.getPlanet())
	stormtrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_gloves.iff", owner.getPlanet())
	stormtrooper_belt = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_belt.iff", owner.getPlanet())	
											
	inventory.add(stormtrooper_bicep_r);
	inventory.add(stormtrooper_bicep_l);
	inventory.add(stormtrooper_bracer_r);
	inventory.add(stormtrooper_bracer_l);
	inventory.add(stormtrooper_leggings);
	inventory.add(stormtrooper_helmet);
	inventory.add(stormtrooper_chest);
	inventory.add(stormtrooper_boots);
	inventory.add(stormtrooper_gloves);
	inventory.add(stormtrooper_belt);
	screenOne(core, owner)
	return
	
	
def scouttrooperArmor(owner, inventory):
	scouttrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_bicep_r.iff", owner.getPlanet())
	scouttrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_bicep_l.iff", owner.getPlanet())
	scouttrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_bracer_r.iff", owner.getPlanet())
	scouttrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_bracer_l.iff", owner.getPlanet())
	scouttrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_leggings.iff", owner.getPlanet())
	scouttrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_helmet.iff", owner.getPlanet())
	scouttrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	scouttrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_chest_plate.iff", owner.getPlanet())
	scouttrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	scouttrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	scouttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	scouttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	scouttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	scouttrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	scouttrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_boots.iff", owner.getPlanet())
	scouttrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_belt.iff", owner.getPlanet())	
	scouttrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(scouttrooper_bicep_r);
	inventory.add(scouttrooper_bicep_l);
	inventory.add(scouttrooper_bracer_r);
	inventory.add(scouttrooper_bracer_l);
	inventory.add(scouttrooper_leggings);
	inventory.add(scouttrooper_helmet);
	inventory.add(scouttrooper_chest);
	inventory.add(scouttrooper_belt);
	inventory.add(scouttrooper_boots);
	inventory.add(scouttrooper_gloves);
	screenOne(core, owner)
	return
	
	
def snowtrooperArmor(owner, inventory):
	snowtrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_bicep_r.iff", owner.getPlanet())
	snowtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_bicep_l.iff", owner.getPlanet())
	snowtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_bracer_r.iff", owner.getPlanet())
	snowtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_bracer_l.iff", owner.getPlanet())
	snowtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_leggings.iff", owner.getPlanet())
	snowtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_helmet.iff", owner.getPlanet())
	snowtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	snowtrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_chest_plate.iff", owner.getPlanet())
	snowtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	snowtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	snowtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	snowtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	snowtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	snowtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	snowtrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_boots.iff", owner.getPlanet())
	snowtrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/snowtrooper/shared_armor_snowtrooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(snowtrooper_bicep_r);
	inventory.add(snowtrooper_bicep_l);
	inventory.add(snowtrooper_bracer_r);
	inventory.add(snowtrooper_bracer_l);
	inventory.add(snowtrooper_leggings);
	inventory.add(snowtrooper_helmet);
	inventory.add(snowtrooper_chest);
	inventory.add(snowtrooper_boots);
	inventory.add(snowtrooper_gloves);
	screenOne(core, owner)
	return
	
	
def tantelArmor(owner, inventory):
	tantel_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bicep_r.iff", owner.getPlanet())
	tantel_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bicep_l.iff", owner.getPlanet())
	tantel_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bracer_r.iff", owner.getPlanet())
	tantel_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bracer_l.iff", owner.getPlanet())
	tantel_leggings = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_leggings.iff", owner.getPlanet())
	tantel_helmet = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_helmet.iff", owner.getPlanet())
	tantel_chest = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_chest_plate.iff", owner.getPlanet())
	tantel_boots = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_boots.iff", owner.getPlanet())
	tantel_gloves = core.objectService.createObject("object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_gloves.iff", owner.getPlanet())	
											
	inventory.add(tantel_bicep_r);
	inventory.add(tantel_bicep_l);
	inventory.add(tantel_bracer_r);
	inventory.add(tantel_bracer_l);
	inventory.add(tantel_leggings);
	inventory.add(tantel_helmet);
	inventory.add(tantel_chest);
	inventory.add(tantel_boots);
	inventory.add(tantel_gloves);
	screenOne(core, owner)
	return	
	
	
def ubeseArmor(owner, inventory):
	ubese_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_bracer_r.iff", owner.getPlanet())
	ubese_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ubese_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ubese_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ubese_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ubese_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ubese_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ubese_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_bracer_l.iff", owner.getPlanet())
	ubese_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ubese_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ubese_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ubese_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ubese_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ubese_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ubese_pants = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_pants.iff", owner.getPlanet())
	ubese_pants.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ubese_pants.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ubese_pants.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ubese_pants.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ubese_pants.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ubese_pants.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ubese_helmet = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_helmet.iff", owner.getPlanet())
	ubese_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ubese_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ubese_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ubese_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ubese_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ubese_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	ubese_jacket = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_jacket.iff", owner.getPlanet())
	ubese_jacket.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	ubese_jacket.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	ubese_jacket.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	ubese_jacket.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	ubese_jacket.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	ubese_jacket.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	ubese_bandolier = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_bandolier.iff", owner.getPlanet())
	ubese_boots = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_boots.iff", owner.getPlanet())	
	ubese_gloves = core.objectService.createObject("object/tangible/wearables/armor/ubese/shared_armor_ubese_gloves.iff", owner.getPlanet())	
											
	inventory.add(ubese_bandolier);
	inventory.add(ubese_bracer_r);
	inventory.add(ubese_bracer_l);
	inventory.add(ubese_pants);
	inventory.add(ubese_helmet);
	inventory.add(ubese_jacket);
	inventory.add(ubese_boots);
	inventory.add(ubese_gloves);
	screenOne(core, owner)
	return

	
def jediMasterCloak(owner, inventory):
	cloak0 = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_s05_h1.iff', owner.getPlanet())	
	cloak1 = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_dark_s05.iff', owner.getPlanet())	
	cloak2 = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_light_s05.iff', owner.getPlanet())	
	cloak3 = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_prefect_talmont.iff', owner.getPlanet())	
	cloak3 = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_inquisitor.iff', owner.getPlanet())
	inventory.add(cloak0)
	inventory.add(cloak1)
	inventory.add(cloak2)
	inventory.add(cloak3)
	screenOne(core, owner)
	return
	
def jediKnightRobes(owner, inventory):
	robe1 = core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_light_s03.iff", owner.getPlanet())
	robe2 = core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_dark_s03.iff", owner.getPlanet())
	inventory.add(robe1)
	inventory.add(robe2)
	screenOne(core, owner)
	return
	
def lightsabers(owner, inventory):
	lightsaber1 = core.objectService.createObject("object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff", owner.getPlanet())
	lightsaber1.setIntAttribute("required_combat_level", 90);
	lightsaber1.setStringAttribute("class_required", "Jedi");
	lightsaber1.setAttackSpeed(1);
	lightsaber1.setDamageType("energy");
	lightsaber1.setMaxRange(5);
	lightsaber1.setMinDamage(689);
	lightsaber1.setMaxDamage(1379);
	lightsaber1.setWeaponType(WeaponType.ONEHANDEDSABER);
						
	lightsaber2 = core.objectService.createObject("object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff", owner.getPlanet())
	lightsaber2.setIntAttribute("required_combat_level", 90);
	lightsaber2.setStringAttribute("class_required", "Jedi");
	lightsaber2.setAttackSpeed(1);
	lightsaber2.setDamageType("energy");
	lightsaber2.setMaxRange(5);
	lightsaber2.setMinDamage(689);
	lightsaber2.setMaxDamage(1379);
	lightsaber2.setWeaponType(WeaponType.TWOHANDEDSABER);
						
	lightsaber3 = core.objectService.createObject("object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff", owner.getPlanet())
	lightsaber3.setIntAttribute("required_combat_level", 90);
	lightsaber3.setStringAttribute("class_required", "Jedi");
	lightsaber3.setAttackSpeed(1);
	lightsaber3.setDamageType("energy");
	lightsaber3.setMaxRange(5);
	lightsaber3.setMinDamage(689);
	lightsaber3.setMaxDamage(1379);
	lightsaber3.setWeaponType(WeaponType.POLEARMSABER);
	
	inventory.add(lightsaber1)
	inventory.add(lightsaber2)
	inventory.add(lightsaber3)
	screenOne(core, owner)
	return
	
def lightsaberPowerCrystals(owner, inventory):
	kraytPearl = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", owner.getPlanet())
	kraytPearl.setAttachment("LootItemName", "kraytpearl_premium");
	core.lootService.handleSpecialItems(kraytPearl, "kraytpearl");
	kraytPearl2 = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", owner.getPlanet())
	kraytPearl2.setAttachment("LootItemName", "kraytpearl_premium");
	core.lootService.handleSpecialItems(kraytPearl2, "kraytpearl");
	kraytPearl3 = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", owner.getPlanet())
	kraytPearl3.setAttachment("LootItemName", "kraytpearl_premium");
	core.lootService.handleSpecialItems(kraytPearl3, "kraytpearl");
	kraytPearl4 = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", owner.getPlanet())
	kraytPearl4.setAttachment("LootItemName", "kraytpearl_premium");
	core.lootService.handleSpecialItems(kraytPearl4, "kraytpearl");
	kraytPearl5 = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", owner.getPlanet())
	kraytPearl5.setAttachment("LootItemName", "kraytpearl_premium");
	core.lootService.handleSpecialItems(kraytPearl5, "kraytpearl");
	inventory.add(kraytPearl);
	inventory.add(kraytPearl2);
	inventory.add(kraytPearl3);
	inventory.add(kraytPearl4);
	inventory.add(kraytPearl5);
	screenOne(core, owner)
	return
	
def lightsaberColorCrystals(owner, inventory):
	colorCrystal = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_force_crystal.iff", owner.getPlanet())
	colorCrystal.getAttributes().put("@obj_attr_n:condition", "100/100");
	colorCrystal.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");
	colorCrystal.setAttachment("radial_filename", "item/tunable");
	colorCrystal.setCustomizationVariable('/private/index_color_1', 5)
	
	colorCrystal2 = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_force_crystal.iff", owner.getPlanet())
	colorCrystal2.getAttributes().put("@obj_attr_n:condition", "100/100");
	colorCrystal2.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");
	colorCrystal2.setAttachment("radial_filename", "item/tunable");
	colorCrystal2.setCustomizationVariable('/private/index_color_1', 1)
	
	lavaCrystal = core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_lava_crystal.iff", owner.getPlanet())
	inventory.add(colorCrystal)
	inventory.add(colorCrystal2)
	inventory.add(lavaCrystal)
	screenOne(core, owner)
	return
	
def jediBelt(owner, inventory):
	# inventory.add(core.objectService.createObject("object/tangible/wearables/backpack/shared_fannypack_s01.iff", owner.getPlanet()))
	screenOne(core, owner)
	return
	
def backpack(owner, inventory):
	# inventory.add(core.objectService.createObject("object/tangible/wearables/backpack/shared_backpack_s01.iff", owner.getPlanet()))
	screenOne(core, owner)
	return

def starterEquipmentBox(owner, inventory):
	inventory.add(core.objectService.createObject("object/tangible/npe/shared_npe_uniform_box.iff", owner.getPlanet()))
	screenOne(core, owner)
	return
	
def heroismJewelry(owner, inventory):
	heroismBand = core.objectService.createObject("object/tangible/wearables/ring/shared_ring_s04.iff", owner.getPlanet(), "item_band_set_hero_01_01")					
	heroismRing = core.objectService.createObject("object/tangible/wearables/ring/shared_ring_s02.iff", owner.getPlanet(), "item_ring_set_hero_01_01")			
	heroismNecklace = core.objectService.createObject("object/tangible/wearables/necklace/shared_necklace_s10.iff", owner.getPlanet(), "item_necklace_set_hero_01_01")			
	heroismBraceletRight = core.objectService.createObject("object/tangible/wearables/bracelet/shared_bracelet_s03_r.iff", owner.getPlanet(), "item_bracelet_r_set_hero_01_01")			
	heroismBraceletLeft = core.objectService.createObject("object/tangible/wearables/bracelet/shared_bracelet_s03_l.iff", owner.getPlanet(), "item_bracelet_l_set_hero_01_01")
						
	inventory.add(heroismBand)
	inventory.add(heroismRing)
	inventory.add(heroismNecklace)
	inventory.add(heroismBraceletRight)
	inventory.add(heroismBraceletLeft)
	screenOne(core, owner)
	return
	
def vehicleHandler(owner, inventory):
	speederbike = core.objectService.createObject('object/tangible/deed/vehicle_deed/shared_speederbike_swoop_deed.iff', owner.getPlanet())
	inventory.add(speederbike)
	screenOne(core, owner)
	return
	
def cbtHandler(owner, inventory):
	cbt = core.objectService.createObject('object/tangible/terminal/shared_terminal_character_builder.iff', owner.getPlanet())
	npe = core.objectService.createObject('object/tangible/terminal/shared_terminal_npe_transition.iff', owner.getPlanet())
	inventory.add(cbt)
	inventory.add(npe)
	screenOne(core, owner)
	return
	
def eisleyTravel(owner):
	position = Point3D(3472, 4, -4870)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return
	
def jediRuinsTravel(owner):
	position = Point3D(4260, 4, 5373)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dantooine"), position, owner.getOrientation(), None)
	return
	
def GCWDearicTravel(owner):
	position = Point3D(264, 4, -2950)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("talus"), position, owner.getOrientation(), None)
	return	
	
def GCWKerenTravel(owner):
	position = Point3D(1366, 13, 2747)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return
	
def GCWBestineTravel(owner):
	position = Point3D(-1385,12, -3597)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return

def surveyDevices(owner, inventory):
	mineralSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_mineral.iff", owner.getPlanet())			
	chemicalSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_inorganic.iff", owner.getPlanet())		
	floraSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_lumber.iff", owner.getPlanet())
	gasSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_gas.iff", owner.getPlanet())
	waterSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_moisture.iff", owner.getPlanet())
	windSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_wind.iff", owner.getPlanet())
	solarSurveyTool = core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_solar.iff", owner.getPlanet())
	inventory.add(mineralSurveyTool)
	inventory.add(chemicalSurveyTool)
	inventory.add(floraSurveyTool)
	inventory.add(gasSurveyTool)
	inventory.add(waterSurveyTool)
	inventory.add(windSurveyTool)
	inventory.add(solarSurveyTool)
	return
	
def GCWBanners(owner, inventory):

	gcwBanner1 = core.objectService.createObject('object/tangible/gcw/pvp_rank_rewards/shared_rebel_battle_banner.iff', owner.getPlanet())
	gcwBanner1.setStfFilename('static_item_n')
	gcwBanner1.setStfName('item_pvp_captain_battle_banner_rebel_reward_04_01')
	gcwBanner1.setDetailFilename('static_item_d')
	gcwBanner1.setDetailName('item_pvp_captain_battle_banner_rebel_reward_04_01')
	inventory.add(gcwBanner1)
	gcwBanner2 = core.objectService.createObject('object/tangible/gcw/pvp_rank_rewards/shared_imperial_battle_banner.iff', owner.getPlanet())
	gcwBanner2.setStfFilename('static_item_n')
	gcwBanner2.setStfName('item_pvp_captain_battle_banner_imperial_reward_04_01')
	gcwBanner2.setDetailFilename('static_item_d')
	gcwBanner2.setDetailName('item_pvp_captain_battle_banner_imperial_reward_04_01')
	inventory.add(gcwBanner2)
	return

def bountyDroids(owner, inventory):
	seekers = core.objectService.createObject('object/tangible/mission/shared_mission_bounty_droid_seeker.iff', owner.getPlanet())
	seekers.setUses(20)
	inventory.add(seekers)
	
	probe = core.objectService.createObject('object/tangible/mission/shared_mission_bounty_droid_probot.iff', owner.getPlanet())
	probe.setUses(20)
	inventory.add(probe)
	return

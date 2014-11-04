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
	window.addListBoxMenuItem('Assault Trooper', 0)
	window.addListBoxMenuItem('Composite', 1)
	window.addListBoxMenuItem('Clone Trooper', 2)
	window.addListBoxMenuItem('Marine', 3)
	window.addListBoxMenuItem('RIS', 4)
	window.addListBoxMenuItem('Rebel snow', 5)
	window.addListBoxMenuItem('Stormtrooper', 6)
	window.addListBoxMenuItem('Scout Trooper', 7)
	window.addListBoxMenuItem('Snow Trooper', 8)
	window.addListBoxMenuItem('Ubese', 9)
	window.addListBoxMenuItem('Mando', 10)
	window.addListBoxMenuItem('Jedi Master Cloak', 11)
	window.addListBoxMenuItem('Jedi Knight Robes', 12)
	core.suiService.openSUIWindow(window);
	
def armorHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		assaulttrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='1':
		compositeArmor(owner, inventory)
		return	
	if returnList.get(0)=='2':
		clonetrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='3':
		marineArmor(owner, inventory)
		return
	if returnList.get(0)=='4':
		risArmor(owner, inventory)
		return
	if returnList.get(0)=='5':
		rebelsnowArmor(owner, inventory)
		return
	if returnList.get(0)=='6':
		stormtrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='7':
		scouttrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='8':
		snowtrooperArmor(owner, inventory)
		return
	if returnList.get(0)=='9':
		ubeseArmor(owner, inventory)
		return
	if returnList.get(0)=='10':
		mandoArmor(owner, inventory)
		return
	if returnList.get(0)=='11':
		jediMasterCloak(owner, inventory)
		return
	if returnList.get(0)=='12':
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
	
	
def assaulttrooperArmor(owner, inventory):
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
	clonetrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bicep_l.iff", owner.getPlanet())
	clonetrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bracer_r.iff", owner.getPlanet())
	clonetrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_bracer_l.iff", owner.getPlanet())
	clonetrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_leggings.iff", owner.getPlanet())
	clonetrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_helmet.iff", owner.getPlanet())
	clonetrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	clonetrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/clone_trooper/shared_armor_clone_trooper_neutral_s01_chest_plate.iff", owner.getPlanet())
	clonetrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	clonetrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	clonetrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	clonetrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	clonetrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	clonetrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
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
	
	
def marineArmor(owner, inventory):
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
	rebelsnow_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bicep_l.iff", owner.getPlanet())
	rebelsnow_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bracer_r.iff", owner.getPlanet())
	rebelsnow_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_bracer_l.iff", owner.getPlanet())
	rebelsnow_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_leggings = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_leggings.iff", owner.getPlanet())
	rebelsnow_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_helmet = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_helmet.iff", owner.getPlanet())
	rebelsnow_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	rebelsnow_chest = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_chest_plate.iff", owner.getPlanet())
	rebelsnow_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	rebelsnow_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	rebelsnow_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	rebelsnow_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	rebelsnow_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	rebelsnow_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	rebelsnow_boots = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_boots.iff", owner.getPlanet())
	rebelsnow_gloves = core.objectService.createObject("object/tangible/wearables/armor/rebel_snow/shared_armor_rebel_snow_gloves.iff", owner.getPlanet())	
											
	inventory.add(rebelsnow_bicep_r);
	inventory.add(rebelsnow_bicep_l);
	inventory.add(rebelsnow_bracer_r);
	inventory.add(rebelsnow_bracer_l);
	inventory.add(rebelsnow_leggings);
	inventory.add(rebelsnow_helmet);
	inventory.add(rebelsnow_chest);
	inventory.add(rebelsnow_boots);
	inventory.add(rebelsnow_gloves);
	screenOne(core, owner)
	return
	
	
def stormtrooperArmor(owner, inventory):
	stormtrooper_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_r.iff", owner.getPlanet())
	stormtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_l.iff", owner.getPlanet())
	stormtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_r.iff", owner.getPlanet())
	stormtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_l.iff", owner.getPlanet())
	stormtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_leggings = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_leggings.iff", owner.getPlanet())
	stormtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_helmet = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_helmet.iff", owner.getPlanet())
	stormtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	stormtrooper_chest = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_chest_plate.iff", owner.getPlanet())
	stormtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	stormtrooper_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	stormtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	stormtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	stormtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	stormtrooper_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	stormtrooper_boots = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_boots.iff", owner.getPlanet())
	stormtrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(stormtrooper_bicep_r);
	inventory.add(stormtrooper_bicep_l);
	inventory.add(stormtrooper_bracer_r);
	inventory.add(stormtrooper_bracer_l);
	inventory.add(stormtrooper_leggings);
	inventory.add(stormtrooper_helmet);
	inventory.add(stormtrooper_chest);
	inventory.add(stormtrooper_boots);
	inventory.add(stormtrooper_gloves);
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
	scouttrooper_gloves = core.objectService.createObject("object/tangible/wearables/armor/scout_trooper/shared_armor_scout_trooper_gloves.iff", owner.getPlanet())	
											
	inventory.add(scouttrooper_bicep_r);
	inventory.add(scouttrooper_bicep_l);
	inventory.add(scouttrooper_bracer_r);
	inventory.add(scouttrooper_bracer_l);
	inventory.add(scouttrooper_leggings);
	inventory.add(scouttrooper_helmet);
	inventory.add(scouttrooper_chest);
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
	
	
def mandoArmor(owner, inventory):
	mando_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bicep_r.iff", owner.getPlanet())
	mando_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bicep_l.iff", owner.getPlanet())
	mando_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bracer_r.iff", owner.getPlanet())
	mando_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_bracer_l.iff", owner.getPlanet())
	mando_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_leggings = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_leggings.iff", owner.getPlanet())
	mando_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_helmet = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_helmet.iff", owner.getPlanet())
	mando_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
	mando_chest = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_chest_plate.iff", owner.getPlanet())
	mando_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
	mando_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
	mando_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
	mando_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
	mando_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
	mando_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);						
	mando_shoes = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_shoes.iff", owner.getPlanet())
	mando_gloves = core.objectService.createObject("object/tangible/wearables/armor/mandalorian/shared_armor_mandalorian_gloves.iff", owner.getPlanet())	
											
	inventory.add(mando_bicep_r);
	inventory.add(mando_bicep_l);
	inventory.add(mando_bracer_r);
	inventory.add(mando_bracer_l);
	inventory.add(mando_leggings);
	inventory.add(mando_helmet);
	inventory.add(mando_chest);
	inventory.add(mando_shoes);
	inventory.add(mando_gloves);
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

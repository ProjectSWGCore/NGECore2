import sys
from java.util import HashMap
from java.util import Vector
from services.sui.SUIService import ListBoxType
from services.sui.SUIService import InputBoxType
from main import NGECore
from java.lang import Long
from services.sui.SUIWindow import Trigger

def setup():
	return
	
def run(core, actor, target, commandString):
	
	if actor.getCombatFlag() > 0:
		return
	
	cell = actor.getContainer()
	building = actor.getGrandparent()
	ghost = actor.getSlottedObject('ghost')
		
	if not ghost or not cell or not building or not core.housingService.getPermissions(actor, cell):
		return
		
	if ghost.getAmountOfVendors() >= actor.getSkillModBase('manage_vendor'):
		actor.sendSystemMessage('@player_structure:full_vendors', 0)
		return
	
	suiOptions = HashMap()
	suiOptions.put(Long(1), '@player_structure:terminal')
	suiOptions.put(Long(2), '@player_structure:droid')
	# TODO add creatures
	window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, '@player_structure:vendor_type_t', '@player_structure:vendor_type_d', suiOptions, actor, None, 5)
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleFirstWindow)
	core.suiService.openSUIWindow(window)
	return
	
def handleFirstWindow(actor, window, eventType, returnList):
	
	core = NGECore.getInstance()
	index = int(returnList.get(0))
	selected = window.getObjectIdByIndex(index)
	hiring = actor.getSkillModBase('hiring')
	if selected == 1:
		suiOptions = HashMap()
		if hiring >= 10:
			suiOptions.put(Long(1), '@player_structure:terminal_bulky')
		if hiring >= 30:
			suiOptions.put(Long(2), '@player_structure:terminal_standard')		
		if hiring >= 50:
			suiOptions.put(Long(3), '@player_structure:terminal_small')
		if hiring >= 75:
			suiOptions.put(Long(4), '@player_structure:terminal_fancy')
		if hiring >= 90:
			suiOptions.put(Long(5), '@player_structure:terminal_slim')
		window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, '@player_structure:terminal_type_t', '@player_structure:terminal_type_d', suiOptions, actor, None, 5)
		returnList = Vector()
		returnList.add('List.lstList:SelectedRow')
		window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleTerminalWindow)
		core.suiService.openSUIWindow(window)
	
	if selected == 2:
		suiOptions = HashMap()
		if hiring >= 20:
			suiOptions.put(Long(1), '@player_structure:droid_bartender')
		if hiring >= 50:
			suiOptions.put(Long(2), '@player_structure:droid_power')		
		if hiring >= 60:
			suiOptions.put(Long(3), '@player_structure:droid_wed')
		if hiring >= 90:
			suiOptions.put(Long(4), '@player_structure:droid_surgical')
		if hiring >= 100:
			suiOptions.put(Long(5), '@player_structure:droid_protocol')
		window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, '@player_structure:droid_type_t', '@player_structure:droid_type_d', suiOptions, actor, None, 5)
		returnList = Vector()
		returnList.add('List.lstList:SelectedRow')
		window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleDroidWindow)
		core.suiService.openSUIWindow(window)
	
	return
	
def handleTerminalWindow(actor, window, eventType, returnList):

	core = NGECore.getInstance()
	index = int(returnList.get(0))
	selected = window.getObjectIdByIndex(index)
	
	if selected == 1:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_terminal_bulky.iff')
	elif selected == 2:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_terminal_standard.iff')
	elif selected == 3:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_terminal_small.iff')
	elif selected == 4:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_terminal_fancy.iff')
	elif selected == 5:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_terminal_slim.iff')

	createNameVendorWindow(core, actor)
	
	return

def handleDroidWindow(actor, window, eventType, returnList):

	core = NGECore.getInstance()
	index = int(returnList.get(0))
	selected = window.getObjectIdByIndex(index)
	
	if selected == 1:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_droid_bartender.iff')
	elif selected == 2:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_droid_power.iff')
	elif selected == 3:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_droid_wed.iff')
	elif selected == 4:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_droid_surgical.iff')
	elif selected == 5:
		actor.setAttachment('selectedVendorTemplate', 'object/tangible/vendor/shared_vendor_droid_protocol.iff')

	createNameVendorWindow(core, actor)

	return
	
def createNameVendorWindow(core, actor):

	window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, '@player_structure:name_t', '@player_structure:name_d', actor, None, 5, handleVendorName)
	core.suiService.openSUIWindow(window)
	return
	
def handleVendorName(actor, window, eventType, returnList):

	core = NGECore.getInstance()
	name = returnList.get(0)
	print name
	inventory = actor.getSlottedObject('inventory')
	ghost = actor.getSlottedObject('ghost')
	
	if not core.characterService.checkName(name, actor.getClient()):
		actor.sendSystemMessage('@player_structure:obscene', 0)
		createNameVendorWindow(core, actor)
		return
	
	if not actor.getAttachment('selectedVendorTemplate') or not inventory or not ghost:
		actor.sendSystemMessage('@player_structure:create_failed', 0)
		return
		
	if actor.getInventoryItemCount() >= 80:
		actor.sendSystemMessage('@player_structure:inventory_full_generic', 0)
		return
	
	vendor = core.objectService.createObject(actor.getAttachment('selectedVendorTemplate'), actor.getPlanet())
	
	if not vendor:
		actor.sendSystemMessage('@player_structure:create_failed', 0)
		return
		
	actor.setAttachment('selectedVendorTemplate', None)
	vendor.setAttachment('isVendor', True)
	vendor.setAttachment('onMap', True)	
	vendor.setAttachment('maintenanceAmount', 0)
	vendor.setAttachment('vendorOwner', Long(actor.getObjectID()))
	vendor.setAttachment('initialized', False)
	vendor.setAttachment('vendorSearchEnabled', False)
	vendor.setCustomName('Vendor: ' + name)
	ghost.addVendor(vendor.getObjectID())
	inventory.add(vendor)
	actor.sendSystemMessage('@player_structure:create_success', 0)
	return
	
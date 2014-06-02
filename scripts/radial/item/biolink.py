from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.lang import Long
from main import NGECore
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		bioLinkPrompt(core, owner, target, option)
	if option == 15 and target:
		core.objectService.destroyObject(target)
	return
	
def bioLinkPrompt(core, owner, target, option):

	if not 'shared_character_inventory' in target.getContainer().getTemplate():
		owner.sendSystemMessage('@base_player:must_biolink_to_use_from_inventory', 1)
		return
	bl = target.getStringAttribute('bio_link')
	#if not '<pending>' in bl:
	#	owner.sendSystemMessage('@base_player:not_linked_to_holder', 1) # This should never occur, because a bio-linked item should have another radial
	#	return
	owner.setAttachment('BioLinkItemCandidate', Long(target.getObjectID()))
	window = core.suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@sui:bio_link_item_title')
	window.setProperty('Prompt.lblPrompt:Text', '@sui:bio_link_item_prompt')		
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnUpdate:visible', 'False')
	window.setProperty('btnCancel:Text', '@cancel')
	window.setProperty('btnOk:Text', '@ui_radial:bio_link')		
	returnList = Vector()
	
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, bioLinkProcess)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, bioLinkCancel)		
	core.suiService.openSUIWindow(window);
	return
	
def bioLinkProcess(owner, window, eventType, returnList):
	objectID = owner.getAttachment('BioLinkItemCandidate')
	linkedobject = NGECore.getInstance().objectService.getObject(objectID)
	owner.sendSystemMessage('@base_player:item_bio_linked', 1)
	newRadial = linkedobject.getAttachment('Post_BL_radial_filename')
	linkedobject.setAttachment('radial_filename', newRadial)
	linkedobject.setStringAttribute('bio_link', owner.getCustomName())
	return
	
def bioLinkCancel(owner, window, eventType, returnList):
	
	return
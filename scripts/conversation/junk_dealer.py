from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_bef51e38')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)
	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_54fab04f')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	prose3 = ProsePackage('conversation/junk_dealer_generic', 's_48')
	outOfBand3 = OutOfBand()
	outOfBand3.addProsePackage(prose3)
	prose4 = ProsePackage('conversation/junk_dealer_generic', 's_3aa18b2d')
	outOfBand4 = OutOfBand()
	outOfBand4.addProsePackage(prose4)
	option1 = ConversationOption(outOfBand2, 0)
	option2 = ConversationOption(outOfBand3, 1)
	option3 = ConversationOption(outOfBand4, 1)
	options = Vector()
	options.add(option1)
	options.add(option2)
	options.add(option3)
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return
	
def handleFirstScreen(core, actor, npc, selection):

	if selection == 0:
		# sell items

		window = core.suiService.createSUIWindow('Script.listBox', actor, npc, 0);
		window.setProperty('bg.caption.lblTitle:Text', '@loot_dealer:sell_title');
		window.setProperty('Prompt.lblPrompt:Text', '@loot_dealer:sell_prompt');
				
		for item in actor.getinventoryitems():
			window.addListBoxMenuItem(item.getCustomName(), 0);
				
		window.setProperty('btnOther:visible', 'True')
		window.setProperty('btnCancel:visible', 'True')
		window.setProperty('btnOk:visible', 'True')
		window.setProperty('btnOther:Text', 'Examine Item')
		window.setProperty('btnCancel:Text', '@cancel')
		window.setProperty('btnOk:Text', '@loot_dealer:btn_sell')		
		returnList = Vector()
		returnList.add('List.lstList:SelectedRow')
	
		window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, sellWindowCallBack)
		window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, sellWindowCallBack)
		window.addHandler(2, '', Trigger.TRIGGER_UPDATE, returnList, sellWindowCallBack)		
		core.suiService.openSUIWindow(window);
	
		return
		
	if selection == 1:
		# expertise reset
		# TODO: check for prices
		convSvc = core.conversationService
		prose = ProsePackage('conversation/junk_dealer_generic', 's_35')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/junk_dealer_generic', 's_71')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/junk_dealer_generic', 's_72')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)
		options = Vector()
		options.add(option1)
		options.add(option2)
		convSvc.sendConversationOptions(actor, npc, options, handleResetScreen)


	return
	
def handleRespecScreen(core, actor, npc, selection):

	if selection == 0:
		core.skillService.sendRespecWindow(actor)
		
	core.conversationService.handleEndConversation(actor, npc)

	return

	
def handleResetScreen(core, actor, npc, selection):

	if selection == 0:
		core.skillService.resetExpertise(actor)
		
	core.conversationService.handleEndConversation(actor, npc)

	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_38')
	return
	
	
def sellWindowCallBack(owner, window, eventType, returnList):

	if eventType == 0 and len(returnList) == 2:
		cashCredits = int(returnList.get(0))
		bankCredits = int(returnList.get(1))

		if bankCredits + cashCredits == owner.getCashCredits() + owner.getBankCredits():
			owner.setCashCredits(cashCredits)
			owner.setBankCredits(bankCredits)
			owner.sendSystemMessage('@base_player:bank_success', 0)
	return
	
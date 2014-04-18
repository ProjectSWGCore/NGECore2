from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

sellItemListRef = 0
buyItemListRef = 0
coreRef = 0
junkDealerRef = 0

def startConversation(core, actor, npc):
	core.lootService.prepInv(actor)
	global coreRef
	global junkDealerRef
	global sellItemListRef
	sellItemListRef = core.lootService.getSellableInventoryItems(actor)
	coreRef = core
	junkDealerRef = npc
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
	
	if core.lootService.getBuyHistory(actor,npc)!=None: 
		prose5 = ProsePackage('conversation/junk_dealer_generic', 's_43')
		outOfBand5 = OutOfBand()
		outOfBand5.addProsePackage(prose5)
		option4 = ConversationOption(outOfBand5, 1)
		options.add(option4)
	
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return
	
def handleFirstScreen(core, actor, npc, selection):

	if selection == 0:
		# sell items
		
		sellItemListRef = core.lootService.getSellableInventoryItems(actor)
		
		if len(sellItemListRef) == 0:
			actor.sendSystemMessage('@loot_dealer:no_items', 1)
			startConversation(coreRef,actor,junkDealerRef)
			return # no point
		
		window = core.suiService.createSUIWindow('Script.listBox', actor, npc, 0)
		window.setProperty('bg.caption.lblTitle:Text', '@loot_dealer:sell_title')
		window.setProperty('Prompt.lblPrompt:Text', '@loot_dealer:sell_prompt')
	
		
		
		for item in sellItemListRef:
			window.addListBoxMenuItem(item.getCustomName(), 0);
			actor.sendSystemMessage('ddf ' + item.getCustomName(), 1)
				
		window.setProperty('btnOther:visible', 'True')
		window.setProperty('btnCancel:visible', 'True')
		window.setProperty('btnOk:visible', 'True')
		window.setProperty('btnOther:Text', '@loot_dealer:examine')
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
		# mark items
		window = core.suiService.createSUIWindow('Script.listBox', actor, npc, 0)
		window.setProperty('bg.caption.lblTitle:Text', '@loot_dealer:junk_no_sell_title')
		window.setProperty('Prompt.lblPrompt:Text', '@loot_dealer:junk_no_sell_desc')
		
		sellItemListRef = core.lootService.getSellableInventoryItems(actor)
		
		for item in sellItemListRef:
			nameString = item.getCustomName()
			nameString = 'TestJunkItem'
			if item.isNoSell():
				nameString = '[*No Sell*]' + item.getCustomName()
				window.addListBoxMenuItem(nameString, 0)
			else:
				nameString = '[*Sell*]' + item.getCustomName()
				window.addListBoxMenuItem(nameString, 0)
				
		window.setProperty('btnOther:visible', 'True')
		window.setProperty('btnCancel:visible', 'True')
		window.setProperty('btnOk:visible', 'True')
		window.setProperty('btnOther:Text', '@loot_dealer:examine')
		window.setProperty('btnCancel:Text', '@cancel')
		window.setProperty('btnOk:Text', '@loot_dealer:junk_no_sell_button')		
		returnList = Vector()
		returnList.add('List.lstList:SelectedRow')
	
		#window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, noSellWindowCallBack)
		#window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, noSellWindowCallBack)
		window.addHandler(0, '',9, returnList, noSellWindowCallBack)
		window.addHandler(1, '',10, returnList, noSellWindowCallBack)
		window.addHandler(2, '',11, returnList, noSellWindowCallBack)		
		core.suiService.openSUIWindow(window);
		return
		
	if selection == 3:
		# buy back

		window = core.suiService.createSUIWindow('Script.listBox', actor, npc, 0)
		window.setProperty('bg.caption.lblTitle:Text', '@loot_dealer:buy_back_title')
		window.setProperty('Prompt.lblPrompt:Text', '@loot_dealer:buy_back_prompt')
		
		buyHistory = core.lootService.getBuyHistory(actor,junkDealerRef)
		if buyHistory==None:
			return
		
		for item in buyHistory:
			nameString = '[%s' % 100  + ']' + item.getCustomName() #%item.getJunkDealerPrice()
			window.addListBoxMenuItem(nameString, 0)
				
		window.setProperty('btnOther:visible', 'True')
		window.setProperty('btnCancel:visible', 'True')
		window.setProperty('btnOk:visible', 'True')
		window.setProperty('btnOther:Text', '@loot_dealer:examine')
		window.setProperty('btnCancel:Text', '@cancel')
		window.setProperty('btnOk:Text', '@loot_dealer:btn_buy_back')		
		returnList = Vector()
		returnList.add('List.lstList:SelectedRow')
	
		window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, buyBackWindowCallBack)
		window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, buyBackWindowCallBack)
		window.addHandler(2, '', Trigger.TRIGGER_UPDATE, returnList, buyBackWindowCallBack)		
		core.suiService.openSUIWindow(window);
	
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
	
	if returnList.get(0) == 0:
		owner.sendSystemMessage("huh?", 1)
		#startConversation(coreRef,owner,junkDealerRef)
		return
	
	#sell
	if eventType == 0:
		
		sellItem = sellItemListRef.get(int(returnList.get(0)))
		
		#if len(sellItemListRef):
			#owner.sendSystemMessage('@junk_dealer:no_items', 1)
			#return
		
		if sellItem == None:
			owner.sendSystemMessage('@junk_dealer:no_items', 1)
			return
		
		inventory = owner.getSlottedObject('inventory')		
		
		
		junkPrice = 20
		owner.setCashCredits(owner.getCashCredits()+junkPrice);
		
		# add item to 10-item junk dealer history
		coreRef.lootService.addToBuyHistory(owner,junkDealerRef,sellItem)
		
		#You sell %TT for %DI credits.
		owner.sendSystemMessage("sold "+sellItem.getCustomName(), 1)
		#owner.sendSystemMessage(ProsePackage("@junk_dealer:prose_sold_junk", "TT", sellItem.getCustomName()), 0)
		
		inventory.remove(sellItem)
		
		coreRef.suiService.closeSUIWindow(owner,window.getWindowId());
		handleFirstScreen(coreRef, owner, junkDealerRef, 0)
		return
	
	#cancel
	if eventType == 1:
		owner.sendSystemMessage("cancel ", 1)
		#coreRef.suiService.closeSUIWindow(window,window.getWindowId());
		#startConversation(coreRef,owner,junkDealerRef)
		return
	
	#examine
	if eventType == 2:
		owner.sendSystemMessage("examine ", 1)
		#coreRef.suiService.closeSUIWindow(window,window.getWindowId());
		#startConversation(coreRef,owner,junkDealerRef)
		#owner.sendSystemMessage('Examine', 0)
		return
		
	return
	
def noSellWindowCallBack(owner, window, eventType, returnList):
	
	if returnList.get(0) == 0:
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#toggle
	if eventType == 0:
		owner.sendSystemMessage('Toggle', 0)
		toggleItem = sellItemListRef.get(int(returnList.get(0)))
		if toggleItem.isNoSell():
			toggleItem.setNoSell(0)
		else:
			toggleItem.setNoSell(1)
		
		#core.suiService.closeSUIWindow(window);
		handleFirstScreen(coreRef, owner, junkDealerRef, 1)
		return
	
	#cancel
	if eventType == 1:
		owner.sendSystemMessage('Cancle', 0)
		sellItem = returnList.get(0)
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#examine
	if eventType == 2:
		owner.sendSystemMessage('Ex', 0)
		sellItem = returnList.get(0)
		startConversation(coreRef,owner,junkDealerRef)
		owner.sendSystemMessage('Examine', 0)
		return
		
def buyBackWindowCallBack(owner, window, eventType, returnList):
	
	if returnList.get(0) == 0:
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#buy
	if eventType == 0:		
		index = int(returnList.get(0))
		buyHistory = coreRef.lootService.getBuyHistory(owner,junkDealerRef)
		if len(buyHistory) == 0:
			owner.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 1)
		
		buyBackItem = buyHistory.get(index)
		
		if buyBackItem == None:
			owner.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 0)
			startConversation(coreRef,owner,junkDealerRef)
			return
			
		junkPrice = 20
		if owner.getCashCredits()>=junkPrice and owner.getInventoryItemCount() < 80:			
			owner.setCashCredits(owner.getCashCredits()-junkPrice)			
			inventory = owner.getSlottedObject('inventory')							
			inventory.add(buyBackItem)
			# remove from junk dealer history
			coreRef.lootService.removeBoughtBackItemFromHistory(owner,junkDealerRef,buyBackItem)
		else:
			owner.sendSystemMessage('@loot_dealer:prose_no_buy_back', 0)
			
		coreRef.suiService.closeSUIWindow(owner,window.getWindowId());
		handleFirstScreen(coreRef, owner, junkDealerRef, 3)
		return
	
	#cancel
	if eventType == 1:
		owner.sendSystemMessage('Cancle', 0)
		sellItem = returnList.get(0)
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#examine
	if eventType == 2:
		owner.sendSystemMessage('Ex', 0)
		sellItem = returnList.get(0)
		startConversation(coreRef,owner,junkDealerRef)
		owner.sendSystemMessage('Examine', 0)
		return
		
	
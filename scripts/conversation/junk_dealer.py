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
	#core.lootService.prepInv(actor)
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
	
	if len(core.lootService.getBuyHistory(actor,npc))!= 0:
		prose5 = ProsePackage('conversation/junk_dealer_generic', 's_43')
		outOfBand5 = OutOfBand()
		outOfBand5.addProsePackage(prose5)
		option4 = ConversationOption(outOfBand5, 1)
		options.add(option4)
	
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return
	
def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	
	if selection == 0:
		# sell items
		
		prose = ProsePackage('conversation/junk_dealer_generic', 's_84a67771')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
			
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
				nameString = '[Sellable]' + item.getCustomName()
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
	
	if selection == 2:
		lootKitScreen1(core, actor, npc)
	
	
	if selection == 3:
		# buy back
		
		buyHistory = coreRef.lootService.getBuyHistory(actor,npc)
		if len(buyHistory) == 0:
			actor.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 1)
			startConversation(coreRef,actor,npc)
			return
		
		prose = ProsePackage('conversation/junk_dealer_generic', 's_44')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		
		window = core.suiService.createSUIWindow('Script.listBox', actor, npc, 0)
		window.setProperty('bg.caption.lblTitle:Text', '@loot_dealer:buy_back_title')
		window.setProperty('Prompt.lblPrompt:Text', '@loot_dealer:buy_back_prompt')
		
		buyHistory = core.lootService.getBuyHistory(actor,junkDealerRef)
		if buyHistory==None:
			return
		
		
		
		for item in buyHistory:
			junkDealerPrice = item.getJunkDealerPrice()
			if junkDealerPrice == 0:
				junkDealerPrice = 20
			nameString = '[%s' % junkDealerPrice  + ']' + item.getCustomName() #%
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
	core.conversationService.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_4bd9d15e')
	return
	
	
def sellWindowCallBack(owner, window, eventType, returnList):
	
	sellItemListRef = coreRef.lootService.getSellableInventoryItems(owner)
	
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
		
		
		junkDealerPrice = sellItem.getJunkDealerPrice()
		if junkDealerPrice == 0:
			junkDealerPrice = 20
		owner.setCashCredits(owner.getCashCredits()+junkDealerPrice)
		
		# add item to 10-item junk dealer history
		coreRef.lootService.addToBuyHistory(owner,junkDealerRef,sellItem)
		
		owner.sendSystemMessage(OutOfBand.ProsePackage("@junk_dealer:prose_sold_junk", "TT", sellItem.getCustomName(),junkDealerPrice), 0)
		
		inventory.remove(sellItem)
		
		coreRef.suiService.closeSUIWindow(owner,window.getWindowId())
		handleFirstScreen(coreRef, owner, junkDealerRef, 0)
		return
	
	#cancel
	if eventType == 1:
		owner.sendSystemMessage("cancel ", 1)
		coreRef.suiService.closeSUIWindow(owner,window.getWindowId())
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#examine
	if eventType == 2:
		owner.sendSystemMessage("examine ", 1)
		#coreRef.suiService.closeSUIWindow(window,window.getWindowId())
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
		
		#core.suiService.closeSUIWindow(window)
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
	
	convSvc = coreRef.conversationService
	
	if returnList.get(0) == 0:
		startConversation(coreRef,owner,junkDealerRef)
		return
	
	#buy
	if eventType == 0:		
		index = int(returnList.get(0))
		buyHistory = coreRef.lootService.getBuyHistory(owner,junkDealerRef)
		if len(buyHistory) == 0:
			owner.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 1)
			startConversation(coreRef,owner,junkDealerRef)
			return
		
		buyBackItem = buyHistory.get(index)
		
		if buyBackItem == None:
			owner.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 0)
			startConversation(coreRef,owner,junkDealerRef)
			return
		
		if owner.getInventoryItemCount() >= 80:
			prose = ProsePackage('conversation/junk_dealer_generic', 's_47')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(owner, junkDealerRef, outOfBand)
			return
			
		junkDealerPrice = buyBackItem.getJunkDealerPrice()
		if junkDealerPrice == 0:
			junkDealerPrice = 20
		owner.setCashCredits(owner.getCashCredits()+junkDealerPrice)
		
		if owner.getCashCredits()>=junkDealerPrice:			
			owner.setCashCredits(owner.getCashCredits()-junkDealerPrice)			
			inventory = owner.getSlottedObject('inventory')							
			inventory.add(buyBackItem)
			owner.sendSystemMessage('You buy back ' + buyBackItem.getCustomName() + ' for %s' % junkDealerPrice + ' credits.', 0)
			# remove from junk dealer history
			coreRef.lootService.removeBoughtBackItemFromHistory(owner,junkDealerRef,buyBackItem)
		else:
			owner.sendSystemMessage('@loot_dealer:prose_no_buy_back', 0)
			
		coreRef.suiService.closeSUIWindow(owner,window.getWindowId());
		handleFirstScreen(coreRef, owner, junkDealerRef, 3)
		return
	
	#cancel
	if eventType == 1:
		owner.sendSystemMessage('Cancel', 0)
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

def handleLootScreenSelection1(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	if selection == 0:
		lootKitScreen1(core, actor, npc)
	
	return	
	
def handleLootScreenSelection2(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	if selection == 0:
		lootKitScreen2(core, actor, npc)
	
	return
	
def handleLootScreenSelection3(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	if selection == 0:
		lootKitScreen3(core, actor, npc)
	
	return
	
def handleLootScreenSelection4(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	if selection == 0:
		lootKitScreen4(core, actor, npc)
	
	return
	
def handleLootScreenSelection5(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	if selection == 0:
		lootKitScreen5a(core, actor, npc)
		
	if selection == 1:
		lootKitScreen5b(core, actor, npc)
	
	return
	
def handleLootScreenSelection6(core, actor, npc, selection):
	
	convSvc = core.conversationService	
	prose = ProsePackage('conversation/junk_dealer_generic', 's_14efaaa2')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)
	if selection == 0:
		template = 'object/tangible/loot/collectible/kits/shared_orange_rug_kit.iff'
		addLootKit(core, actor, npc,template)
		
	if selection == 1:
		template = 'object/tangible/loot/collectible/kits/shared_blue_rug_kit.iff'
		addLootKit(core, actor, npc,template)
	
	if selection == 2:
		template = 'object/tangible/loot/collectible/kits/shared_gong_kit.iff'
		addLootKit(core, actor, npc,template)
		
	if selection == 3:
		template = 'object/tangible/loot/collectible/kits/shared_light_table_kit.iff'
		addLootKit(core, actor, npc,template)
		
	if selection == 4:
		template = 'object/tangible/loot/collectible/kits/shared_sculpture_kit.iff'
		addLootKit(core, actor, npc,template)
	
	return
	
def addLootKit(core, actor, npc,template):

	kit = core.objectService.createObject(template, actor.getPlanet())
	inventory = actor.getSlottedObject('inventory')							
	inventory.add(kit)
	return
	
def lootKitScreen1(core, actor, npc):
	
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_d9e6b751')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)

	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_6d53d062')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	
	option1 = ConversationOption(outOfBand2, 0)
		
	options = Vector()
	options.add(option1)
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection2)

	return
	
def lootKitScreen2(core, actor, npc):
	
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_e29f48dc')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)
	
	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_324b9b0f')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	
	option1 = ConversationOption(outOfBand2, 0)
		
	options = Vector()
	options.add(option1)
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection3)
	
	return
	
def lootKitScreen3(core, actor, npc):
	
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_12fe83a6')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)

	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_e1a103e5')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	
	option1 = ConversationOption(outOfBand2, 0)
		
	options = Vector()
	options.add(option1)
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection4)
	
	return
	
def lootKitScreen4(core, actor, npc):
	
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_4d65752')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)

	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_d347bee3')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	prose3 = ProsePackage('conversation/junk_dealer_generic', 's_b60b73f8')
	outOfBand3 = OutOfBand()
	outOfBand3.addProsePackage(prose3)
	
	option1 = ConversationOption(outOfBand2, 0)
	option2 = ConversationOption(outOfBand3, 0)
		
	options = Vector()
	options.add(option1)
	options.add(option2)
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection5)
	
	return
	
def lootKitScreen5a(core, actor, npc):
	
	convSvc = core.conversationService
	prose = ProsePackage('conversation/junk_dealer_generic', 's_3fc7eb45')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)

	prose2 = ProsePackage('conversation/junk_dealer_generic', 's_ee977dee')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	prose3 = ProsePackage('conversation/junk_dealer_generic', 's_8f39769')
	outOfBand3 = OutOfBand()
	outOfBand3.addProsePackage(prose3)
	prose4 = ProsePackage('conversation/junk_dealer_generic', 's_fe657cdd')
	outOfBand4 = OutOfBand()
	outOfBand4.addProsePackage(prose4)
	prose5 = ProsePackage('conversation/junk_dealer_generic', 's_9ede4b84')
	outOfBand5 = OutOfBand()
	outOfBand5.addProsePackage(prose5)
	prose6 = ProsePackage('conversation/junk_dealer_generic', 's_87c5851b')
	outOfBand6 = OutOfBand()
	outOfBand6.addProsePackage(prose6)
	
	option1 = ConversationOption(outOfBand2, 0)
	option2 = ConversationOption(outOfBand3, 0)
	option3 = ConversationOption(outOfBand4, 0)
	option4 = ConversationOption(outOfBand5, 0)
	option5 = ConversationOption(outOfBand6, 0)
		
	options = Vector()
	options.add(option1)
	options.add(option2)
	options.add(option3)
	options.add(option4)
	options.add(option5)
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection6)
	
	return
	
def lootKitScreen5b(core, actor, npc):
	
	startConversation(core,actor,npc)
	return
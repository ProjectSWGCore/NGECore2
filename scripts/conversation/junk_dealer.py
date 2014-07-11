from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
from main import NGECore
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_bef51e38'))
	
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_54fab04f'), 0))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_48'), 1))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_3aa18b2d'), 2))
	
	buyBack = actor.getAttachment('buy_back')

	if buyBack is not None and buyBack != 0 and core.objectService.objsInContainer(actor, core.objectService.getObject(buyBack)) > 0:
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_43'), 3))
	
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return
	
def handleFirstScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	if selection == 0:
		# sell items

		sellItemListRef = core.lootService.getSellableInventoryItems(actor)
		
		if len(sellItemListRef) == 0:
			actor.sendSystemMessage('@loot_dealer:no_items', 1)
			startConversation(core, actor, npc)
			return # no point
		
		convSvc.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_84a67771')
		core.lootService.handleJunkDealerSellWindow(actor, npc, sellItemListRef)
		return
		
	if selection == 1:
		# Mark Items
		convSvc.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_50')
		core.lootService.handleJunkDealerMarkItems(actor, npc)
		return
	
	if selection == 2:
		lootKitScreen1(core, actor, npc)
		return
	
	if selection == 3:
		# buy back
		
		buyHistory = core.objectService.objsInContainer(actor, core.objectService.getObject(actor.getAttachment('buy_back')))
		if buyHistory == 0:
			actor.sendSystemMessage('@loot_dealer:no_buy_back_items_found', 1)
			startConversation(core,actor,npc)
			return
		
		convSvc.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_44')
		core.lootService.handleJunkDealerBuyBackWindow(actor, npc)
		return
	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/junk_dealer_generic', 's_4bd9d15e')
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
	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_14efaaa2'))
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

	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_d9e6b751'))

	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_6d53d062'), 0))
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection2)

	return
	
def lootKitScreen2(core, actor, npc):
	
	convSvc = core.conversationService

	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_e29f48dc'))
	
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_324b9b0f'), 0))
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection3)
	
	return
	
def lootKitScreen3(core, actor, npc):
	
	convSvc = core.conversationService

	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_12fe83a6'))

	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_e1a103e5'), 0))

	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection4)

	return
	
def lootKitScreen4(core, actor, npc):
	
	convSvc = core.conversationService

	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_4d65752'))

	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_d347bee3'), 0))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_b60b73f8'), 1))
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection5)

	return
	
def lootKitScreen5a(core, actor, npc):
	
	convSvc = core.conversationService

	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_3fc7eb45'))

	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_ee977dee'), 0))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_8f39769'), 1))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_fe657cdd'), 2))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_9ede4b84'), 3))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/junk_dealer_generic:s_87c5851b'), 4))
	
	convSvc.sendConversationOptions(actor, npc, options, handleLootScreenSelection6)
	
	return
	
def lootKitScreen5b(core, actor, npc):
	
	startConversation(core,actor,npc)
	return
from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	prose = ProsePackage('conversation/respecseller', 's_31')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)
	prose2 = ProsePackage('conversation/respecseller', 's_32')
	outOfBand2 = OutOfBand()
	outOfBand2.addProsePackage(prose2)
	prose3 = ProsePackage('conversation/respecseller', 's_33')
	outOfBand3 = OutOfBand()
	outOfBand3.addProsePackage(prose3)
	option1 = ConversationOption(outOfBand2, 0)
	option2 = ConversationOption(outOfBand3, 1)
	options = Vector()
	options.add(option1)
	options.add(option2)
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return
	
def handleFirstScreen(core, actor, npc, selection):

	if selection == 0:
		# respec
		# TODO: check for prices
		convSvc = core.conversationService
		prose = ProsePackage('conversation/respecseller', 's_58')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/respecseller', 's_60')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/respecseller', 's_64')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)
		options = Vector()
		options.add(option1)
		options.add(option2)
		convSvc.sendConversationOptions(actor, npc, options, handleRespecScreen)
		return
	if selection == 1:
		# expertise reset
		# TODO: check for prices
		convSvc = core.conversationService
		prose = ProsePackage('conversation/respecseller', 's_35')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/respecseller', 's_71')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/respecseller', 's_72')
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
	core.conversationService.sendStopConversation(actor, npc, 'conversation/respecseller', 's_38')
	return
	
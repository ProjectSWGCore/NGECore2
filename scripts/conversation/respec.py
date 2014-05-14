from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	convSvc.sendConversationMessage(actor, npc, OutOfBand(ProsePackage('conversation/respecseller:s_31')))
	
	options = Vector()
	options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_32')), 0))
	options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_33')), 1))
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	
	return

def handleFirstScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	# TODO: check for prices
	
	if selection == 0: # respec
		convSvc.sendConversationMessage(actor, npc, OutOfBand(ProsePackage('conversation/respecseller:s_58')))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_60')), 0))
		options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_64')), 1))
		convSvc.sendConversationOptions(actor, npc, options, handleRespecScreen)
	
	if selection == 1: # expertise reset
		convSvc.sendConversationMessage(actor, npc, OutOfBand(ProsePackage('conversation/respecseller:s_35')))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_71')), 0))
		options.add(ConversationOption(OutOfBand(ProsePackage('conversation/respecseller:s_72')), 1))
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

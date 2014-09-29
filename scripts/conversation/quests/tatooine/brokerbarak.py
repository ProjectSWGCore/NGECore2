from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	player = actor.getPlayerObject()
	if player is None:
		return
	quest = player.getQuest('smuggle_generic_1')
	if quest is None or quest.isCompleted():
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/generic_broker_1:s_17'), 0)) # Yes.
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/generic_broker_1:s_18'), 0)) # Not right now.
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/generic_broker_1:s_14'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)
		return
	elif player.getFactionStanding() >= 1000:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/generic_broker_1', 's_8')
	else:
	 	core.conversationService.sendStopConversation(actor, npc, 'conversation/generic_broker_1', 's_4')
	return

def handleOptionsOne(core, actor, npc, selection):
	if selection == 0: # Yes.
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/generic_broker_1:s_24'), 0)) # OK
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/generic_broker_1:s_22'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	elif selection == 1: # Not right now.
		core.conversationService.sendStopConversation(actor, npc, 'conversation/generic_broker_1', 's_30')
	return

def handleOptionsTwo(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/generic_broker_1', 's_26')
	core.questService.sendQuestAcceptWindow(actor, 'quest/smuggle_generic_1')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/generic_broker_1', 's_4')
	return

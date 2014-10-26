from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	player = actor.getPlayerObject()
	if player is None:
		return
	quest = player.getQuest('tatooine_eisley_tdc')
	if quest is None:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_drixa', 's_43')						
 	elif quest.isCompleted() or quest.getActiveTask() > 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_drixa', 's_40')
	else:
		options = Vector()
		# Here's your order.
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_7'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_5'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)
	return
	
def handleOptionsOne(core, actor, npc, selection):
	options = Vector()
	# I am just delivering what Byxle gave me.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_41'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_9'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	options = Vector()
	# Of course
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_30'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_28'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
	return

def handleOptionsThree(core, actor, npc, selection):
	options = Vector()
	# TDC isn't fast food, it's good food delivered fast.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_38'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_drixa:s_32'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFour)
	return

def handleOptionsFour(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_drixa', 's_40')
	core.questService.handleActivateSignal(actor, npc, 'tatooine_eisley_tdc', 'tat_eisley_tdc_e1')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_drixa', 's_40')
	return

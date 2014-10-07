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
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_22')
	elif quest.isCompleted() or quest.getActiveTask() > 6:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	elif quest.getActiveTask() == 4:
		options = Vector()
		# Here you go, four chewy Chuba combos.
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_18'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_11'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)					
	elif quest.getActiveTask() == 6:
		options = Vector()
		# I am sure Byxle just made a mistake.
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_13'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_6'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_22')
		
	return
	
def handleOptionsOne(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_20')
	core.questService.handleActivateSignal(actor, npc, 'tatooine_eisley_tdc', 'tat_eisley_tdc_e5')
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	options = Vector()
	# TDC isn't fast food, it's good food delivered fast.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_15'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_14'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
	return

def handleOptionsThree(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	core.questService.handleActivateSignal(actor, npc, 'tatooine_eisley_tdc', 'tat_eisley_tdc_e7')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	return

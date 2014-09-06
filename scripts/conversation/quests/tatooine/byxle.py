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
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_58'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_56'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)
		return						
# 	if quest.isCompleted() == True:
# 		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_byxle', 's_48')
# 		return
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_byxle', 's_48')	
	
def handleOptionsOne(core, actor, npc, selection):
	options = Vector()
	# So what do I need to do?
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_62'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_60'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	options = Vector()
	# Ok, Sure. I can do that for you.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_66'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_64'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
	return

def handleOptionsThree(core, actor, npc, selection):
	options = Vector()
	# I have to say that?
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_76'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_byxle:s_68'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFour)
	return

def handleOptionsFour(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_byxle', 's_78')
	core.questService.sendQuestAcceptWindow(actor, 'quest/tatooine_eisley_tdc')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_byxle', 's_48')
	return

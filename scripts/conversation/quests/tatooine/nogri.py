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
		# Too busy getting this cargo ready, no time to talk.
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_nogri', 's_48')						
 	elif quest.isCompleted() or quest.getActiveTask() > 3:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_nogri', 's_42')
	else:
		options = Vector()
		# Got it right here.
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_7'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_5'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)
	return
	
def handleOptionsOne(core, actor, npc, selection):
	options = Vector()
	# That's how you know it's fresh.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_11'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_9'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	options = Vector()
	# You're serious, aren't you?
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_15'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_13'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
	return

def handleOptionsThree(core, actor, npc, selection):
	options = Vector()
	# [Look in the bag]
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_23'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_21'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFour)
	return

def handleOptionsFour(core, actor, npc, selection):
	options = Vector()
	# [Look in the bag]
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_28'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_26'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFive)
	return

def handleOptionsFive(core, actor, npc, selection):
	options = Vector()
	# [Look in the bag]
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_32'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_30'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsSix)
	return

def handleOptionsSix(core, actor, npc, selection):
	options = Vector()
	# [Look in the bag]
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_36'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_34'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsSeven)
	return

def handleOptionsSeven(core, actor, npc, selection):
	options = Vector()
	# [Look in the bag]
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_40'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_nogri:s_38'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsEight)
	return

def handleOptionsEight(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_nogri', 's_42')
	core.questService.handleActivateSignal(actor, npc, 'tatooine_eisley_tdc', 'tat_eisley_tdc_e3')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_nogri', 's_40')
	return

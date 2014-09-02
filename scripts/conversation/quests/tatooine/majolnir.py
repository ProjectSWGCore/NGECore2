from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	player = actor.getPlayerObject()

	if player is None:
		return
	
	quest = player.getQuest('find_majolnir')
	
	if quest is None:
		endConversation(core, actor, npc)
		return
	
	if quest.isCompleted():
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_majolnir', 's_24')
		return
		
	if quest.getActiveTask() == 3:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_majolnir', 's_18')
		core.questService.handleActivateSignal(actor, npc, quest, 3)
		return
		
	endConversation(core, actor, npc)
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_majolnir', 's_65')
	return
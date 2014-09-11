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
	elif quest.isCompleted() == True or quest.getActiveTask() > 6:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_22')
		
	return
	
def handleOptionsOne(core, actor, npc, selection):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_20')
	pos = actor.getWorldPosition()
	
	player = actor.getPlayerObject()
	if player is None:
		return
	quest = player.getQuest('tatooine_eisley_tdc')
	data = core.questService.getQuestData(quest.getName())
	task = data.getTasks().get(5);
	count = task.getCount()
	type = task.getCreatureType()
 	for i in xrange(0, count):
 		core.spawnService.spawnCreature('chuba', actor.getPlanet().getName(), 0, pos.x+1, pos.y, pos.z, 1, 0, 1, 0, 1)
	core.questService.completeActiveTask(actor, quest)
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	options = Vector()
	# TDC isn't fast food, it's good food delivered fast.
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_15'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/tatooine_eisley_jano:s_14'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
	return

def handleOptionsThree(core, actor, npc, selection):
	player = actor.getPlayerObject()
	if player is None:
		return
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	quest = player.getQuest('tatooine_eisley_tdc')
	core.questService.handleActivateSignal(actor, npc, quest, 6)
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_jano', 's_16')
	return

from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	player = actor.getPlayerObject()

	if player is None:
		return
	
	quest = player.getQuest('tatooine_eisley_legacy')
	
	if quest is None:
		endConversation(core, actor, npc)
		return
	
	# Check if player finished the main talking quest, if they did then handle mayor and trehla return/not yet started options
	if quest.isCompleted() == True:
		
		if player.getQuest('tatooine_eisley_gotoniko') is not None:
			core.conversationService.sendStopConversation(actor, npc, '@conversation/c_newbie_secondchance', 's_59')
			return
		
		mayorVourk = player.getQuest('newbie_goto_vourk')
		if mayorVourk is not None:
			if mayorVourk.isCompleted() == False:
				# TODO: Mayor return conv. options
				return
			else:
				endConversation(core, actor, npc)
			return
	
		trehlaVourk = player.getQuest('return_to_vourk')
		if trehlaVourk is not None:
			if trehlaVourk.isCompleted() == False:
				# TODO: Trehla return conv. options
				return
			else:
				endConversation(core, actor, npc)
			return
		
		# Player doesn't have a return quest... Now we will check if they have any active quests for these..
		preTrehla = player.getQuest('tatooine_eisley_gototrehla_v2')
		if preTrehla is not None:
			if preTrehla.isCompleted() == False:
				core.conversationService.sendStopConversation(actor, npc, '@conversation/c_newbie_secondchance', 's_61')
			elif hasActiveTrehlaQuest(core, actor, npc, player) == True: # True = has a pre-quest to return quests
				core.conversationService.sendStopConversation(actor, npc, '@conversation/c_newbie_secondchance', 's_61')
				return
		
		# Trehla checks failed, has to be on Mayor quests.
		endConversation(core, actor, npc)
		return

	if quest.getActiveTask() == 2:
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_118'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_116'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsOne)
		return
		
	endConversation(core, actor, npc)
	return

def handleOptionsOne(core, actor, npc, selection):
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_122'), 0))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_170'), 1))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_120'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
	return
	
def handleOptionsTwo(core, actor, npc, selection):
	if (selection == 0): # So what do I do now?
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_68'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_66'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsThree)
		return
	
	elif (selection == 1): # I'm not doing anything you ask
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_122'), 0))
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_172'))
		core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsTwo)
		return
	return

def handleOptionsThree(core, actor, npc, selection):
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_72'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_70'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFour)
	return

def handleOptionsFour(core, actor, npc, selection):
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_76'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_74'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsFive)
	return
	
def handleOptionsFive(core, actor, npc, selection):
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_83'), 0))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_79'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsSix)
	return
	
def handleOptionsSix(core, actor, npc, selection):
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_134'), 0))
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_138'), 1))
	#options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_142'), 2))
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/c_newbie_secondchance:s_132'))
	core.conversationService.sendConversationOptions(actor, npc, options, handleOptionsSeven)
	return

def handleOptionsSeven(core, actor, npc, selection):
	print ('selection 7: ' + str(selection))
	if (selection == 0): # Ok, I'll do what he says.
		quest = actor.getPlayerObject().getQuest('tatooine_eisley_legacy')
		
		if (actor.getLevel() >= 10):
			core.questService.activateQuest(actor, 'quest/tatooine_eisley_gotoniko')
			return
		else:
			core.questService.activateQuest(actor, 'quest/tatooine_eisley_gotomayor')
			return
		
		core.questService.handleActivateSignal(actor, npc, quest, 2)
		core.conversationService.sendStopConversation(actor, npc, '@conversation/c_newbie_secondchance', 's_136')
		return
	elif (selection == 1): # But I don't know anything about Mos Eisley!
		quest = actor.getPlayerObject().getQuest('tatooine_eisley_legacy')
		core.questService.handleActivateSignal(actor, npc, quest, 2)
		return
	#else if (selection == 2): # What if he asks me to fly a ship for him?
		#return
	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, '@conversation/c_newbie_secondchance', 's_200')
	return

def hasActiveTrehlaQuest(core, actor, npc, player):
	qTrehla = player.getQuest('c_newbie_quest1a')
	if qTrehla is not None:
		if qTrehla.isCompleted() == False:
			return True
	return False
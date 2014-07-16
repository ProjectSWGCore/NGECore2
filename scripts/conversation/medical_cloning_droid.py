from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions


from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger

from java.util import Vector
import sys




def startConversation(core, actor, npc):
	global coreRef
	coreRef = core
	convSvc = core.conversationService
	

	if actor.getBuffByName('cloning_sickness'):
	
		level = int(actor.getLevel())
		
		if level <= 10:
			cloningFee = 100
		elif level >= 11:
			cloningFee = (((actor.getLevel() + 17) * actor.getLevel())/2)
		elif level == 90:
			cloningFee = 5000
			
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/clone_droid:s_6", int(cloningFee)))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/clone_droid:s_8'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/clone_droid:s_12'), 1))

		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		return
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_14')
		return	
	return
	
def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	if selection == 0:

		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/clone_droid:s_10'))

		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/clone_droid:s_12'), 1))

		convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
		
		return

		
	if selection == 1:
		if actor.getLevel() <= 10:
			cloningFee = 100
		elif actor.getLevel() >= 11:
			cloningFee = (((actor.getLevel() + 17) * actor.getLevel())/2)
		elif actor.getLevel() == 90:
			cloningFee = 5000

			
		if actor.getCashCredits() >= cloningFee:
			core.buffService.removeBuffFromCreature(actor, actor.getBuffByName("cloning_sickness"));
			core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_14')
			actor.setCashCredits(actor.getCashCredits() - int(cloningFee))
			return
		elif actor.getCashCredits() <= cloningFee:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_16')
			return
		return		
	
	
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	if actor.getLevel() <= 10:
		cloningFee = 100
	elif actor.getLevel() >= 11:
		cloningFee = (((actor.getLevel() + 17) * actor.getLevel())/2)
	elif actor.getLevel() == 90:
		cloningFee = 5000
	
	if selection == 0:
		if actor.getCashCredits() >= cloningFee:
			core.buffService.removeBuffFromCreature(actor, actor.getBuffByName("cloning_sickness"));
			core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_14')
			actor.setCashCredits(actor.getCashCredits() - int(cloningFee))
			return
		elif actor.getCashCredits() <= cloningFee:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_16')
			return
		return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_4')
	return
			
		
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
		prose = ProsePackage('conversation/clone_droid', 's_6')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/clone_droid', 's_8')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/clone_droid', 's_12')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)

		
		options = Vector()
		options.add(option1)
		options.add(option2)

		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		return
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_4')
		return	
	return
	
def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	if selection == 0:
		prose = ProsePackage('conversation/clone_droid', 's_10')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/clone_droid', 's_12')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		option1 = ConversationOption(outOfBand2, 0)

		
		options = Vector()
		options.add(option1)

		convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
		
		return

		
	if selection == 1:
		core.buffService.removeBuffFromCreature(actor, actor.getBuffByName("cloning_sickness"));
		core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_14')
		return
	return		
	
	
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	
	
	if selection == 0:
		core.buffService.removeBuffFromCreature(actor, actor.getBuffByName("cloning_sickness"));
		core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_14')
		return
	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/clone_droid', 's_4')
	return
	

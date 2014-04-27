from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions
from resources.datatables import PvpStatus
from resources.datatables import FactionStatus

from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger

from java.util import Vector
import sys


impRecruiterRef = 0


def startConversation(core, actor, npc):
	global coreRef
	global impRecruiterRef
	coreRef = core
	impRecruiterRef = npc
	convSvc = core.conversationService
	faction = actor.getFaction()
	factionStatus = actor.getFactionStatus()
	
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		prose = ProsePackage('conversation/faction_recruiter_imperial', 's_414')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_imperial', 's_428')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/faction_recruiter_imperial', 's_85')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		prose4 = ProsePackage('conversation/faction_recruiter_imperial', 's_70')
		outOfBand4 = OutOfBand()
		outOfBand4.addProsePackage(prose4)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)
		option3 = ConversationOption(outOfBand4, 1)

		
		options = Vector()
		options.add(option1)
		options.add(option2)
		options.add(option3)

		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		return
		
	if actor.getFaction == 'imperial' and actor.getFactionstatus == FactionStatus.OnLeave:
		prose = ProsePackage('conversation/faction_recruiter_imperial', 's_80')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_imperial', 's_49')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/faction_recruiter_imperial', 's_410')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		prose4 = ProsePackage('conversation/faction_recruiter_imperial', 's_324')
		outOfBand4 = OutOfBand()
		outOfBand4.addProsePackage(prose4)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)
		option3 = ConversationOption(outOfBand4, 1)
		return
		
	if actor.getFaction() == 'rebel':
		prose = ProsePackage('conversation/faction_recruiter_imperial', 's_306')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		return

def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_imperial', 's_432')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_imperial', 's_434')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_imperial', 's_55')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)
	
			
			options = Vector()
			options.add(option1)
			options.add(option2)
	
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return
	
			
		if selection == 1:
			return
		
		if selection == 2:
			convSvc.sendConversationOptions(actor, npc, options, endConversation)	
			return
		return
	
	
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	
	
	if selection == 0:
		actor.setFaction('imperial')
		prose = ProsePackage('conversation/faction_recruiter_imperial', 's_116')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_imperial', 's_117')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		option1 = ConversationOption(outOfBand2, 0)

		
		options = Vector()
		options.add(option1)


		convSvc.sendConversationOptions(actor, npc, options, endConversation)
		return
		
	
def endConversation(core, actor, npc):
	if actor.getFaction() == 'rebel':
		npc.playEffectObject('all_b_emt_backhand', '')
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_308')
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_100')
	return
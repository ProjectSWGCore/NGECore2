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
	
	if actor.getPvpStatus(PvpStatus.GoingOvert) or actor.getPvpStatus(PvpStatus.GoingCovert):
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_364')
		return	
	elif actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		prose = ProsePackage('conversation/faction_recruiter_rebel', 's_334')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_348')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_83')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand3, 1)

		
		options = Vector()
		options.add(option1)
		options.add(option2)

		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		prose = ProsePackage('conversation/faction_recruiter_rebel', 's_216')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_49')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_330')
		outOfBand3 = OutOfBand()
		outOfBand3.addProsePackage(prose3)
		prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_248')
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
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:
		prose = ProsePackage('conversation/faction_recruiter_rebel', 's_216')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_49')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_330')
		outOfBand4 = OutOfBand()
		outOfBand4.addProsePackage(prose4)
		prose5 = ProsePackage('conversation/faction_recruiter_rebel', 's_248')
		outOfBand5 = OutOfBand()
		outOfBand5.addProsePackage(prose5)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand4, 1)
		option4 = ConversationOption(outOfBand5, 1)
		
		options = Vector()
		options.add(option1)
		options.add(option2)
		options.add(option4)
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFactionStatus() == FactionStatus.SpecialForces:
		prose = ProsePackage('conversation/faction_recruiter_rebel', 's_216')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_49')
		outOfBand2 = OutOfBand()
		outOfBand2.addProsePackage(prose2)
		prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_330')
		outOfBand4 = OutOfBand()
		outOfBand4.addProsePackage(prose4)
		prose5 = ProsePackage('conversation/faction_recruiter_rebel', 's_248')
		outOfBand5 = OutOfBand()
		outOfBand5.addProsePackage(prose5)
		option1 = ConversationOption(outOfBand2, 0)
		option2 = ConversationOption(outOfBand4, 1)
		option4 = ConversationOption(outOfBand5, 1)
		
		options = Vector()
		options.add(option1)
		options.add(option2)
		options.add(option4)
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFaction() == 'imperial':
		npc.setCurrentAnimation('all_b_emt_backhand')
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_230')
		return
	return

def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_352')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_354')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
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
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_362')	
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_50')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_95')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_93')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_74')
			outOfBand4 = OutOfBand()
			outOfBand4.addProsePackage(prose4)
			prose5 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand5 = OutOfBand()
			outOfBand5.addProsePackage(prose5)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)
			option3 = ConversationOption(outOfBand4, 1)
			option4 = ConversationOption(outOfBand5, 1)
	
			
			options = Vector()
			options.add(option1)
			options.add(option2)
			options.add(option3)
			options.add(option4)
	
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return
			
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_imperial:s_332", percent, "TO", str(100 - percent)))
			return
		
		if selection == 2:
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:	
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_50')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_86')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_93')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_74')
			outOfBand4 = OutOfBand()
			outOfBand4.addProsePackage(prose4)
			prose5 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand5 = OutOfBand()
			outOfBand5.addProsePackage(prose5)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)
			option3 = ConversationOption(outOfBand4, 1)
			option4 = ConversationOption(outOfBand5, 1)
	
			
			options = Vector()
			options.add(option1)
			options.add(option2)
			options.add(option3)
			options.add(option4)
	
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return

		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_rebel:s_332", percent, "TO", str(100 - percent)))
			return

		if selection == 2:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_76')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_78')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)
	
			
			options = Vector()
			options.add(option1)
			options.add(option2)
	
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.SpecialForces:	
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_50')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_90')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_95')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			prose4 = ProsePackage('conversation/faction_recruiter_rebel', 's_78')
			outOfBand4 = OutOfBand()
			outOfBand4.addProsePackage(prose4)
			prose5 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand5 = OutOfBand()
			outOfBand5.addProsePackage(prose5)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)
			option3 = ConversationOption(outOfBand4, 1)
			option4 = ConversationOption(outOfBand5, 1)
	
			
			options = Vector()
			options.add(option1)
			options.add(option2)
			options.add(option3)
			options.add(option4)
	
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return

		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_rebel:s_332", percent, "TO", str(100 - percent)))
			return

		if selection == 2:
		
			return
		return

			
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			actor.setFaction('imperial')
			actor.setFactionStatus(FactionStatus.OnLeave)
	
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_99')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_358')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			option1 = ConversationOption(outOfBand2, 0)
	
			
			options = Vector()
			options.add(option1)
	
	
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_356')	
			return
		if selection ==1:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_362')	
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			actor.setFactionStatus(FactionStatus.Combatant)
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_224')
			return
		
		if selection == 1:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_64')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_66')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_226')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			return
		if selection == 2:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_76')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_78')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			return
		if selection == 3:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_97')
			return
		return	
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_88')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_90')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_94')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			return

		if selection == 1:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_64')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_66')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_226')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			return
		if selection == 2:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_76')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_78')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			return
		if selection == 3:
			return
		return	
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.SpecialForces:
		if selection == 0:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_88')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_90')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_94')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			return

		if selection == 1:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_54')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_56')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleSixthScreen)
			return
		if selection == 2:
			prose = ProsePackage('conversation/faction_recruiter_rebel', 's_76')
			outOfBand = OutOfBand()
			outOfBand.addProsePackage(prose)
			convSvc.sendConversationMessage(actor, npc, outOfBand)
			prose2 = ProsePackage('conversation/faction_recruiter_rebel', 's_74')
			outOfBand2 = OutOfBand()
			outOfBand2.addProsePackage(prose2)
			prose3 = ProsePackage('conversation/faction_recruiter_rebel', 's_360')
			outOfBand3 = OutOfBand()
			outOfBand3.addProsePackage(prose3)
			option1 = ConversationOption(outOfBand2, 0)
			option2 = ConversationOption(outOfBand3, 1)

	
			
			options = Vector()
			options.add(option1)
			options.add(option2)

	
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			return
		if selection == 3:
			return	
		return
		
def handleThirdScreen(core, actor, npc, selection):
	if selection == 0:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_58')	
		actor.setFaction('neutral')
		return
	
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_80')
		return	
	return
	
def handleFourthScreen(core, actor, npc, selection):
	if selection == 0:
		actor.setFactionStatus(FactionStatus.SpecialForces)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_224')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_72')
		return
	return

def handleFithScreen(core, actor, npc, selection):
	if selection == 0:
		actor.setFactionStatus(FactionStatus.OnLeave);
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_58')
	return
	
def handleSixthScreen(core, actor, npc, selection):
	if selection == 0:
		actor.setFactionStatus(FactionStatus.Combatant)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_92')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_57')
		return
	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_304')
	return
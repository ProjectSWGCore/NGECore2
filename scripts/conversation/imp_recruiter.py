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
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_444')
		return	
	elif actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_414'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_428'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_85'), 1))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.OnLeave:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_80'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_410'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_324'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.Combatant:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_310'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_410'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_324'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFactionStatus() == FactionStatus.SpecialForces:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_310'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_410'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_324'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFaction() == 'rebel':
		npc.setCurrentAnimation('all_b_emt_backhand')
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_308')
		return
	return

def handleFirstScreen(core, actor, npc, selection):
	
	convSvc = core.conversationService
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_432'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_434'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_440'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			return
						
		if selection == 1:
			return
		
		if selection == 2:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_442')	
			return
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_50'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_97'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_93'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_88'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_55'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_imperial:s_412", percent, "TO", str(100 - percent)))
			return
		if selection == 2:
			return
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.Combatant:	
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('conversation/faction_recruiter_imperial', 's_55'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_74'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_93'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_88'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_55'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_imperial:s_412", percent, "TO", str(100 - percent)))
			return

		if selection == 2:
			core.scriptService.callScript("scripts/", "gcw_rewards_imperial", "handleImperialItems1", core, actor); 
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_90')
			return
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.SpecialForces:	
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_50'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_74'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_97'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_88'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_55'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_imperial:s_412", percent, "TO", str(100 - percent)))
			return
		if selection == 2:
			return
		return

			
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			core.factionService.join(actor, 'imperial')
			core.factionService.changeFactionStatus(actor, FactionStatus.OnLeave)
			
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_116'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_438'), 0))
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_100')
			
			return
		if selection == 1:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_442')	
			return
		return
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			core.factionService.changeFactionStatus(actor, FactionStatus.Combatant)
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_300')
			return
		
		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_64'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_66'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_302'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_90'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_92'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_96'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			return
		if selection == 3:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_95')
			return
		return	
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.Combatant:
		if selection == 0:
			core.factionService.changeFactionStatus(actor, FactionStatus.SpecialForces)
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_76'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_79'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_70'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			return

		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_64'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_66'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_302'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_90'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_92'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_96'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)

			return
		if selection == 3:
			return
		return	
	elif actor.getFaction() == 'imperial' and actor.getFactionStatus() == FactionStatus.SpecialForces:
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('conversation/faction_recruiter_imperial:s_76'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_79'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_70'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			
			return
		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_54'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_56'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_84'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleSixthScreen)
			
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_90'))
				
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_92'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_imperial:s_96'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			
			return
		if selection == 3:
			return	
		return
		
def handleThirdScreen(core, actor, npc, selection):
	if selection == 0:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_94')	
		core.factionService.resign(actor)
		return
	
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_98')
		return	
	return
	
def handleFourthScreen(core, actor, npc, selection):
	if selection == 0:
		core.factionService.changeFactionStatus(actor, FactionStatus.Combatant)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_68')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_72')
		return
	return

def handleFithScreen(core, actor, npc, selection):
	if selection == 0:
		core.factionService.changeFactionStatus(actor, FactionStatus.OnLeave)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_82')
	return
	
def handleSixthScreen(core, actor, npc, selection):
	if selection == 0:
		actor.setFactionStatus(FactionStatus.Combatant)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_58')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_57')
		return
	return
	
def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_304')
	return
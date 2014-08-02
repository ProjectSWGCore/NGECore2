from resources.objects.creature import CreatureObject
from resources.datatables import PvpStatus
from resources.datatables import FactionStatus
import sys
import time

def setup():
    return
    
def run(core, actor, target, commandString):
	faction = actor.getFaction()
	factionStatus = actor.getFactionStatus()

	if actor.getPvpStatus(PvpStatus.GoingOvert) or actor.getPvpStatus(PvpStatus.GoingCovert):
		actor.sendSystemMessage('@faction_recruiter:pvp_status_changing', 0)
		return
	if core.gcwService.isInPvpZone(actor):
		actor.sendSystemMessage('@gcw:pvp_advanced_region_cannot_go_covert', 0)
		return
	
	if commandString != '' and commandString != faction and len(faction)!=0:
		if commandString == 'rebel' or commandString == 'imperial' or commandString == 'neutral':

			if faction != 'neutral' and len(faction)!=0:
				actor.sendSystemMessage('@faction_recruiter:sui_resig_complete_in_5', 0)
			
			if actor.getFactionStatus() == FactionStatus.SpecialForces:
				actor.setPvpStatus(PvpStatus.GoingCovert, True)
				time.sleep(300)
			
			actor.setPvpStatus(PvpStatus.GoingCovert | PvpStatus.Overt | PvpStatus.Attackable | PvpStatus.Aggressive | PvpStatus.Enemy, False)
			actor.setFactionStatus(FactionStatus.OnLeave)
			
			if actor.getFaction() != '' and actor.getFaction() != 'neutral':
				time.sleep(1)
				actor.setFaction('')
				actor.sendSystemMessage('@faction_recruiter:resign_complete', 0)
			
			if commandString == 'neutral' or commandString == 'resign':
				time.sleep(1)
				actor.setFaction('') # ???
				actor.sendSystemMessage('@faction_recruiter:resign_complete', 0)
				return
			
			time.sleep(1)
			actor.setFaction(commandString)
		return
	
	#This can be removed if player is required to see the recruiter first
	if faction == '':
		actor.setFactionStatus(FactionStatus.Combatant) # Must be! otherwise updatePVPstatus will be called with onLeave!
		actor.setFaction(commandString)
		player = actor.getSlottedObject("ghost")
		if player.getCurrentRank()==0:
			player.setCurrentRank(1);
		faction = commandString
	
	if faction == 'neutral' or faction == '':
		actor.sendSystemMessage('@faction_recruiter:not_aligned', 0)
		return
	
	if factionStatus == FactionStatus.OnLeave:
		actor.sendSystemMessage('@faction_recruiter:on_leave_to_covert', 0)
		actor.setPvpStatus(PvpStatus.GoingCovert, True)
		time.sleep(1)
		actor.setPvpStatus(PvpStatus.GoingCovert, False)
		actor.setFactionStatus(FactionStatus.Combatant)		
		actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
		actor.updatePvpStatus()
		core.factionService.updatePVPFromNearbyNPCs(actor, actor.getClient())
		return
	
	if factionStatus == FactionStatus.Combatant:
		actor.sendSystemMessage('@faction_recruiter:covert_to_overt', 0)
		actor.setPvpStatus(PvpStatus.GoingOvert, True)
		time.sleep(30)
		if core.gcwService.isInPvpZone(actor):
			return
		actor.setPvpStatus(PvpStatus.GoingOvert, False)
		actor.setFactionStatus(FactionStatus.SpecialForces)		
		actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
		actor.updatePvpStatus()
		return
	
	if factionStatus == FactionStatus.SpecialForces:
		actor.sendSystemMessage('@faction_recruiter:overt_to_covert', 0)
		actor.setPvpStatus(PvpStatus.GoingCovert, True)
		time.sleep(300)
		if core.gcwService.isInPvpZone(actor):
			return
		actor.setFactionStatus(FactionStatus.Combatant)
		actor.setPvpStatus(PvpStatus.GoingCovert, False)
		actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
		actor.updatePvpStatus()
		return
	
	return

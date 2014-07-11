from resources.common import OutOfBand
from resources.common import ProsePackage
import sys

def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return

def setup(core, actor, target, command):

	if actor.hasCooldown('inside_info'):
		actor.sendSystemMessage('@bounty_hunter:sm_calling_contact_too_often', 0)
		return
	
	elif target.hasCooldown('inside_info_target'):
		actor.sendSystemMessage('@bounty_hunter:sm_calling_contact_too_often_target', 0)
		return

	if target is None:
		target = actor
	
	bountyItem = core.missionService.getBountyListItem(target.getObjectID())
	
	if bountyItem is None:
		target.sendSystemMessage('@bounty_hunter:sm_no_bounty_self', 0)
	
	else:
		hunters = bountyItem.getAssignedHunters()
		if hunters.size() > 0:
			distance = hunterDistance(core, actor, target, hunters)
			if distance != 0:
				target.sendSystemMessage(OutOfBand.ProsePackage('@bounty_hunter:sm_bounty_hunter_distance', 'DI', int(distance)), 0)
				applyCooldowns(actor, target)
				return
			else:
				target.sendSystemMessage(OutOfBand.ProsePackage('@bounty_hunter:sm_bounty_amount_target_with_bounties', 'TO', str(bountyItem.getCreditReward())), 0)
				applyCooldowns(actor, target)
				return
		else:
			target.sendSystemMessage(OutOfBand.ProsePackage('@bounty_hunter:sm_bounty_amount_target_no_bounties', 'TO', str(bountyItem.getCreditReward())), 0)
			applyCooldowns(actor, target)
			return
		return
	return

def hunterDistance(core, actor, target, assignedHunters):
	for objID in assignedHunters:
		hunter = core.objectService.getObject(objID)
		if hunter is not None:
			if hunter.getPlanetId() == target.getPlanetId():
				if target.getPosition().getDistance2D(hunter.getWorldPosition()) < float(300):
					return target.getPosition().getDistance2D(hunter.getWorldPosition())
	return 0

def applyCooldowns(actor, target):
	actor.addCooldown('inside_info', 60)
	if target.getObjectID() != actor.getObjectID():
		target.addCooldown('inside_info_target', 600)
		return
	return
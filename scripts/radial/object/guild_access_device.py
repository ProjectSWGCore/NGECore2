from resources.common import RadialOptions
from services.sui.SUIService import InputBoxType
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):
	# http://i293.photobucket.com/albums/mm62/cebot/radial.jpg

	#radials.clear()
	
	if owner.getGuildId() != 0:

		guild = core.guildService.getGuildById(owner.getGuildId())
		
		if guild is None:
			return
		
		member = guild.getMember(owner.getObjectID())

		
		# TODO: Add List of Guilds radial option
		# TODO: Add List of Guild Wars radial option http://i293.photobucket.com/albums/mm62/cebot/wars.jpg << List of Guild Wars
		
		radials.add(RadialOptions(0, RadialOptions.serverGuildGuildManagement, 3, '@guild:menu_guild_management')) # Guild Management
		radials.add(RadialOptions(0, RadialOptions.serverGuildMemberManagement, 3, '@guild:menu_member_management')) # Member Management
		if guild.isElectionsEnabled() or owner.getObjectID() == guild.getLeader():
			radials.add(RadialOptions(0, 216, 3, '@guild:menu_leader_race')) # Guild Leader Elections

		#### Guild Management ####
		radials.add(RadialOptions(3, RadialOptions.serverGuildInfo, 3, '@guild:menu_info')) # Guild Information
		#radials.add(RadialOptions(3, RadialOptions.serverGuildEnemies, 3, '@guild:menu_enemies')) # Guild Enemies
		#radials.add(RadialOptions(3, 215, 3, '@guild:menu_rank_list')) # Rank List
		# TODO: Add Rank Summary
		radials.add(RadialOptions(3, 217, 3, '@guild:menu_permission_list')) # Permissions List
		
		if member.hasDisbandPermission():
			radials.add(RadialOptions(3, RadialOptions.serverGuildDisband, 3, '@guild:menu_disband')) # Disband Guild
		if member.hasChangeNamePermission():
			radials.add(RadialOptions(3, RadialOptions.serverGuildNameChange, 3, '@guild:menu_namechange')) # Change Guild Name

		#### Member Management ####
		radials.add(RadialOptions(4, RadialOptions.serverGuildMembers, 3, '@guild:menu_members')) # Guild Members
		if member.hasSponsorPermission():
			radials.add(RadialOptions(4, RadialOptions.serverGuildSponsor, 3, '@guild:menu_sponsor')) # Sponsor for Membership
		if member.hasAcceptPermission() and guild.getSponsoredPlayers().size() > 0:
			radials.add(RadialOptions(4, RadialOptions.serverGuildSponsored, 3, '@guild:menu_sponsored')) # Sponsored for Membership
		if owner.getObjectID() == guild.getLeader():
			radials.add(RadialOptions(4, 218, 3, '@guild:menu_member_motd')) # Create a Guild Message
			radials.add(RadialOptions(4, 69, 3, '@guild:menu_leader_change')) # Transfer PA Leadership

		#### Guild Leader Elections ####
		if guild.isElectionsEnabled():

			if guild.isRunningForLeader(owner.getObjectID()) == False:
				radials.add(RadialOptions(5, 72, 3, '@guild:menu_leader_register')) # Register to Run
			else:
				radials.add(RadialOptions(5, 73, 3, '@guild:menu_leader_unregister')) # Unregister from Race
			radials.add(RadialOptions(5, 74, 4, '@guild:menu_leader_vote')) # Cast a Vote
			radials.add(RadialOptions(5, 75, 4, '@guild:menu_leader_standings')) # View Standings
			if owner.getObjectID() == guild.getLeader():
				radials.add(RadialOptions(5, 56, 3, '@guild:menu_disable_elections')) # Disable Elections
		else:
			if owner.getObjectID() == guild.getLeader():
				radials.add(RadialOptions(5, 57, 3, '@guild:menu_enable_elections')) # Enable Elections
		return
	
	return
	
def handleSelection(core, owner, target, option):
	guild = core.guildService.getGuildById(owner.getGuildId())
	
	if guild is None:
		return
	
	#### Guild Management ####
	
	# - Guild Info
	if option == RadialOptions.serverGuildInfo:
		core.guildService.showGuildInfoWindow(owner, guild)
		return
	
	# - Rank List
	elif option == 215:
		return
	
	# - Permissions List
	elif option == 217:
		core.guildService.handleViewPermissionsList(owner, guild)
		return
	
	# - Disband Guild
	elif option == RadialOptions.serverGuildDisband:
		core.guildService.showDisbandConfirmWindow(owner, guild)
		return
	
	# - Change Guild Name
	elif option == RadialOptions.serverGuildNameChange:
		core.guildService.handleChangeGuildName(owner, guild)
		return

	#### Member Management ####
	
	# - Guild Members
	elif option == RadialOptions.serverGuildMembers:
		core.guildService.handleViewGuildMembers(owner, guild)
		return
	
	# - Sponsor for Membership
	elif option == RadialOptions.serverGuildSponsor:
		core.guildService.handleGuildSponsorWindow(owner)
		return
	
	# - Sponsored for Membership
	elif option == RadialOptions.serverGuildSponsored:
		core.guildService.handleManageSponsoredPlayers(owner)
		return
	
	# - Transfer PA Leadership
	elif option == 69:
		core.guildService.handleTransferLeadership(owner, guild)
		return
	
	# - Change Message of the Day
	elif option == 218:
		core.guildService.handleChangeGuildMotd(owner, guild)
		return
		
	#### Guild Leader Elections ####
	
	# - Register to Run
	elif option == 72:
		core.guildService.handleRunForLeader(owner, guild)
		return
	
	# - Unregister from Race
	elif option == 73:
		core.guildService.handleUnregisterForLeader(owner, guild)
		return
	
	# - Cast a Vote
	elif option == 74:
		core.guildService.handleVoteForLeader(owner, guild)
		return
	
	# - View Standings
	elif option == 75:
		core.guildService.handleViewElectionStandings(owner, guild)
		return
		
	# - Enable Elections
	elif option == 57:
		core.guildService.handleEnableGuildElections(guild)
		return

	# - Disable Elections
	elif option == 56:
		return
	return
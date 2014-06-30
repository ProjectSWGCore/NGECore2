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
	return
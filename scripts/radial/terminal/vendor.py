from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	radials.add(RadialOptions(0, 11, 1, ''))
	radials.add(RadialOptions(0, 55, 0, ''))
	radials.add(RadialOptions(2, 56, 1, ''))
	radials.add(RadialOptions(2, 57, 1, ''))
	radials.add(RadialOptions(2, 58, 1, ''))
	radials.add(RadialOptions(2, 59, 1, ''))
	radials.add(RadialOptions(0, 52, 0, ''))
	radials.add(RadialOptions(3, 53, 1, ''))
	radials.add(RadialOptions(3, 54, 1, ''))

	if not owner.getPlayerObject().getOwnedVendors().contains(target.getObjectID())
		return
	radials.add(RadialOptions(0, 112, 3, '@player_structure:vendor_control'))
	if target.getAttachment('initialized') == False:
		radials.add(RadialOptions(1, 118, 3, '@player_structure:vendor_init'))
	else:
		# soe reuses option 118
		radials.add(RadialOptions(4, 118, 3, '@player_structure:vendor_status'))
		radials.add(RadialOptions(4, 217, 3, '@player_structure:give_maintenance'))
		radials.add(RadialOptions(4, 115, 3, '@player_structure:take_maintenance'))
		if owner.getSkillModBase('private_register_vendor') >= 1:
			radials.add(RadialOptions(4, 116, 3, '@player_structure:register_vendor'))
		radials.add(RadialOptions(4, 205, 3, '@player_structure:enable_vendor_search'))
		radials.add(RadialOptions(4, 206, 3, '@player_structure:disable_vendor_search'))
		radials.add(RadialOptions(4, 121, 3, '@player_structure:customize_vendor'))
		
	radials.add(RadialOptions(4, 114, 3, '@player_structure:vendor_pack'))		
	radials.add(RadialOptions(4, 117, 3, '@player_structure:remove_vendor'))	
	
	return
	
def handleSelection(core, owner, target, option):
	return
	
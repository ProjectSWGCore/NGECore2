from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 98, 0, 'Modify primary color'))
	radials.add(RadialOptions(0, 99, 0, 'Modify secondary color'))
	radials.add(RadialOptions(0, 100, 0, 'Modify tirtiary color'))
	radials.add(RadialOptions(0, 101, 0, 'Modify quaternary color'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 98 and target:
		core.suiService.sendColorPicker(owner, target, '/private/index_color_1')
	if option == 99 and target:
		core.suiService.sendColorPicker(owner, target, '/private/index_color_2')
	if option == 100 and target:
		core.suiService.sendColorPicker(owner, target, '/private/index_color_3')
	if option == 101 and target:
		core.suiService.sendColorPicker(owner, target, '/private/index_color_4')
	return
	

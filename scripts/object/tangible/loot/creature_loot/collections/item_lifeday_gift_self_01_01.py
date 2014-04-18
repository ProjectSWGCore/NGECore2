import sys

def setup(core, object):
	object.setStfName('item_lifeday_gift_self_01_01')
	object.setDetailName('item_lifeday_gift_self_01_01')
	object.setCustomName('Life Day Gift Box')
	object.setIntAttribute('volume', 1)
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('tier', 2)
	object.setAttachment('radial_filename', 'holidays/lifeday/lifeday_gift')
	return
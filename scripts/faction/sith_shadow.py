import sys

def isAlly(ally):
	if ally == 'rebel' or ally == 'imperial':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'fs_villager':
		return 1
	return 0
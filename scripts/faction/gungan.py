import sys

def isAlly(ally):
	return 0

def isEnemy(enemy):
	if enemy == 'plasma_thief' or enemy == 'swamp_rat' or enemy == 'borvo':
		return 1
	return 0
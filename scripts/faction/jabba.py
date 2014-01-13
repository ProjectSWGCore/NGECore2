import sys

def isAlly(ally):
	if ally == 'hutt':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'valarian' or enemy == 'borvo':
		return 1
	return 0
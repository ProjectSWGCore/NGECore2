import sys

def isAlly(ally):
	if ally == 'restuss':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'narmle' or enemy == 'spice_collective' or enemy == 'kobola':
		return 1
	return 0
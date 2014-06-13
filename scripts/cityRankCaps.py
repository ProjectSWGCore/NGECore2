import sys
from jarray import array

def addCityRankCaps(core):
	
	cityService = core.playerCityService
	cityService.addCityRankCap('corellia', array([20, 20, 15, 10, 10], 'i'))
	cityService.addCityRankCap('dantooine', array([50, 50, 30, 20, 20], 'i'))
	cityService.addCityRankCap('lok', array([50, 50, 30, 20, 20], 'i'))	
	cityService.addCityRankCap('naboo', array([20, 20, 15, 10, 10], 'i'))
	cityService.addCityRankCap('rori', array([50, 50, 30, 20, 20], 'i'))
	cityService.addCityRankCap('talus', array([50, 50, 30, 20, 20], 'i'))
	cityService.addCityRankCap('tatooine', array([20, 20, 15, 10, 10], 'i'))		
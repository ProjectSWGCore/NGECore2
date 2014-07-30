
#---- Afarathu Cave ---
#---- 7/29/14 ----
#  Scripted by --- Steve -- Aka Oswin ---
#/*******************************************************************************
# * Copyright (c) 2014 <Project SWG>
# * 
# * This File is part of NGECore2.
# * 
# * This program is free software: you can redistribute it and/or modify
# * it under the terms of the GNU Lesser General Public License as published by
# * the Free Software Foundation, either version 3 of the License, or
# * (at your option) any later version.
# * 
# * This program is distributed in the hope that it will be useful,
# * but WITHOUT ANY WARRANTY; without even the implied warranty of
# * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# * GNU Lesser General Public License for more details.
# * 
# * You should have received a copy of the GNU Lesser General Public License
# * along with this program.  If not, see <http://www.gnu.org/licenses/>.
# * 
# * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
# * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
# ******************************************************************************/
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	Af_cave = core.objectService.getObject(long(6035619))
	
	Ruffian1  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(1),
						float(-2.31808543205),float(-13.3325338364),float(-8.36793708801),
						float(0.999482870102),float(0),float(0.032154392451),float(0))
	
	Ruffian2  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(1),
						float(12.163775444),float(-13.6742763519),float(-4.50061416626),
						float(-0.593322277069),float(0),float(0.804964959621),float(0))
	
	Ruffian3  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(2),
						float(9.6674747467),float(-23.842508316),float(-27.5561332703),
						float(0.99931871891),float(0),float(-0.0369078740478),float(0))
	
	Ruffian4  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(2),
						float(-11.9123859406),float(-30.614402771),float(-26.6665840149),
						float(0.724915981293),float(0),float(0.68883728981),float(0))
	
	Ruffian5  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(4),
						float(50.1837425232),float(-46.5529289246),float(-52.8906211853),
						float(-0.425053924322),float(0),float(0.905167996883),float(0))
	
	Ruffian6  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(4),
						float(43.4518928528),float(-46.0730361938),float(-94.3979415894),
						float(0.963921666145),float(0),float(-0.2661857903),float(0))
	
	Ruffian7  = stcSvc.spawnObject('afarathu_cult_ruffian', 'corellia',  Af_cave.getCellByCellNumber(4),
						float(23.8808498383),float(-45.6798477173),float(-102.914619446),
						float(0.918368399143),float(0),float(0.395726561546),float(0))
		
	Follower1  = stcSvc.spawnObject('afarathu_cult_follower', 'corellia',  Af_cave.getCellByCellNumber(3),
						float(2.66539788246),float(-40.4499588013),float(-62.3544654846),
						float(-0.664468228817),float(0),float(0.747316539288),float(0))
	
	Follower2  = stcSvc.spawnObject('afarathu_cult_follower', 'corellia',  Af_cave.getCellByCellNumber(3),
						float(3.96364426613),float(-40.8625488281),float(-71.974571228),
						float(0.887980043888),float(0),float(-0.459882050753),float(0))
	
	Follower3  = stcSvc.spawnObject('afarathu_cult_follower', 'corellia',  Af_cave.getCellByCellNumber(3),
						float(-0.982226252556),float(-40.8204269409),float(-73.8085174561),
						float(0.967448890209),float(0),float(-0.253066599369),float(0))
	
	Follower4  = stcSvc.spawnObject('afarathu_cult_follower', 'corellia',  Af_cave.getCellByCellNumber(8),
						float(-95.3377914429),float(-73.681137085),float(-169.730529785),
						float(0.634666860104),float(0),float(0.772785902023),float(0))
			
	Savage1  = stcSvc.spawnObject('afarathu_savage', 'corellia',  Af_cave.getCellByCellNumber(5),
						float(-10.4163970947),float(-44.5707168579),float(-136.173324585),
						float(0.917002856731),float(0),float(0.398880660534),float(0))
	
	Savage2  = stcSvc.spawnObject('afarathu_savage', 'corellia',  Af_cave.getCellByCellNumber(5),
						float(-2.02453446388),float(-45.6916885376),float(-147.256774902),
						float(0.979026675224),float(0),float(0.20373275876),float(0))
	
	Savage3  = stcSvc.spawnObject('afarathu_savage', 'corellia',  Af_cave.getCellByCellNumber(7),
						float(-21.4365825653),float(-65.5355529785),float(-243.367904663),
						float(0.986020326614),float(0),float(0.166625484824),float(0))
	
	Savage4  = stcSvc.spawnObject('afarathu_savage', 'corellia',  Af_cave.getCellByCellNumber(7),
						float(-3.52972960472),float(-63.9877548218),float(-234.324752808),
						float(0.98648250103),float(0),float(-0.163866847754),float(0))
	
	Brute1  = stcSvc.spawnObject('afarathu_brute', 'corellia',  Af_cave.getCellByCellNumber(8),
						float(-83.352355957),float(-71.5322799683),float(-169.516235352),
						float(0.741839826107),float(0),float(0.670577168465),float(0))
	
	Brute2  = stcSvc.spawnObject('afarathu_brute', 'corellia',  Af_cave.getCellByCellNumber(8),
						float(-100.404327393),float(-72.739402771),float(-157.651901245),
						float(0.0384769365191),float(0),float(0.999259531498),float(0))
	
	Brute3  = stcSvc.spawnObject('afarathu_brute', 'corellia',  Af_cave.getCellByCellNumber(8),
						float(-110.747596741),float(-69.6207809448),float(-170.188323975),
						float(0.667285561562),float(0),float(0.744801998138),float(0))
	
	Brute4  = stcSvc.spawnObject('afarathu_brute', 'corellia',  Af_cave.getCellByCellNumber(11),
						float(-85.238319397),float(-101.389694214),float(-110.497489929),
						float(0.0889279991388),float(0),float(0.996038079262),float(0))
	
	Brute5  = stcSvc.spawnObject('afarathu_brute', 'corellia',  Af_cave.getCellByCellNumber(11),
						float(-92.392074585),float(-101.186233521),float(-112.037940979),
						float(0.0733542069793),float(0),float(0.997305929661),float(0))
	
	Leader = stcSvc.spawnObject('afarathu_cult_leader', 'corellia',  Af_cave.getCellByCellNumber(11),
						float(-90.65284729),float(-101.072135925),float(-104.325401306),
						float(0.0347759760916),float(0),float(0.999395132065),float(0))
	
	
	
	return
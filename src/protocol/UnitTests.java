/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package protocol;

import java.util.HashMap;
import java.util.Map;

import protocol.unitTests.BaselinesMessage;
import protocol.unitTests.ClientPermissionsMessage;
import protocol.unitTests.AccountFeatureBits;
import protocol.unitTests.AddIgnoreMessage;
import protocol.unitTests.AttributeListMessage;
import protocol.unitTests.CharacterCreationDisabled;
import protocol.unitTests.CharacterSheetResponseMessage;
import protocol.unitTests.ClientMfdStatusUpdateMessage;
import protocol.unitTests.ClientRandomNameResponse;
import protocol.unitTests.ClientVerifyAndLockNameResponse;
import protocol.unitTests.CmdSceneReady;
import protocol.unitTests.CmdStartScene;
import protocol.unitTests.CollectionServerFirstListResponse;
import engine.protocol.unitTests.AbstractUnitTest;
import engine.protocol.unitTests.AbstractUnitTests;
import engine.resources.common.CRC;

public abstract class UnitTests extends AbstractUnitTests {
	
	private static Map<Long, String> objectIdMap = null;
	
	static {
		Map<Integer, AbstractUnitTest> unitTests = getUnitTests();
		unitTests.put(CRC.StringtoCRC("AccountFeatureBits"), new AccountFeatureBits());
		unitTests.put(CRC.StringtoCRC("AddIgnoreMessage"), new AddIgnoreMessage());
		unitTests.put(CRC.StringtoCRC("AttributeListMessage"), new AttributeListMessage());
		unitTests.put(CRC.StringtoCRC("BaselinesMessage"), new BaselinesMessage());
		unitTests.put(CRC.StringtoCRC("CharacterCreationDisabled"), new CharacterCreationDisabled());
		unitTests.put(CRC.StringtoCRC("CharacterSheetResponseMessage"), new CharacterSheetResponseMessage());
		unitTests.put(CRC.StringtoCRC("ClientMfdStatusUpdate"), new ClientMfdStatusUpdateMessage());
		unitTests.put(CRC.StringtoCRC("ClientPermissionsMessage"), new ClientPermissionsMessage());
		unitTests.put(CRC.StringtoCRC("ClientRandomNameResponse"), new ClientRandomNameResponse());
		unitTests.put(CRC.StringtoCRC("ClientVerifyAndLockNameResponse"), new ClientVerifyAndLockNameResponse());
		unitTests.put(CRC.StringtoCRC("CmdSceneReady"), new CmdSceneReady());
		unitTests.put(CRC.StringtoCRC("CmdStartScene"), new CmdStartScene());
		unitTests.put(CRC.StringtoCRC("CollectionServerFirstListResponse"), new CollectionServerFirstListResponse());
	}
	
	public static Map<Long, String> getObjectIdMap() {
		if (objectIdMap == null) {
			objectIdMap = new HashMap<Long, String>();
		}
		
		return objectIdMap;
	}
	
}

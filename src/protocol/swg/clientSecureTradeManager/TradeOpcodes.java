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
package protocol.swg.clientSecureTradeManager;

public class TradeOpcodes {
	
	public static int AbortTradeMessage = 0x9CA80F98;
	public static int TradeCompleteMessage = 0xC542038B;
	public static int AddItemMessage = 0x1E8D1356;
	public static int RemoveItemMessage = 0x4417AF8B;
	public static int GiveMoneyMessage = 0xD1527EE8;
	public static int AcceptTransactionMessage = 0xB131CA17;
	public static int UnAcceptTransactionMessage = 0xE81E4382;
	public static int VerifyTradeMessage = 0x9AE247EE;
	public static int AddItemFailedMessage = 0x69D3E1D2;
	public static int BeginVerificationMessage = 0xE7491DF5;
}

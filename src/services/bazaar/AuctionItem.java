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
package services.bazaar;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.PrimaryKey;

import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;

@Entity(version=1)
public class AuctionItem implements IPersistent, Comparable<AuctionItem> {
	
	@PrimaryKey
	private long objectId;
	private SWGObject item;
	private long ownerId;
	private long vendorId;
	private long buyerId;
	private long offerToId;
	private int itemType;
	private String ownerName;
	private String bidderName = "";
	private String itemName;
	private String itemDescription;
	private String planet;
	private int price;
	private int proxyBid;
	private boolean auction;
	private String vuid;
	private int status;
	private boolean onBazaar;
	private long expireTime;
	private int auctionOptions;
	
	public static final int PREMIUM = 0x400;
	public static final int WITHDRAW = 0x800;
	public final static int FORSALE = 1;
	public final static int SOLD = 2;
	public final static int EXPIRED = 4;
	public final static int OFFERED = 5;
	public final static int RETRIEVED = 6;	
	
	@NotPersistent
	private Transaction txn;
	
	public AuctionItem() {
		
	}

	public AuctionItem(SWGObject item, long ownerId) {
		this.item = item;
		this.objectId = item.getObjectID();
		this.ownerId = ownerId;
	}

	
	public long getObjectId() {
		return objectId;
	}


	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}


	public long getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}


	public long getVendorId() {
		return vendorId;
	}


	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}


	public long getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}


	public long getOfferToId() {
		return offerToId;
	}


	public void setOfferToId(long offerToId) {
		this.offerToId = offerToId;
	}


	public int getItemType() {
		return itemType;
	}


	public void setItemType(int itemType) {
		this.itemType = itemType;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	public String getBidderName() {
		return bidderName;
	}


	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getProxyBid() {
		return proxyBid;
	}


	public void setProxyBid(int proxyBid) {
		this.proxyBid = proxyBid;
	}


	public boolean isAuction() {
		return auction;
	}


	public void setAuction(boolean auction) {
		this.auction = auction;
	}


	public String getVuid() {
		return vuid;
	}


	public void setVuid(String vuid) {
		this.vuid = vuid;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public boolean isOnBazaar() {
		return onBazaar;
	}


	public void setOnBazaar(boolean onBazaar) {
		this.onBazaar = onBazaar;
	}


	public long getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}


	public int getAuctionOptions() {
		return auctionOptions;
	}


	public void setAuctionOptions(int auctionOptions) {
		this.auctionOptions = auctionOptions;
	}

	@Override
	public void createTransaction(Environment env) {
		txn = env.beginTransaction(null, null);
	}

	@Override
	public Transaction getTransaction() {
		return txn;
	}

	public SWGObject getItem() {
		return item;
	}

	public void setItem(SWGObject item) {
		this.item = item;
	}

	public String getPlanet() {
		return planet;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}

	@Override
	public int compareTo(AuctionItem other) {
		if(getExpireTime() < other.getExpireTime())
			return -1;
		else if(getExpireTime() > other.getExpireTime())
			return 1;
		else
			return 0;
	}
	
}

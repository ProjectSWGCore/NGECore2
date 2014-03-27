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
package resources.visitors;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import engine.clientdata.VisitorInterface;

public class ProfessionTemplateVisitor implements VisitorInterface {


	private class RaceTemplate {
		private Vector<String> items;
		private String template;
		public RaceTemplate(String template) {
			this.template = template;
			this.items = new Vector<String>();
		}

		public Vector<String> getItems() {
			return items;
		}

		public String getTemplate() {
			return template;
		}
	}

	private CharsetDecoder charsetDecoder;

	private Vector<RaceTemplate> pItems;

	public ProfessionTemplateVisitor() {
		this.charsetDecoder = Charset.forName("US-ASCII").newDecoder();
		this.pItems = new Vector<RaceTemplate>();
	}

	public Vector<String> getItems(String template) {
		for(RaceTemplate rTemplate : pItems) {
			if (rTemplate.getTemplate().equals(template)) {
				return rTemplate.getItems();
			}
		}
		return null;
	}

	@Override
	public void notifyFolder(String str, int depth) throws Exception {
	}

	@Override
	public void parseData(String node, IoBuffer data, int depth, int size) {

		try {
			if (depth != 3)
				return;

			if (node.equals("PTMPNAME")) {
				String ptmpname = data.getString(charsetDecoder);

				pItems.add(new RaceTemplate(ptmpname));
				charsetDecoder.reset();
			} else if (node.equals("ITEM")){
				int index = pItems.size() - 1;
				data.skip(4);
				String item = data.getString(charsetDecoder);
				pItems.get(index).getItems().add(item);
				charsetDecoder.reset();
			}
		} catch(CharacterCodingException e) {
			e.printStackTrace();
		}
	}
}

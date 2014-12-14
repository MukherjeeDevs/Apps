package com.sample.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sample.model.Image;

public class XMLLoader {

	public static ArrayList<Image> loadXML(String username) {
		DOMParser parser = new DOMParser();

		InputStream is = XMLLoader.class.getResourceAsStream("Sample.xml");
		if (is == null) {
			System.out.println("Is NULL");
		} else {
			System.out.println("NOT NULL");
		}

		ArrayList<Image> images = new ArrayList<Image>();

		InputSource src = new InputSource(is); //
		try {
			parser.parse(src);
			Document doc = parser.getDocument();
			is.close();
			images = getRecentImages(doc);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// InputStreamReader isr = new InputStreamReader(is);
		// StringBuffer buff = new StringBuffer();
		//
		// int c = 0;
		// char[] cbuf = new char[16];
		// try {
		// while (true) {
		// c = isr.read(cbuf, 0, cbuf.length);
		// if (c == -1)
		// break;
		// buff.append(cbuf);
		// }
		// isr.close();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// int start = 0;
		// while (true) {
		// start = buff.indexOf("<image>", start);
		// if (start == -1)
		// break;
		// start = start + "<image>".length();
		//
		// int end = buff.indexOf("</image>", start);
		// if (end == -1)
		// break;
		// String url = buff.substring(start, end);
		// Image img = new Image();
		// img.setUrl(url);
		// images.add(img);
		// start = end + 1;
		// }

		return images;

	}

	private static ArrayList<Image> getRecentImages(Document doc) {
		ArrayList<Image> imgs = new ArrayList<Image>();
		NodeList list = doc.getElementsByTagName("image");

		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			String url = n.getTextContent().trim();

			Image img = new Image();
			img.setUrl(url);
			imgs.add(img);
		}

		return imgs;
	}
}

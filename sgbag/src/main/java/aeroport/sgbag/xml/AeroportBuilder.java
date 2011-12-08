package aeroport.sgbag.xml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Log4j
public class AeroportBuilder {
	Document document;
	Element racine;

	@Getter
	int kernelIndex;

	@Getter
	int viewIndex;

	public AeroportBuilder(String file) throws ParserConfigurationException,
			SAXException, IOException {

		kernelIndex = 0;
		viewIndex = 0;

		DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
		DocumentBuilder constructeur = null;
		constructeur = fabrique.newDocumentBuilder();

		File xml = new File(file);
		document = constructeur.parse(xml);
		racine = document.getDocumentElement();
		log.debug("Création du builder " + racine);
	}

	private Element getElementByName(String name) {
		NodeList kernelNodes = racine.getElementsByTagName(name);
		int tmp = 0;
		Node kernelNode;

		do {
			kernelNode = kernelNodes.item(tmp++);
			log.debug(kernelNode);
		} while (kernelNode.getNodeType() != Node.ELEMENT_NODE);

		Element kernelElement = (Element) kernelNode;
		log.debug(kernelElement);

		return kernelElement;
	}

	private Element getKernelElement() {
		return getElementByName("kernel");
	}

	private Element getViewElement() {
		return getElementByName("views");
	}

	private Object getObjectFromElement(Element element) {
		Object o = null;
		NodeList propertieNodes = element.getChildNodes();
		Class<?> c = null;
		try {
			c = Class.forName("aeroport.sgbag.kernel." + element.getTagName());
		} catch (ClassNotFoundException e) {
			return null;
		}

		try {
			o = c.newInstance();
		} catch (InstantiationException e1) {
			return null;
		} catch (IllegalAccessException e1) {
			return null;
		}

		for (int i = 0; i < propertieNodes.getLength(); i++) {
			if (propertieNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element propertieElem = (Element) propertieNodes.item(i);
				log.debug(propertieElem);

				Method setter = null;
				try {
					setter = c.getDeclaredMethod(
							"set" + propertieElem.getTagName(), int.class);
				} catch (SecurityException e) {
					return null;
				} catch (NoSuchMethodException e) {
					return null;
				}

				try {
					setter.invoke(
							o,
							Integer.parseInt(getTagValue(
									propertieElem.getTagName(), element)));
				} catch (NumberFormatException e) {
					return null;
				} catch (IllegalArgumentException e) {
					return null;
				} catch (IllegalAccessException e) {
					return null;
				} catch (InvocationTargetException e) {
					return null;
				}
			}
		}
		return o;
	}

	public Object getNextKernelObject() {
		Object o = null;
		Element kernelElement = getKernelElement();
		NodeList kernelNodes = kernelElement.getChildNodes();

		Node currentKernelNode;
		try {
			do {
				currentKernelNode = kernelNodes.item(kernelIndex++);
				log.debug(currentKernelNode);
			} while (currentKernelNode.getNodeType() != Node.ELEMENT_NODE);
		} catch (NullPointerException e) {
			return null;
		}

		Element currentKernelElement = (Element) currentKernelNode;
		log.debug(currentKernelElement);

		o = getObjectFromElement(currentKernelElement);

		kernelIndex++;

		return o;
	}

	public Object getNextViewObject() {
		Object o = null;
		Element viewElement = getViewElement();
		NodeList viewNodes = viewElement.getChildNodes();

		Node currentViewNode;
		do {
			currentViewNode = viewNodes.item(viewIndex++);
			log.debug(currentViewNode);
		} while (currentViewNode.getNodeType() != Node.ELEMENT_NODE);

		Element currentViewElement = (Element) currentViewNode;
		log.debug(currentViewElement);

		o = getObjectFromElement(currentViewElement);

		viewIndex++;

		return o;
	}

	public List<List<Object>> getAllObjects() {
		// TODO
		return null;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}

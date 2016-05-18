package com.ebay.raptor.promotion.xml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parse a xml string or file, then translate it into a class instance.
 * DomParser parser = new DomParser();
 * 
 * Document doc = parser.createDocument(xmlString);
 * T obj = parser.parseElementToObject(doc.getDocumentElement(), Class<T>);
 * 
 * @author lyan2
 * @email lyan@email.com
 */
public class DOMResolver {

	public Document createDocument(String xmlContent)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		ByteArrayInputStream input = new ByteArrayInputStream(
				xmlContent.getBytes("UTF-8"));
		return builder.parse(input);
	}

	public Document createDocument(File file)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(file);
	}

	/**
	 * Parse a document node into a class instance.
	 * @param node
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 */
	@SuppressWarnings("unchecked")
	public <T> T parseElementToObject(Node node, Class<T> clazz) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException, DOMException {
		T obj = null;
		
		if (!this.isInstanceType(clazz)) {
			if (clazz.isPrimitive()) {
				return (T)this.resolvePrimitiveValue(getElementText(node), clazz);
			} else if (clazz.isEnum()) {
				return (T)this.resolveEnumValue(getElementText(node), clazz);
			} else if (String.class.isAssignableFrom(clazz)) {
				return (T)getElementText(node);
			}
		} else {
			obj = clazz.newInstance();
		}
		
		BeanInfo info = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		
		resolveNodeAttributes(node, obj, descriptors);
		
		Map<String, Object> map = getElementNodes(node);
		if (!map.isEmpty()) {
			Set<String> elementNames = map.keySet();
			for (String name : elementNames.toArray(new String[0])) {
				Object element = map.get(name);
				if (List.class.isInstance(element)) {
					resolveChildElements(name, (List)element, obj, descriptors);
				} else {
					resolveChildElement(name, element, obj, descriptors);
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * Attribute node's value must be String or primitive types.
	 * @param node
	 * @param obj
	 * @param descriptors
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 */
	public void resolveNodeAttributes(Node node, Object obj, PropertyDescriptor[] descriptors) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException {
		NamedNodeMap attributes = node.getAttributes();
		for (int a = 0; a < attributes.getLength(); a++) {
			Node attr = attributes.item(a);
			for (PropertyDescriptor descriptor : descriptors) {
				if (descriptor.getName().equalsIgnoreCase(attr.getNodeName())) {
					Class<?> propertyType = descriptor.getPropertyType();
					Method setter = descriptor.getWriteMethod();
						
					if (propertyType.isPrimitive()) {
						// long, char, byte, short, int, float, double, boolean
						setPrimitiveValue(setter, obj, attr.getNodeValue(), propertyType);
					} else if (String.class.isAssignableFrom(propertyType)) {
						// string
						setter.invoke(obj, attr.getNodeValue());
					} else if (propertyType.isEnum()) {
						setEnumValue(setter, obj, attr.getNodeValue(), propertyType);
					}
					
					break;
				}
			}
		}
	}
	
	/**
	 * Resolve a child element as a property of obj.
	 * @param childElementName
	 * @param childElement
	 * @param obj
	 * @param descriptors
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public void resolveChildElement(String childElementName, Object childElement, Object obj, PropertyDescriptor[] descriptors) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, ArrayIndexOutOfBoundsException, InstantiationException, IntrospectionException {
		
		for (PropertyDescriptor descriptor : descriptors) {
			Class<?> propertyType = descriptor.getPropertyType();
			Method setter = descriptor.getWriteMethod();
			
			if (descriptor.getName().equalsIgnoreCase(childElementName)) {
				if (propertyType.isPrimitive()) {
					// long, char, byte, short, int, float, double, boolean
					Node node = (Node)childElement;
					setPrimitiveValue(setter, obj, getElementText(node), propertyType);
				} else if (String.class.isAssignableFrom(propertyType)) {
					// string
					Node node = (Node)childElement;
					setter.invoke(obj, getElementText(node));
				} else if (propertyType.isArray() || List.class.isAssignableFrom(propertyType)) {
					Map childElements = getElementNodes((Node)childElement);
					Iterator<String> iter = childElements.keySet().iterator();
					if (iter.hasNext()) {
						String elementName = iter.next();
						Object element = childElements.get(elementName);
						if (List.class.isInstance(element)) {
							resolveChildElements(elementName, (List)element, obj, descriptors);
						} else {
							resolveChildElement(elementName, element, obj, descriptors);
						}
					}
				} else if (propertyType.isEnum()) {
					setEnumValue(setter, obj, getElementText((Node)childElement), propertyType);
				} else {
					setter.invoke(obj, parseElementToObject((Node)childElement, propertyType));
				}
				
				break;
			} else if ((childElementName + "s").equalsIgnoreCase(descriptor.getName())
					|| (childElementName + "es").equalsIgnoreCase(descriptor.getName())
					|| (childElementName + "list").equalsIgnoreCase(descriptor.getName())) {
				 if (propertyType.isArray()) {
					Class<?> arrayGenericClass = propertyType.getComponentType();
					
					Object array = Array.newInstance(arrayGenericClass, 1);
					Array.set(array, 0, parseElementToObject((Node)childElement, arrayGenericClass));
					setter.invoke(obj, array);
					
				} else if (List.class.isAssignableFrom(propertyType)) {
					Method getter = descriptor.getReadMethod();
					ParameterizedType paramType = (ParameterizedType)getter.getGenericReturnType();
					
					Type[] types = paramType.getActualTypeArguments();
					Class<?> listGenericClass = null;
					if (types.length >= 1) {
						listGenericClass = (Class<?>)types[0];
					}
					
					List list = new ArrayList();
					if (listGenericClass != null) {
						list.add(parseElementToObject((Node)childElement, listGenericClass));
					}
					
					setter.invoke(obj, list);
				}
				
				break;
			}
		}
		
	}
	
	/**
	 * Resolve a list of element as the elements of obj's list or array property
	 * @param childElementName The element name of this list of element nodes.
	 * @param elements List of child element nodes
	 * @param obj
	 * @param descriptors
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws DOMException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public void resolveChildElements(String childElementName, List<Node> elements, Object obj, PropertyDescriptor[] descriptors) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, ArrayIndexOutOfBoundsException, InstantiationException, IntrospectionException {
		
		for (PropertyDescriptor descriptor : descriptors) {
			Class<?> propertyType = descriptor.getPropertyType();
			Method setter = descriptor.getWriteMethod();
			
			if (descriptor.getName().equalsIgnoreCase(childElementName)) {
				if (propertyType.isPrimitive()) {
					// long, char, byte, short, int, float, double, boolean
					setPrimitiveValue(setter, obj, getElementText(elements.get(0)), propertyType);
				} else if (String.class.isAssignableFrom(propertyType)) {
					// string
					setter.invoke(obj, getElementText(elements.get(0)));
				} else {
					setter.invoke(obj, parseElementToObject(elements.get(0), propertyType));
				}
				
				break;
			} else if ((childElementName + "s").equalsIgnoreCase(descriptor.getName())
					|| (childElementName + "es").equalsIgnoreCase(descriptor.getName())
					|| (childElementName + "list").equalsIgnoreCase(descriptor.getName())) {
				 if (propertyType.isArray()) {
					Class<?> arrayGenericClass = propertyType.getComponentType();
					
					Object array = Array.newInstance(arrayGenericClass, elements.size());
					int i = 0;
					
					Iterator<Node> iter = elements.iterator();
					while(iter.hasNext() && arrayGenericClass != null) {
						Node childNode = iter.next();
						Array.set(array, i++, parseElementToObject(childNode, arrayGenericClass));
					}
					setter.invoke(obj, array);
					
				} else if (List.class.isAssignableFrom(propertyType)) {
					Method getter = descriptor.getReadMethod();
					ParameterizedType paramType = (ParameterizedType)getter.getGenericReturnType();
					
					Type[] types = paramType.getActualTypeArguments();
					Class<?> listGenericClass = null;
					if (types.length >= 1) {
						listGenericClass = (Class<?>)types[0];
					}
					
					List list = new ArrayList();
					
					Iterator<Node> iter = elements.iterator();
					while(iter.hasNext() && listGenericClass != null) {
						Node childNode = iter.next();
						list.add(parseElementToObject(childNode, listGenericClass));
					}
					
					setter.invoke(obj, list);
				}
				
				break;
			}
		}
		
	}
	
	/**
	 * Get node's all element nodes. if several elements have the same name, then store them in a list. 
	 * @param node
	 * @return Map <nodeName, Node|List>
	 */
	private Map<String, Object> getElementNodes(Node node) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (node.hasChildNodes()) {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node childNode = list.item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					if (map.containsKey(childNode.getNodeName())) {
						// multiple nodes with the same name
						Object value = map.get(childNode.getNodeName());
						if (List.class.isInstance(value)) {
							((List<Node>)value).add(childNode);
						} else {
							List<Object> array = new ArrayList<Object>();
							array.add(map.get(childNode.getNodeName()));
							array.add(childNode);
							map.put(childNode.getNodeName(), array);
						}
					} else {
						map.put(childNode.getNodeName(), childNode);
					}
				}
			}
		}
		
		return map;
	}
	
	/**
	 * Get all the text node text content of an element.
	 * @param node
	 * @return
	 */
	private String getElementText(Node node) {
		StringBuffer text = new StringBuffer();
		NodeList list = node.getChildNodes();
		for (int j = 0; j < list.getLength(); j++) {
			Node textNode = list.item(j);
			if (textNode.getNodeType() == Node.TEXT_NODE) {
				text.append(list.item(j).getTextContent());
			}							
		}
		
		return text.toString();
	}
	
	/**
	 * Class except string, collection, primitive, number, boolean, character.
	 * @param type
	 * @return
	 */
	public boolean isInstanceType(Class<?> type) {
		if (String.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type) 
				|| type.isPrimitive() || type.isEnum() || Number.class.isAssignableFrom(type)
				|| Boolean.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Resolve string value to primitive type if this string could be parsed into primitive type.
	 * @param value
	 * @param type
	 * @return
	 */
	private Object resolvePrimitiveValue(String value, Class<?> type) {
		if (long.class.isAssignableFrom(type)) {
			return Long.parseLong(value);
		} else if (int.class.isAssignableFrom(type)) {
			return Integer.parseInt(value);
		} else if (double.class.isAssignableFrom(type)) {
			return Double.parseDouble(value);
		} else if (float.class.isAssignableFrom(type)) {
			return Float.parseFloat(value);
		} else if (short.class.isAssignableFrom(type)) {
			return Short.parseShort(value);
		} else if (boolean.class.isAssignableFrom(type)) {
			// only "true" will get true
			return Boolean.parseBoolean(value);
		} else if (byte.class.isAssignableFrom(type)) {
			return Byte.parseByte(value);
		} else if (char.class.isAssignableFrom(type)) {
			if (!value.isEmpty()) {
				return value.charAt(0);
			}
		}
		
		return null;
	}
	
	private <T> T resolveEnumValue(String value, Class<T> type) {
		for (T constant : type.getEnumConstants()) {
			if (constant.toString().equalsIgnoreCase(value)) {
				return constant;
			}
		}
		
		return null;
	}
	
	/**
	 * Cast String value to specified primitive type. Then call setter, set value as a property value of obj.
	 * @param setter
	 * @param obj
	 * @param value
	 * @param type
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void setPrimitiveValue(Method setter, Object obj, String value, Class<?> type) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result = resolvePrimitiveValue(value, type);
		if (result != null) {
			setter.invoke(obj, result);
		}
	}
	
	private void setEnumValue(Method setter, Object obj, String value, Class<?> type) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result = resolveEnumValue(value, type);
		if (result != null) {
			setter.invoke(obj, result);
		}
	}
}

/* ListModelConverter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Fri Dec  1 16:55:36     2006, Created by Henri Chen
}}IS_NOTE

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zkbind.converter.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.ListModelSet;
import org.zkoss.zul.impl.GroupsListModel;

/**
 * The {@link Converter} implementation for converting collection to ListModel and vice versa.
 *
 * @author Henri Chen
 */
public class ListModelConverter implements Converter, java.io.Serializable {
	private static final long serialVersionUID = 201108171744L;
	/** Convert a Set, Map, List, Object[], Enum, or other kind of ListModel to associated {@link ListModel}.
	 * @param val must be instanceof Set, Map, List, Object[], Enum Class, or other kind of ListModel implementation.
	 * @param comp associated component
	 * @param ctx bind context
	 */
	public Object coerceToUi(Object val, Component comp, BindContext ctx) {
		if (val == null) {
			val = new ArrayList();
		}
		if (val instanceof ListModel) {
			return val;
		} else if (val instanceof Set) {
			return new ListModelSet((Set)val, true);
		} else if (val instanceof List) {
			return new ListModelList((List)val, true);
		} else if (val instanceof Map) {
			return new ListModelMap((Map)val, true);
		} else if (val instanceof Object[]) {
			return new ListModelArray((Object[]) val, true);
		} else if ((val instanceof Class) && Enum.class.isAssignableFrom((Class)val)) {
			return new ListModelArray((Object[]) ((Class)val).getEnumConstants(), true);
		} else if (val instanceof GroupsModel) { //feature#2866506: Data Binding shall support GroupsModel with Listbox/Grid
			return new GroupsListModel((GroupsModel) val);
		} else {
			throw new UiException("Expects java.util.Set, java.util.List, java.util.Map, Object[], Enum Class, GroupsModel, or ListModel only. "+val.getClass());
		}
	}

	/** Convert a {@link ListModel} to Set, Map, List, or ListModel (itself).
	 * @param val must be ListModelSet, ListModelList, ListModelMap, or other kind of ListModel
	 * @param comp associated component
	 * @param ctx bind context
	 */
	public Object coerceToBean(Object val, Component comp, BindContext ctx) {
		if (val == null) {
			throw new NullPointerException("val");
		}
		if (val instanceof ListModelSet) {
			return ((ListModelSet)val).getInnerSet();
		} else if (val instanceof ListModelList) {
			return ((ListModelList)val).getInnerList();
		} else if (val instanceof ListModelMap) {
			return ((ListModelMap)val).getInnerMap();
		} else if (val instanceof ListModelArray) {
			return ((ListModelArray)val).getInnerArray();
		} else if (val instanceof ListModel) {
			return val;
		} else {
			throw new UiException("Expects ListModelSet, ListModelList, ListModelMap, or ListModel only."+val.getClass());
		}
	}
}

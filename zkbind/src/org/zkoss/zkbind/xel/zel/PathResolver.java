/* PathResolver.java

	Purpose:
		
	Description:
		
	History:
		Sep 8, 2011 9:05:13 AM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.xel.zel;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.zkoss.zel.ELContext;
import org.zkoss.zel.ELException;
import org.zkoss.zel.ELResolver;
import org.zkoss.zel.PropertyNotFoundException;
import org.zkoss.zel.PropertyNotWritableException;
import org.zkoss.zel.impl.parser.AstIdentifier;
import org.zkoss.zel.impl.parser.AstValue;
import org.zkoss.zel.impl.parser.Node;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.impl.Path;

/**
 * Handle dot series path when evaluating expression.
 * @author henri
 *
 */
public class PathResolver extends ELResolver {
	private Stack<Integer> _numOfKids = new Stack<Integer>();
	private Stack<List<String>> _paths = new Stack<List<String>>();
	
	private String toNodeString(ELContext ctx) {
		final Node node0 = (Node) ctx.getContext(Node.class);
		return BindELContext.toNodeString(node0, new StringBuffer());
	}
	
	@Override
	public Object getValue(ELContext ctx, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
        if (ctx == null) {
            throw new NullPointerException();
        }
        if (base == null) { //init
        	Integer numOfKids = (Integer) ctx.getContext(AstValue.class);
        	if (numOfKids == null) {
        		numOfKids = (Integer) ctx.getContext(AstIdentifier.class);
        	}
			_numOfKids.push(numOfKids);
			_paths.push(new ArrayList<String>());
        }
        Integer numOfKids = _numOfKids.pop();
        List<String> path = _paths.pop();
        
    	//maintain the number of kids
    	int nums = numOfKids.intValue() - 1;
    	numOfKids = new Integer(nums);
    	ctx.putContext(Integer.class, numOfKids);

    	//maintain the form path field
    	path.add(toNodeString(ctx));
    	ctx.putContext(Path.class, path);

        if (nums > 0) { //still more property
        	_numOfKids.push(numOfKids);
        	_paths.push(path);
        }
        return null;
	}

	@Override
	public Class<?> getType(ELContext ctx, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
        if (ctx == null) {
            throw new NullPointerException();
        }

        Integer numOfKids = _numOfKids.pop();
        List<String> path = _paths.pop();
        
    	//maintain the number of kids
    	int nums = numOfKids.intValue() - 1;
    	numOfKids = new Integer(nums);
    	ctx.putContext(Integer.class, numOfKids);

    	//maintain the form path field
    	path.add(toNodeString(ctx));
    	ctx.putContext(Path.class, path);
    	
    	return null;

	}

	@Override
	public void setValue(ELContext ctx, Object base, Object property,
			Object value) throws NullPointerException,
			PropertyNotFoundException, PropertyNotWritableException,
			ELException {
        if (ctx == null) {
            throw new NullPointerException();
        }
		
        //#getType() will maintain number of kids and path field, just let go
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return false;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
			Object base) {
		return null;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return null;
	}
}

/* AccessInfo.java

	Purpose:
		
	Description:
		
	History:
		Jul 28, 2011 5:17:48 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.Binding;

/**
 * Represent an load/save binding expression 
 * <p>e.g.
 * "load = property (before|after) command"
 * "save = property (before|after) command"
 * </p>
 * @author henri
 *
 */
public class AccessInfo {
	final String commandName; //command name
	final ExpressionX property; //property expression
	final boolean after; //before command or after command
	
	public AccessInfo(ExpressionX property, boolean after, String command) {
		this.property = property;
		this.after = after;
		this.commandName = command;
	}
	public String getCommandName() {
		return this.commandName;
	}
	public boolean isAfter() {
		return this.after;
	}
	public ExpressionX getProperty() {
		return this.property;
	}

	//tokenize
	private static final Pattern ACCESS_PATTERN = Pattern.compile("\\s+|[^\\s\"']+|\"[^\"]*\"|'[^']*'|'[^']*");
	public static AccessInfo create(Binding binding, String accessScript, Class expectedType) {
		final Binder binder = binding.getBinder();
		final Matcher matcher = ACCESS_PATTERN.matcher(accessScript);
		final StringBuffer property = new StringBuffer();
		final StringBuffer command = new StringBuffer();
		Boolean after = null;
		while (matcher.find()) {
			final String token = matcher.group();
			if (after != null) {
				command.append(token);
			} else {
				if ("after".equalsIgnoreCase(token)) {
					after = Boolean.TRUE;
				} else if ("before".equalsIgnoreCase(token)) {
					after = Boolean.FALSE;
				} else {
					property.append(token);
				}
			}
		}
		if (property.length() <= 0) {
			throw new IllegalArgumentException(accessScript);
		}
		final BindEvaluatorX eval = binder.getEvaluatorX();
		final String script = command.toString().trim();
		final ExpressionX comm = script.length() > 0 ? eval.parseExpressionX(null, script, String.class) : null;
		final String commandName = comm == null ? null : (String) eval.getValue((BindContext)null, (Component)null, comm);
		if (comm != null && !isLiteralString(script, commandName)) {
			throw new IllegalArgumentException("command must be a literal text rather than an expression: " + comm.getExpressionString());
		}
		final BindContext ctx = commandName != null ? null : 
				new BindContextImpl(binder, binding, false, null, null, null, null); //only prompt loading shall track dependency
		final ExpressionX prop = eval.parseExpressionX(ctx, property.toString().trim(), expectedType);
		final boolean af = after != null ? after.booleanValue() : false;
		return new AccessInfo(prop, af, commandName);
	}
	
	private static boolean isLiteralString(String script, String evaled) {
		return script.equals("'"+evaled+"'") || script.equals("\""+evaled+"\"");
	}
}

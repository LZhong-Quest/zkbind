package org.zkoss.bind.examples.spring.util;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("messagePool")
@Scope("desktop")
public class MessagePool extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

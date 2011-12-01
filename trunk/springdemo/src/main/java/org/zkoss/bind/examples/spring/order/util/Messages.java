package org.zkoss.bind.examples.spring.order.util;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("messages")
@Scope("desktop")
public class Messages extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	

}

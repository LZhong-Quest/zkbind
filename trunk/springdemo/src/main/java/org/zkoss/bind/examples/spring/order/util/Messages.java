package org.zkoss.bind.examples.spring.order.util;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("messagePool")
@Scope("desktop")
public class Messages extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private final String NOT_NULL_MESSAGE = "must not null";
	private final String NOT_POSITIVE_MESSAGE = "must large than 0";
	

}

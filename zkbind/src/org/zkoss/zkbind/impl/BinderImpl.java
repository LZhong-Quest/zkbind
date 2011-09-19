/* BinderImpl.java

	Purpose:
		
	Description:
		
	History:
		Jul 29, 2011 6:08:51 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zkoss.lang.Classes;
import org.zkoss.lang.Strings;
import org.zkoss.lang.reflect.Fields;
import org.zkoss.xel.ExpressionX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Binder;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.PhaseListener;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.converter.FormatedDateConverter;
import org.zkoss.zkbind.converter.ObjectBooleanConverter;
import org.zkoss.zkbind.converter.UriConverter;
import org.zkoss.zkbind.sys.BindEvaluatorX;
import org.zkoss.zkbind.sys.Binding;
import org.zkoss.zkbind.sys.CommandBinding;
import org.zkoss.zkbind.sys.LoadBinding;
import org.zkoss.zkbind.sys.LoadFormBinding;
import org.zkoss.zkbind.sys.LoadPropertyBinding;
import org.zkoss.zkbind.sys.SaveFormBinding;
import org.zkoss.zkbind.sys.SavePropertyBinding;
import org.zkoss.zkbind.sys.tracker.Tracker;
import org.zkoss.zkbind.tracker.impl.TrackerImpl;
import org.zkoss.zkbind.xel.zel.BindELContext;

/**
 * Implementation of Binder.
 * @author henri
 *
 */
public class BinderImpl implements Binder {
	private static final Map<String, Converter> CONVERTERS = new HashMap<String, Converter>();
	private static final Map<String, Object> RENDERERS = new HashMap<String, Object>();
	static {
		initConverter();
	}

	//TODO can be defined in property-library
	private static void initConverter() {
		//TODO use library-property to initialize default user converters
		CONVERTERS.put("objectBoolean", new ObjectBooleanConverter());
		CONVERTERS.put("formatedDate", new FormatedDateConverter());
		CONVERTERS.put("uri", new UriConverter());
	}
	
	//control keys
	public static final String BINDING = "$BINDING$"; //a binding
	public static final String BINDER = "$BINDER$"; //the binder
	public static final String BINDCTX = "$BINDCTX$"; //bind context
	public static final String VAR = "$VAR$"; //variable name in a collection
	public static final String VM = "$VM$"; //the associated view model
	public static final String QUE = "$QUE$"; //the associated event queue name
	public static final String NOTIFYS = "$NOTIFYS$"; //changed properties to be notified
	public static final String VALIDATES = "$VALIDATES$"; //properties to be validated
	
	//System Annotation, see lang-addon.xml
	private static final String SYSBIND = "$SYSBIND$"; //system binding annotation name
	private static final String RENDERER = "$R$"; //system renderer for binding
	private static final String TEMPLATE = "$T$"; //possible template field for binding
	private static final String LOADEVENT = "$LE$"; //load trigger event
	private static final String SAVEEVENT = "$SE$"; //save trigger event
	private static final String ACCESS = "$A$"; //access type (load|save|both), load is default
	private static final String CONVERTER = "$C$"; //system converter for binding
	
	//Command lifecycle result
	private static final int SUCCESS = 0;
	private static final int FAIL_CONFIRM = 1;
	private static final int FAIL_VALIDATE = 2;
	
	private Component _rootComp;
	private BindEvaluatorX _eval;
	private PhaseListener _phaseListener;
	private Tracker _tracker;
	
	private final Map<Component, Map<String, List<Binding>>> _bindings; //comp -> (evtnm | _fieldExpr | formid) -> bindings
	private final Map<String, List<LoadFormBinding>> _loadFormPromptBindings; //comp+formid -> bindings (load form _prompt)
	private final Map<String, List<LoadFormBinding>> _loadFormAfterBindings; //command -> bindings (load form after command)
	private final Map<String, List<SaveFormBinding>> _saveFormAfterBindings; //command -> bindings (save form after command)
	private final Map<String, List<LoadFormBinding>> _loadFormBeforeBindings; //command -> bindings (load form before command)
	private final Map<String, List<SaveFormBinding>> _saveFormBeforeBindings; //command -> bindings (save form before command)
	private final Map<String, List<LoadPropertyBinding>> _loadPromptBindings; //comp+_fieldExpr -> bindings (load _prompt | load on property change)
	private final Map<String, List<LoadPropertyBinding>> _loadEventBindings; //comp+evtnm -> bindings (load on event)
	private final Map<String, List<SavePropertyBinding>> _saveEventBindings; //comp+evtnm -> bindings (save on event)
	private final Map<String, List<LoadPropertyBinding>> _loadAfterBindings; //command -> bindings (load after command)
	private final Map<String, List<SavePropertyBinding>> _saveAfterBindings; //command -> bindings (save after command)
	private final Map<String, List<LoadPropertyBinding>> _loadBeforeBindings; //command -> bindings (load before command)
	private final Map<String, List<SavePropertyBinding>> _saveBeforeBindings; //command -> bindings (save before command)
	private final Map<String, CommandEventListener> _listenerMap; //comp+evtnm -> eventlistener
	private final String _quename;
	private final String _quescope;
	
	public BinderImpl(Component comp, Object vm, String qname, String qscope) {
		_rootComp = comp;
		_bindings = new HashMap<Component, Map<String, List<Binding>>>();
		_loadFormPromptBindings = new HashMap<String, List<LoadFormBinding>>();
		_loadFormAfterBindings = new HashMap<String, List<LoadFormBinding>>();
		_saveFormAfterBindings = new HashMap<String, List<SaveFormBinding>>();
		_loadFormBeforeBindings = new HashMap<String, List<LoadFormBinding>>();
		_saveFormBeforeBindings = new HashMap<String, List<SaveFormBinding>>();
		_loadPromptBindings = new HashMap<String, List<LoadPropertyBinding>>();
		_loadEventBindings = new HashMap<String, List<LoadPropertyBinding>>();
		_saveEventBindings = new HashMap<String, List<SavePropertyBinding>>();
		_loadAfterBindings = new HashMap<String, List<LoadPropertyBinding>>();
		_saveAfterBindings = new HashMap<String, List<SavePropertyBinding>>();
		_loadBeforeBindings = new HashMap<String, List<LoadPropertyBinding>>();
		_saveBeforeBindings = new HashMap<String, List<SavePropertyBinding>>();
		_listenerMap = new HashMap<String, CommandEventListener>();
		_quename = qname != null && !qname.isEmpty() ? qname : BinderImpl.QUE;
		_quescope = qscope != null && !qscope.isEmpty() ? qscope : EventQueues.DESKTOP;

		//initial associated view model
		setViewModel(vm);
		
		//subscribe change listener
		subscribeChangeListener(_quename, _quescope, new EventListener() {
			public void onEvent(Event event) throws Exception {
				final PropertyChangeEvent evt = (PropertyChangeEvent) event;
				BinderImpl.this.loadOnPropertyChange(evt.getBase(), evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}
		});
	}
	
	//called when onPropertyChange is fired to the subscribed event queue
	private void loadOnPropertyChange(Object base, String prop, Object oldValue, Object newValue) {
		final Tracker tracker = getTracker();
		final Set<LoadBinding> bindings = tracker.getLoadBindings(base, prop);
		for(LoadBinding binding : bindings) {
			final BindContext ctx = new BindContextImpl(this, binding, false, null, binding.getComponent(), null);
			binding.load(ctx);
		}
	}
	
	public void setViewModel(Object vm) {
		_rootComp.setAttribute(BinderImpl.VM, vm);
	}
	
	public Object getViewModel() {
		return _rootComp.getAttribute(BinderImpl.VM);
	}
	
	//Note: assume system converter is state-less
	public Converter getConverter(String name) {
		Converter converter = CONVERTERS.get(name);
		if (converter == null && name.indexOf('.') > 0) { //might be a class path
			try {
				converter = (Converter) Classes.newInstanceByThread(name);
				CONVERTERS.put(name, converter); //assume converter is state-less
			} catch (Exception e) {
				throw UiException.Aide.wrap(e);
			}
		}
		if (converter == null) {
			throw new UiException("Cannot find the named converter:" + name);
		}
		return converter;
	}
	
	//Note: assume system renderer is state-less 
	protected Object getRenderer(String name) {
		Object renderer = RENDERERS.get(name);
		if (renderer == null && name.indexOf('.') > 0) { //might be a class path
			try {
				renderer = Classes.newInstanceByThread(name);
				RENDERERS.put(name, renderer); //assume renderer is state-less
			} catch (IllegalAccessException e) {
				throw UiException.Aide.wrap(e);
			} catch (Exception e) {
				//ignore
			}
		}
		return renderer;
	}

	public BindEvaluatorX getEvaluatorX() {
		if (_eval == null) {
			_eval = new BindEvaluatorXImpl(null, org.zkoss.zkbind.xel.BindXelFactory.class);
		}
		return _eval;
	}

	public void addFormBindings(Component comp, String idScript, 
		String[] loadExprs, String[] saveExprs, String[] confirmExprs, String validate, Map args) {
		final BindEvaluatorX eval = getEvaluatorX();
		final ExpressionX idExpr = eval.parseExpressionX(null, idScript, String.class);
		final String id = (String) eval.getValue(null, comp, idExpr);
		final Form form = new FormImpl(id);
		comp.setAttribute(id, form);
		for(String loadExpr : loadExprs) {
			addLoadFormBinding(comp, form, loadExpr, args);
		}
		for(String saveExpr : saveExprs) {
			addSaveFormBinding(comp, form, saveExpr, validate, args);
		}
	}
	
	private void addLoadFormBinding(Component comp, Form form, String loadExpr, Map args) {
		final LoadFormBindingImpl binding = new LoadFormBindingImpl(this, comp, form, loadExpr, args);
		final String uuid = comp.getUuid(); 
		final String attr = form.getId();
		addBinding(comp, attr, binding);
		
		final String command = binding.getCommandName();
		if (command == null) {
			final String compId = dualId(uuid, attr);
			addLoadFormPromptBinding(compId, binding);
		} else {
			final boolean after = binding.isAfter();
			if (after) {
				addLoadFormAfterBinding(command, binding);
			} else {
				addLoadFormBeforeBinding(command, binding);
			}
		}
	}

	private void addSaveFormBinding(Component comp, Form form, String saveExpr, String validate, Map args) {
		//register event Command listener 
		final SaveFormBindingImpl binding = new SaveFormBindingImpl(this, comp, form, saveExpr, validate, args);
		final String formid = form.getId();
		final String command = binding.getCommandName();
		if (command == null) {
			throw new UiException("Form "+formid+" must be saved by a Command: "+ binding.getPropertyString());
		}
		addBinding(comp, formid, binding);
		
		final boolean after = binding.isAfter();
		if (after) {
			addSaveFormAfterBinding(command, binding);
		} else {
			addSaveFormBeforeBinding(command, binding);
		}
	}

	private boolean existFormBinding() {
		return !_loadFormPromptBindings.isEmpty() //comp+formid -> bindings (load form _prompt)
			|| !_loadFormAfterBindings.isEmpty() //command -> bindings (load form after command)
			|| !_saveFormAfterBindings.isEmpty() //command -> bindings (save form after command)
			|| !_loadFormBeforeBindings.isEmpty() //command -> bindings (load form before command)
			|| !_saveFormBeforeBindings.isEmpty(); //command -> bindings (save form before command)
	}

	public void addPropertyBinding(Component comp, String attr, String[] loadExprs, String[] saveExprs, String converter, String validate, Map args) {
		if (Strings.isBlank(converter)) {
			converter = getSystemConverter(comp, attr);
			if (converter != null) {
				converter = "'"+converter+"'";
			}
		}
		for(String loadExpr : loadExprs) {
			addLoadBinding(comp, attr, loadExpr, converter, args);
		}
		for(String saveExpr : saveExprs) {
			addSaveBinding(comp, attr, saveExpr, converter, validate, args);
		}
		initRendererIfAny(comp);
	}

	private String getSystemConverter(Component comp, String attr) {
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(attr, BinderImpl.SYSBIND);
		if (ann != null) {
			final Map attrs = ann.getAttributes(); //(tag, tagExpr)
			return (String) attrs.get(BinderImpl.CONVERTER); //system converter if exists
		}
		return null;
	}
	
	private Template lookupTemplate(Component comp, String templateField) {
		Template tm = comp.getTemplate("model");
		if (tm == null && templateField != null) { //try template class
			try {
				final Component kid = (Component) Fields.get(comp, templateField);
				tm = kid.getTemplate("model");
			} catch (NoSuchMethodException e) {
				throw UiException.Aide.wrap(e);
			}
		}
		return tm;
	}
	
	private void initRendererIfAny(Component comp) {
		//check if exists template
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(BinderImpl.SYSBIND);
		final Map attrs = ann != null ? ann.getAttributes() : null; //(tag, tagExpr)
		final String templateField = attrs == null ? null : (String) attrs.get(BinderImpl.TEMPLATE); //possible template field 
		final Template tm = lookupTemplate(comp, templateField);
		if (tm == null) { //no template
			return;
		}
		
		final Object installed = comp.getAttribute(BinderImpl.VAR);
		if (installed != null) { //renderer was set already init
			return;
		}
		
		final String var = (String) tm.getParameters().get("var");
		final String varnm = var == null ? "each" : var; //var is not specified, default to "each"
		comp.setAttribute(BinderImpl.VAR, varnm);
		
		if (attrs != null) {
			final String rendererName = (String) attrs.get(BinderImpl.RENDERER); //renderer if any
			//setup renderer
			if (rendererName != null) { //there was system renderer
				final String[] values = rendererName.split("=", 2);
				if (values != null) {
					final Object renderer = getRenderer(values[1]);
					//check if user has set a renderer
					Object old = null;
					try {
						old = Fields.get(comp, values[0]);
					} catch (NoSuchMethodException e1) {
						//ignore
					}
					if (old == null) {
						try {
							Fields.set(comp, values[0], renderer, false);
						} catch (Exception  e) {
							throw UiException.Aide.wrap(e);
						}
					}
				}
			}
		}
	}
	
	private void addLoadBinding(Component comp, String attr, String loadExpr, String converter, Map args) {
		//check attribute _accessInfo natural characteristics to register Command event listener 
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(attr, BinderImpl.SYSBIND);
		String evtnm = null;
		if (ann != null) {
			final Map attrs = ann.getAttributes(); //(tag, tagExpr)
			final String rw = (String) attrs.get(BinderImpl.ACCESS); //_accessInfo right, "both|save|load", default to load
			if (rw != null && !"both".equals(rw) && !"load".equals(rw)) { //save only, skip
				return;
			}
			evtnm = (String) attrs.get(BinderImpl.LOADEVENT); //check trigger event for loading
		}
		
		LoadPropertyBindingImpl binding = new LoadPropertyBindingImpl(this, comp, attr, loadExpr, converter, args);
		final String uuid = comp.getUuid();
		addBinding(comp, attr, binding);
		
		final String command = binding.getCommandName();
		if (command == null) {
			if (evtnm != null) { //special case, load on an event
				addEventCommandListenerIfNotExists(comp, evtnm, null); //local command
				final String evtId = dualId(uuid, evtnm);
				addLoadEventBinding(comp, evtId, binding);
			}
			final String compId = dualId(uuid, attr);
			addLoadPromptBinding(comp, compId, binding);
		} else {
			final boolean after = binding.isAfter();
			if (after) {
				addLoadAfterBinding(command, binding);
			} else {
				addLoadBeforeBinding(command, binding);
			}
		}
	}
	
	private void addSaveBinding(Component comp, String attr, String saveExpr, String converter, String validate, Map args) {
		//check attribute _accessInfo natural characteristics to register Command event listener 
		final ComponentCtrl compCtrl = (ComponentCtrl) comp;
		final Annotation ann = compCtrl.getAnnotation(attr, BinderImpl.SYSBIND);
		String evtnm = null;
		if (ann != null) {
			final Map attrs = ann.getAttributes(); //(tag, tagExpr)
			final String rw = (String) attrs.get(BinderImpl.ACCESS); //_accessInfo right, "both|save|load", default to load
			if (!"both".equals(rw) && !"save".equals(rw)) { //load only, skip
				return;
			}
			evtnm = (String) attrs.get(BinderImpl.SAVEEVENT); //check trigger event for saving
		}
		if (evtnm == null) { //no trigger event...
			return;
		}

		final SavePropertyBindingImpl binding = new SavePropertyBindingImpl(this, comp, attr, saveExpr, converter, validate, args);
		final String uuid = comp.getUuid();
		addBinding(comp, attr, binding);
		
		final String command = binding.getCommandName();
		if (command == null) { //save on event
			addEventCommandListenerIfNotExists(comp, evtnm, null); //local command
			final String evtId = dualId(uuid, evtnm);
			addSavePromptBinding(comp, evtId, binding);
		} else {
			final boolean after = binding.isAfter();
			if (after) {
				addSaveAfterBinding(command, binding);
			} else {
				addSaveBeforeBinding(command, binding);
			}
		}
	}
	
	private void addLoadFormPromptBinding(String compId, LoadFormBinding binding) {
		List<LoadFormBinding> bindings = _loadFormPromptBindings.get(compId); 
		if (bindings == null) {
			bindings = new ArrayList<LoadFormBinding>();
			_loadFormPromptBindings.put(compId, bindings);
		}
		bindings.add(binding);
	}
	
	private void addLoadFormBeforeBinding(String command, LoadFormBinding binding) {
		List<LoadFormBinding> bindings = _loadFormBeforeBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<LoadFormBinding>();
			_loadFormBeforeBindings.put(command, bindings);
		}
		bindings.add(binding);
	}
	
	private void addLoadFormAfterBinding(String command, LoadFormBinding binding) {
		List<LoadFormBinding> bindings = _loadFormAfterBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<LoadFormBinding>();
			_loadFormAfterBindings.put(command, bindings);
		}
		bindings.add(binding);
	}

	private void addSaveFormBeforeBinding(String command, SaveFormBinding binding) {
		List<SaveFormBinding> bindings = _saveFormBeforeBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<SaveFormBinding>();
			_saveFormBeforeBindings.put(command, bindings);
		}
		bindings.add(binding);
	}
	
	private void addSaveFormAfterBinding(String command, SaveFormBinding binding) {
		List<SaveFormBinding> bindings = _saveFormAfterBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<SaveFormBinding>();
			_saveFormAfterBindings.put(command, bindings);
		}
		bindings.add(binding);
	}

	private void addLoadEventBinding(Component comp, String evtId, LoadPropertyBinding binding) {
		List<LoadPropertyBinding> bindings = _loadEventBindings.get(evtId); 
		if (bindings == null) {
			bindings = new ArrayList<LoadPropertyBinding>();
			_loadEventBindings.put(evtId, bindings);
		}
		bindings.add(binding);
	}
	private void addLoadPromptBinding(Component comp, String compId, LoadPropertyBinding binding) {
		List<LoadPropertyBinding> bindings = _loadPromptBindings.get(compId); 
		if (bindings == null) {
			bindings = new ArrayList<LoadPropertyBinding>();
			_loadPromptBindings.put(compId, bindings);
		}
		bindings.add(binding);
	}
	
	private void addLoadBeforeBinding(String command, LoadPropertyBinding binding) {
		List<LoadPropertyBinding> bindings = _loadBeforeBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<LoadPropertyBinding>();
			_loadBeforeBindings.put(command, bindings);
		}
		bindings.add(binding);
	}
	
	private void addLoadAfterBinding(String command, LoadPropertyBinding binding) {
		List<LoadPropertyBinding> bindings = _loadAfterBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<LoadPropertyBinding>();
			_loadAfterBindings.put(command, bindings);
		}
		bindings.add(binding);
	}

	private void addSavePromptBinding(Component comp, String compId, SavePropertyBinding binding) {
		List<SavePropertyBinding> bindings = _saveEventBindings.get(compId); 
		if (bindings == null) {
			bindings = new ArrayList<SavePropertyBinding>();
			_saveEventBindings.put(compId, bindings);
		}
		bindings.add(binding);
	}
	
	private void addSaveBeforeBinding(String command, SavePropertyBinding binding) {
		List<SavePropertyBinding> bindings = _saveBeforeBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<SavePropertyBinding>();
			_saveBeforeBindings.put(command, bindings);
		}
		bindings.add(binding);
	}
	
	private void addSaveAfterBinding(String command, SavePropertyBinding binding) {
		List<SavePropertyBinding> bindings = _saveAfterBindings.get(command);
		if (bindings == null) {
			bindings = new ArrayList<SavePropertyBinding>();
			_saveAfterBindings.put(command, bindings);
		}
		bindings.add(binding);
	}

	public void addCommandBinding(Component comp, String evtnm, String commandExpr, Map args) {
		final CommandBindingImpl binding = new CommandBindingImpl(this, comp, evtnm, commandExpr, args);
		addBinding(comp, evtnm, binding);
		addEventCommandListenerIfNotExists(comp, evtnm, binding);
	}
	
	//associate event to CommandBinding
	private void addEventCommandListenerIfNotExists(Component comp, String evtnm, CommandBinding command) {
		final String evtId = dualId(comp.getUuid(), evtnm);
		CommandEventListener listener = _listenerMap.get(evtId);
		if (listener == null) {
			listener = new CommandEventListener();
			comp.addEventListener(evtnm, listener);
		}
		listener.addCommand(command);
	}
	
	private void removeEventCommandListenerIfExists(Component comp, String evtnm) {
		final String evtId = dualId(comp.getUuid(), evtnm);
		final CommandEventListener listener = _listenerMap.remove(evtId);
		if (listener != null) {
			comp.removeEventListener(evtnm, listener);
		}
	}

	private class CommandEventListener implements EventListener { //event used to trigger command
		private boolean _prompt = false;
		private CommandBinding _commandBinding;
		private void addCommand(CommandBinding command) {
			if (!_prompt && command == null) {
				_prompt = true;
			} else {
				_commandBinding = command;
			}
		}
		
		public void onEvent(Event event) throws Exception {
			//command need to be confirmed shall be execute first!
			//must sort the command sequence?
			final Component comp = event.getTarget();
			final String evtnm = event.getName();
			final Set<Property> notifys = new LinkedHashSet<Property>();
			int result = SUCCESS; //command execution result, default to success
			String command = null;
			if (_commandBinding != null) {
				final BindEvaluatorX eval = getEvaluatorX();
				command = (String) eval.getValue(null, comp, ((CommandBindingImpl)_commandBinding).getCommand());
				final Map<String, Object> args = evalArgs(comp, _commandBinding.getArgs());
				result = BinderImpl.this.doCommand(comp, command, evtnm, args, notifys, false);
			}
			//check confirm
			switch(result) {
				case BinderImpl.FAIL_CONFIRM:
					BinderImpl.this.doFailConfirm();
					break;
				case BinderImpl.FAIL_VALIDATE:
					fireNotifyChanges(notifys); //still has to go through notifyChange to show error message
					return;
			}
			//confirm might cancel the operation, on event binding must be the last one to be done 
			if (_prompt) {
				if (command != null) { //command has own VALIDATE phase, don't do validate again
					BinderImpl.this.doSaveEventNoValidate(comp, evtnm, notifys); //save on event without validation
				} else {
					BinderImpl.this.doSaveEvent(comp, evtnm, notifys); //save on event
				}
				BinderImpl.this.doLoadEvent(comp, evtnm); //load on event
			}
			fireNotifyChanges(notifys);
		}
	}
	
	//TODO
	private void doFailConfirm() {
	}

	private Map<String, Object> evalArgs(Component comp, Map<String, ExpressionX> args) {
		if (args == null) {
			return null;
		}
		final BindEvaluatorX eval = getEvaluatorX();
		final Map<String, Object> result = new LinkedHashMap<String, Object>(args.size()); 
		for(final Iterator<Entry<String, ExpressionX>> it = args.entrySet().iterator(); it.hasNext();) {
			final Entry<String, ExpressionX> entry = it.next(); 
			final String key = entry.getKey();
			final ExpressionX value = entry.getValue();
			final Object evalValue = value == null ? null : eval.getValue(null, comp, value);
			result.put(key, evalValue);
		}
		return result;
	}
	
	public void notifyCommand(String command, Map<String, Object> args) {
		final Set<Property> notifys = new HashSet<Property>();
		doCommand(_rootComp, command, null, args, notifys, false);
		fireNotifyChanges(notifys);
	}

	private void fireNotifyChanges(Set<Property> notifys) {
		for(Property prop : notifys) {
			notifyChange(prop.getBase(), prop.getProperty(), null, prop.getValue());
		}
	}
	
	//comp the component that trigger the command
	//major life cycle of binding (on event trigger)
	//command is the command name after evaluation
	//evtnm event name that fire this command
	//args the passed in argument for executing command
	//notifies container for properties that is to be notifyChange
	//skipConfirm whether skip checking confirm 
	//return properties to be notified change
	private int doCommand(Component comp, String command, String evtnm, Map<String, Object> args, Set<Property> notifys, boolean skipConfirm) {
		BindContext ctx = new BindContextImpl(this, null, false, command, comp, args);
		try {
			doBeforePhase(PhaseListener.COMMAND, ctx); //begin of Command
			boolean success = true;
			//confirm
			if (!skipConfirm) {
				success = doConfirm(comp, command, args, ctx);
			}
			if (!success) {
				return FAIL_CONFIRM;
			}
			
			//validate
			success = doValidate(comp, command, evtnm, args, ctx);
			if (!success) {
				return FAIL_VALIDATE;
			}
			
			//save before command bindings
			doSaveBefore(comp, command, notifys);
			
			//load before command bindings
			doLoadBefore(comp, command);
			
			//execute command
			doExecute(comp, command, args, ctx, notifys);
			
			//save after command bindings
			doSaveAfter(comp, command, notifys);
			
			//load after command bindings
			doLoadAfter(comp, command);
			
			return SUCCESS;
		} finally {
			doAfterPhase(PhaseListener.COMMAND, ctx); //end of Command
		}
	}
	
	/*package*/ void doBeforePhase(int phase, BindContext ctx) {
		if (_phaseListener != null) {
			_phaseListener.beforePhase(phase, ctx);
		}
	}
	
	/*package*/ void doAfterPhase(int phase, BindContext ctx) {
		if (_phaseListener != null) {
			_phaseListener.afterPhase(phase, ctx);
		}
	}
	private void doSaveEventNoValidate(Component comp, String evtnm, Set<Property> notifys) {
		final String evtId = dualId(comp.getUuid(), evtnm);
		final List<SavePropertyBinding> bindings = _saveEventBindings.get(evtId);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doSavePropertyBinding(comp, binding, null, 0, notifys);
			}
		}
	}
	private boolean doSaveEvent(Component comp, String evtnm, Set<Property> notifys) {
		final String evtId = dualId(comp.getUuid(), evtnm);
		final List<SavePropertyBinding> bindings = _saveEventBindings.get(evtId);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				final boolean success = doValidateSaveEvent(comp, binding);
				if (!success) { //failed validation
					return false;
				}
				doSavePropertyBinding(comp, binding, null, 0, notifys);
			}
		}
		return true;
	}

	private boolean doConfirm(Component comp, String command, Map args, BindContext ctx) {
		try {
			doBeforePhase(PhaseListener.CONFIRM, ctx);
			//TODO
			return true;
		} finally {
			doAfterPhase(PhaseListener.CONFIRM, ctx);
		}
	}
	
	private boolean doValidate(Component comp, String command, String evtnm, Map args, BindContext ctx) {
		final Set<Property> validates = new HashSet<Property>();
		try {
			doBeforePhase(PhaseListener.VALIDATE, ctx);
			
			//collect Property for validation in validates
			doValidateSaveBefore(comp, command, validates);
			doValidateSaveAfter(comp, command, validates);
			if (evtnm != null) {
				doValidateSaveEvent(comp, command, evtnm, validates);
			}
			
			//do validation (defined by application)
			if (validates.isEmpty()) {
				return true;
			} else {
				final Method m = getValidateMethod();
				return (Boolean) m.invoke(command, validates, ctx);
			}
		} catch (Exception e) {
			throw UiException.Aide.wrap(e);
		} finally {
			doAfterPhase(PhaseListener.VALIDATE, ctx);
		}
	}
	
	private void doValidateSaveEvent(Component comp, String command, String evtnm, Set<Property> validates) {
		final String evtId = dualId(comp.getUuid(), evtnm);
		final List<SavePropertyBinding> bindings = _saveEventBindings.get(evtId);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doValidateSavePropertyBinding(comp, binding, command, validates);
			}
		}
	}

	private void doValidateSaveBefore(Component comp, String command, Set<Property> validates){
		doValidateSavePropertyBefore(comp, command, validates);
		doValidateSaveFormBefore(comp, command, validates);
	}
	
	private void doValidateSavePropertyBefore(Component comp, String command, Set<Property> validates) {
		final List<SavePropertyBinding> bindings = _saveBeforeBindings.get(command);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doValidateSavePropertyBinding(comp, binding, command, validates);
			}
		}
	}
	
	private void doValidateSaveFormBefore(Component comp, String command, Set<Property> validates) {
		final List<SaveFormBinding> bindings = _saveFormBeforeBindings.get(command);
		if (bindings != null) {
			for (SaveFormBinding binding : bindings) {
				doValidateSaveFormBinding(comp, binding, command, validates);
			}
		}
	}
	
	private void doValidateSaveAfter(Component comp, String command, Set<Property> validates) {
		doValidateSavePropertyAfter(comp, command, validates);
		doValidateSaveFormAfter(comp, command, validates);
	}
	
	private void doValidateSavePropertyAfter(Component comp, String command, Set<Property> validates) {
		final List<SavePropertyBinding> bindings = _saveAfterBindings.get(command);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doValidateSavePropertyBinding(comp, binding, command, validates);
			}
		}
	}
	
	private void doValidateSaveFormAfter(Component comp, String command, Set<Property> validates) {
		final List<SaveFormBinding> bindings = _saveFormAfterBindings.get(command);
		if (bindings != null) {
			for (SaveFormBinding binding : bindings) {
				doValidateSaveFormBinding(comp, binding, command, validates);
			}
		}
	}
	
	//validate a prompt save property binding
	private boolean doValidateSaveEvent(Component comp, SavePropertyBinding binding) {
		if (binding.isValidate()) {
			final Map<String, Object> args = evalArgs(comp, binding.getArgs());
			final BindContext ctx = new BindContextImpl(this, binding, true, null, binding.getComponent(), args);
			try {
				doBeforePhase(PhaseListener.VALIDATE, ctx);
				final Method m = getValidateMethod();
				final Set<Property> validates = binding.getValidates(ctx);
				return (Boolean) m.invoke(getViewModel(), new Object[] {null, validates, ctx});
			} catch (Exception e) {
				throw UiException.Aide.wrap(e);
			} finally {
				doAfterPhase(PhaseListener.VALIDATE, ctx);
			}
		}
		return true;
	}
	
	//validate a command save binding
	private void doValidateSavePropertyBinding(Component comp, SavePropertyBinding binding, String command, Set<Property> validates) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, true, command, binding.getComponent(), args);
		validates.addAll(binding.getValidates(ctx)); //collect properties to be validated
	}
	
	//generic operation to validate a save form binding
	private void doValidateSaveFormBinding(Component comp, SaveFormBinding binding, String command, Set<Property> validates) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, true, command, binding.getComponent(), args);
		//by spec., no way to do prompt save of a form 
		//validate command save, no VALIDATE phase for each binding save
		validates.addAll(binding.getValidates(ctx)); //collect properties to be validated
	}
	
	private void doExecute(Component comp, String command, Map args, BindContext ctx, Set<Property> notifys) {
		try {
			doBeforePhase(PhaseListener.EXECUTE, ctx);
			
			final Object base = getViewModel();
			Method method = null;
			Object[] param = null;
			try { //try one without arguments
				method = Classes.getMethodInPublic(base.getClass(), command, null);
				param = new Object[0];
			} catch (NoSuchMethodException e) { //try one with Map arguments 
				try {
					method = Classes.getMethodInPublic(base.getClass(), command, new Class[] {Map.class});
					param = new Object[] {args == null ? Collections.emptyMap() : args};
				} catch (NoSuchMethodException e1) {
					throw UiException.Aide.wrap(e1);
				}
			}
			if (method != null) {
				try {
					method.invoke(base, param);
				} catch (Exception e) {
					throw UiException.Aide.wrap(e);
				}
				notifys.addAll(BindELContext.getNotifys(method, base, (String)null, (Object) null)); //collect notifyChange
			}
		} finally {
			doAfterPhase(PhaseListener.EXECUTE, ctx);
		}
	}
	
	private void doSaveBefore(Component comp, String command, Set<Property> notifys) {
		doSavePropertyBefore(comp, command, notifys);
		doSaveFormBefore(comp, command, notifys);
	}
	
	private void doSavePropertyBefore(Component comp, String command, Set<Property> notifys) {
		final List<SavePropertyBinding> bindings = _saveBeforeBindings.get(command);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doSavePropertyBinding(comp, binding, command, PhaseListener.SAVE_BEFORE, notifys);
			}
		}
	}
	
	private void doSaveFormBefore(Component comp, String command, Set<Property> notifys) {
		final List<SaveFormBinding> bindings = _saveFormBeforeBindings.get(command);
		if (bindings != null) {
			for (SaveFormBinding binding : bindings) {
				doSaveFormBinding(comp, binding, command, PhaseListener.SAVE_BEFORE, notifys);
			}
		}
	}
	
	private void doSaveAfter(Component comp, String command, Set<Property> notifys) {
		doSavePropertyAfter(comp, command, notifys);
		doSaveFormAfter(comp, command, notifys);
	}
	
	private void doSavePropertyAfter(Component comp, String command, Set<Property> notifys) {
		final BindEvaluatorX eval = getEvaluatorX(); 
		final List<SavePropertyBinding> bindings = _saveAfterBindings.get(command);
		if (bindings != null) {
			for (SavePropertyBinding binding : bindings) {
				doSavePropertyBinding(comp, binding, command, PhaseListener.SAVE_AFTER, notifys);
			}
		}
	}
	
	private void doSaveFormAfter(Component comp, String command, Set<Property> notifys) {
		final BindEvaluatorX eval = getEvaluatorX(); 
		final List<SaveFormBinding> bindings = _saveFormAfterBindings.get(command);
		if (bindings != null) {
			for (SaveFormBinding binding : bindings) {
				doSaveFormBinding(comp, binding, command, PhaseListener.SAVE_AFTER, notifys);
			}
		}
	}
	
	private void doLoadEvent(Component comp, String evtnm) {
		final String evtId = dualId(comp.getUuid(), evtnm); 
		final List<LoadPropertyBinding> bindings = _loadEventBindings.get(evtId);
		if (bindings != null) {
			for (LoadPropertyBinding binding : bindings) {
				doLoadPropertyBinding(comp, binding, null, 0);
			}
		}
	}
	
	private void doLoadPrompt(Component comp, String attr) {
		final String compId = dualId(comp.getUuid(), attr); 
		final List<LoadPropertyBinding> bindings = _loadPromptBindings.get(compId);
		if (bindings != null) {
			for (LoadPropertyBinding binding : bindings) {
				doLoadPropertyBinding(comp, binding, null, 0);
			}
		}
	}
	
	private void doLoadBefore(Component comp, String command) {
		doLoadPropertyBefore(comp, command);
		doLoadFormBefore(comp, command);
	}
	
	private void doLoadPropertyBefore(Component comp, String command) {
		final List<LoadPropertyBinding> bindings = _loadBeforeBindings.get(command);
		if (bindings != null) {
			for (LoadPropertyBinding binding : bindings) {
				doLoadPropertyBinding(comp, binding, command, PhaseListener.LOAD_BEFORE);
			}
		}
	}
	
	private void doLoadFormBefore(Component comp, String command) {
		final List<LoadFormBinding> bindings = _loadFormBeforeBindings.get(command);
		if (bindings != null) {
			for (LoadFormBinding binding : bindings) {
				doLoadFormBinding(comp, binding, command, PhaseListener.LOAD_BEFORE);
			}
		}
	}
	
	private void doLoadAfter(Component comp, String command) {
		doLoadPropertyAfter(comp, command);
		doLoadFormAfter(comp, command);
	}
	
	private void doLoadPropertyAfter(Component comp, String command) {
		final List<LoadPropertyBinding> bindings = _loadAfterBindings.get(command);
		if (bindings != null) {
			for (LoadPropertyBinding binding : bindings) {
				doLoadPropertyBinding(comp, binding, command, PhaseListener.LOAD_AFTER);
			}
		}
	}

	private void doLoadFormAfter(Component comp, String command) {
		final List<LoadFormBinding> bindings = _loadFormAfterBindings.get(command);
		if (bindings != null) {
			for (LoadFormBinding binding : bindings) {
				doLoadFormBinding(comp, binding, command, PhaseListener.LOAD_AFTER);
			}
		}
	}
	
	//generic operation to save a property binding
	private void doSavePropertyBinding(Component comp, SavePropertyBinding binding, String command, int phase, Set<Property> notifys) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, true, command, binding.getComponent(), args);
		
		try {
			doBeforePhase(phase, ctx);
			binding.save(ctx);
		} finally {
			doAfterPhase(phase, ctx);
		}
		
		final Set<Property> xnotifys = (Set<Property>) ctx.getAttribute(BinderImpl.NOTIFYS);
		if (xnotifys != null) {
			notifys.addAll(xnotifys);
		}
	}
	
	//generic operation to load a property binding
	private void doLoadPropertyBinding(Component comp, LoadPropertyBinding binding, String command, int phase) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, false, command, binding.getComponent(), args);
		
		try { 
			doBeforePhase(phase, ctx);
			binding.load(ctx);
		} finally {
			doAfterPhase(phase, ctx);
		}
	}
	
	//generic operation to save a property binding
	private void doSaveFormBinding(Component comp, SaveFormBinding binding, String command, int phase, Set<Property> notifys) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, true, command, binding.getComponent(), args);
		
		try {
			doBeforePhase(phase, ctx);
			binding.save(ctx);
		} finally {
			doAfterPhase(phase, ctx);
		}
		
		final Set<Property> xnotifys = (Set<Property>) ctx.getAttribute(BinderImpl.NOTIFYS);
		if (xnotifys != null) {
			notifys.addAll(xnotifys);
		}
	}
	
	//generic operation to load a property binding
	private void doLoadFormBinding(Component comp, LoadFormBinding binding, String command, int phase) {
		final Map<String, Object> args = evalArgs(comp, binding.getArgs());
		final BindContext ctx = new BindContextImpl(this, binding, false, command, binding.getComponent(), args);
		
		try {
			doBeforePhase(phase, ctx);
			binding.load(ctx);
		} finally {
			doAfterPhase(phase, ctx);
		}
	}
	
	/**
	 * Remove all bindings that associated with the specified component.
	 * @param comp the component
	 */
	public void removeBindings(Component comp) {
		final String uuid = comp.getUuid();
		final Map<String, List<Binding>> attrMap = _bindings.remove(comp);
		if (attrMap != null) {
			final Set<Binding> removed = new HashSet<Binding>();
			for(Entry<String, List<Binding>> entry : attrMap.entrySet()) {
				final String key = entry.getKey(); 
				removeEventCommandListenerIfExists(comp, key); //_listenerMap; //comp+evtnm -> eventlistener
				removeBindingsByCompId(uuid, key);
				removed.addAll(entry.getValue());
			}
			if (!removed.isEmpty()) {
				removeBindings(removed);
			}
		}
		
		//remove trackings
		TrackerImpl tracker = (TrackerImpl) getTracker();
		tracker.removeTrackings(comp);

		comp.removeAttribute(BINDER);
	}

	/**
	 * Remove all bindings that associated with the specified component and key (_fieldExpr|evtnm|formid).
	 * @param comp the component
	 * @param key can be component attribute, event name, or form id
	 */
	public void removeBindings(Component comp, String key) {
		removeEventCommandListenerIfExists(comp, key); //_listenerMap; //comp+evtnm -> eventlistener
		
		final String uuid = comp.getUuid();
		final String dualId = dualId(uuid, key);
		final Set<Binding> bindings = new HashSet<Binding>();
		bindings.addAll(_loadFormPromptBindings.remove(dualId)); //comp+formid -> bindings (load form _prompt)
		bindings.addAll(_loadPromptBindings.remove(dualId)); //comp+_fieldExpr -> bindings (load _prompt)
		bindings.addAll(_loadEventBindings.remove(dualId)); //comp+evtnm -> bindings (load on event)
		bindings.addAll(_saveEventBindings.remove(dualId)); //comp+evtnm -> bindings (save on event)

		removeBindings(bindings);
	}

	private void removeBindings(Collection<Binding> bindings) {
		_loadFormAfterBindings.values().removeAll(bindings); //command -> bindings (load form after command)
		_saveFormAfterBindings.values().removeAll(bindings); //command -> bindings (save form after command)
		_loadFormBeforeBindings.values().removeAll(bindings); //command -> bindings (load form before command)
		_saveFormBeforeBindings.values().removeAll(bindings); //command -> bindings (save form before command)
		_loadAfterBindings.values().removeAll(bindings); //command -> bindings (load after command)
		_saveAfterBindings.values().removeAll(bindings); //command -> bindings (save after command)
		_loadBeforeBindings.values().removeAll(bindings); //command -> bindings (load before command)
		_saveBeforeBindings.values().removeAll(bindings); //command -> bindings (save before command)
	}
	
	private void removeBindingsByCompId(String uuid, String attr) {
		final String dualId = dualId(uuid, attr);
		_loadFormPromptBindings.remove(dualId); //comp+formid -> bindings (load form _prompt)
		_loadPromptBindings.remove(dualId); //comp+_fieldExpr -> bindings (load _prompt)
		_loadEventBindings.remove(dualId); //comp+evtnm -> bindings (load on event)
		_saveEventBindings.remove(dualId); //comp+evtnm -> bindings (save on event)
	}
	
	private void addBinding(Component comp, String attr, Binding binding) {
		Map<String, List<Binding>> attrMap = _bindings.get(comp);
		if (attrMap == null) {
			attrMap = new HashMap<String, List<Binding>>(); 
			_bindings.put(comp, attrMap);
		}
		List<Binding> bindings = attrMap.get(attr);
		if (bindings == null) {
			bindings = new ArrayList<Binding>();
			attrMap.put(attr, bindings);
		}
		bindings.add(binding);
		comp.setAttribute(BINDER, this);
	}

	public Tracker getTracker() {
		if (_tracker == null) {
			_tracker = new TrackerImpl();
		}
		return _tracker;
	}
	
	/**
	 * Internal Use only.
	 */
	public void loadComponent(Component comp) {
		loadComponentProperties(comp);
		for(Component kid = comp.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
			loadComponent(kid); //recursive
		}
	}
	
	private void loadComponentProperties(Component comp) {
		final String uuid = comp.getUuid();
		final Map<String, List<Binding>> compBindings = _bindings.get(comp);
		if (compBindings != null) {
			for(String key : compBindings.keySet()) {
				final String dualId = dualId(uuid, key);
				final List<LoadFormBinding> formBindings = _loadFormPromptBindings.get(dualId);
				if (formBindings != null) {
					for (LoadFormBinding binding : formBindings) {
						final Map<String, Object> args = evalArgs(binding.getComponent(), binding.getArgs());
						final BindContext ctx = new BindContextImpl(this, binding, false, null, comp, args);
						binding.load(ctx);
					}
				}
			}
			for(String key : compBindings.keySet()) {
				final String dualId = dualId(uuid, key);
				final List<LoadPropertyBinding> propBindings = _loadPromptBindings.get(dualId);
				if (propBindings != null) {
					for (LoadPropertyBinding binding : propBindings) {
						final Map<String, Object> args = evalArgs(comp, binding.getArgs());
						final BindContext ctx = new BindContextImpl(this, binding, false, null, comp, args);
						binding.load(ctx);
					}
				}
			}
		}
	}
	
	public void notifyChange(Object base, String attr, Object oldValue, Object newValue) {
		getEventQueue().publish(new PropertyChangeEvent("onPropertyChange", _rootComp, base, attr, oldValue, newValue));
	}
	
	public void setPhaseListener(PhaseListener listener) {
		_phaseListener = listener;
	}

	public void subscribeChangeListener(String quename, String quescope, EventListener listener) {
		EventQueue que = EventQueues.lookup(quename, quescope, true);
		que.subscribe(listener);
	}
	
	private class PropertyChangeEvent extends Event {
		private static final long serialVersionUID = 201109091736L;
		private final Object _base;
		private final String _propName;
		private final Object _oldValue;
		private final Object _newValue;

		public PropertyChangeEvent(String name, Component comp, Object base, String propName, Object oldValue, Object newValue) {
			super(name, comp);
			this._base = base;
			this._propName = propName;
			this._oldValue = oldValue;
			this._newValue = newValue;
		}

		public Object getBase() {
			return _base;
		}

		public String getPropertyName() {
			return _propName;
		}

		public Object getOldValue() {
			return _oldValue;
		}

		public Object getNewValue() {
			return _newValue;
		}
	}
	
	protected EventQueue getEventQueue() {
		return EventQueues.lookup(_quename, _quescope, true);
	}

	private String dualId(String uuid, String attr) {
		return uuid+"#"+attr;
	}

	//validate method for command triggered binding save
	//return true if validate successful; false otherwise.
	//boolean validate(String commandName, Set<Property> property, BindContext ctx);
	protected Method getValidateMethod() throws NoSuchMethodException {
		return getViewModel()
			.getClass().getMethod("validate", new Class[] {String.class, Set.class, BindContext.class});
	}
}

package org.zkoss.zktest.bind.basic;

import java.util.Map;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.Scope;
import org.zkoss.bind.annotation.ScopeParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;

public class ContextParamVM {

	String applicationScope;
	String sessionScope;
	String desktopScope;
	String pageScope;
	String spaceScope;
	String requestScope;
	String componentScope;

	boolean bindContext;
	boolean bindBinder;

	boolean bindExecution;
	boolean bindComponent;
	boolean bindSpaceOwner;
	boolean bindPage;
	boolean bindDesktop;
	boolean bindSession;
	boolean bindApplication;
	
	String bindComponentId;

	@Init
	public void init(@ContextParam(ContextType.APPLICATION_SCOPE) Map<?, ?> applicationScope,
			@ContextParam(ContextType.SESSION_SCOPE) Map<?, ?> sessionScope,
			@ContextParam(ContextType.DESKTOP_SCOPE) Map<?, ?> desktopScope,
			@ContextParam(ContextType.PAGE_SCOPE) Map<?, ?> pageScope,
			@ContextParam(ContextType.SPACE_SCOPE) Map<?, ?> spaceScope,
			@ContextParam(ContextType.REQUEST_SCOPE) Map<?, ?> requestScope,
			@ContextParam(ContextType.COMPONENT_SCOPE) Map<?, ?> componentScope,

			@ContextParam(ContextType.EXECUTION) Execution execution,
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.SPACE_OWNER) IdSpace spaceOwner, 
			@ContextParam(ContextType.PAGE) Page page,
			@ContextParam(ContextType.DEKSTOP) Desktop desktop,
			@ContextParam(ContextType.SESSION) Session session,
			@ContextParam(ContextType.APPLICATION) WebApp application,

			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.BINDER) Binder binder) {
		this.applicationScope = (String) applicationScope.get("applicationScopeVar");
		this.sessionScope = (String) sessionScope.get("sessionScopeVar");
		this.desktopScope = (String) desktopScope.get("desktopScopeVar");
		this.pageScope = (String) pageScope.get("pageScopeVar");
		this.spaceScope = (String) spaceScope.get("componentScopeVar");
		this.componentScope = (String) componentScope.get("componentScopeVar");
		this.requestScope = (String) requestScope.get("requestScopeVar");

		this.bindExecution = this.requestScope.equals(execution.getAttribute("requestScopeVar"));
		this.bindComponent = this.componentScope.equals(component.getAttribute("componentScopeVar"));
		this.bindSpaceOwner = this.spaceScope.equals(spaceOwner.getAttribute("componentScopeVar"));
		this.bindPage = this.pageScope.equals(page.getAttribute("pageScopeVar"));
		this.bindDesktop = this.desktopScope.equals(desktop.getAttribute("desktopScopeVar"));
		this.bindSession = this.sessionScope.equals(session.getAttribute("sessionScopeVar"));
		this.bindApplication = this.applicationScope.equals(application.getAttribute("applicationScopeVar"));

		this.bindContext = bindContext != null;
		this.bindBinder = binder != null;
		this.bindComponentId = component.getId();
	}

	public String getApplicationScope() {
		return applicationScope;
	}

	public void setApplicationScope(String applicationScope) {
		this.applicationScope = applicationScope;
	}

	public String getSessionScope() {
		return sessionScope;
	}

	public void setSessionScope(String sessionScope) {
		this.sessionScope = sessionScope;
	}

	public String getDesktopScope() {
		return desktopScope;
	}

	public void setDesktopScope(String desktopScope) {
		this.desktopScope = desktopScope;
	}

	public String getPageScope() {
		return pageScope;
	}

	public void setPageScope(String pageScope) {
		this.pageScope = pageScope;
	}

	public String getSpaceScope() {
		return spaceScope;
	}

	public void setSpaceScope(String spaceScope) {
		this.spaceScope = spaceScope;
	}

	public String getRequestScope() {
		return requestScope;
	}

	public void setRequestScope(String requestScope) {
		this.requestScope = requestScope;
	}

	public String getComponentScope() {
		return componentScope;
	}

	public void setComponentScope(String componentScope) {
		this.componentScope = componentScope;
	}

	public boolean isBindContext() {
		return bindContext;
	}

	public void setBindContext(boolean bindContext) {
		this.bindContext = bindContext;
	}

	public boolean isBindBinder() {
		return bindBinder;
	}

	public void setBindBinder(boolean bindBinder) {
		this.bindBinder = bindBinder;
	}

	

	public boolean isBindExecution() {
		return bindExecution;
	}

	public void setBindExecution(boolean bindExecution) {
		this.bindExecution = bindExecution;
	}

	public boolean isBindComponent() {
		return bindComponent;
	}

	public void setBindComponent(boolean bindComponent) {
		this.bindComponent = bindComponent;
	}

	public boolean isBindSpaceOwner() {
		return bindSpaceOwner;
	}

	public void setBindSpaceOwner(boolean bindSpaceOwner) {
		this.bindSpaceOwner = bindSpaceOwner;
	}

	public boolean isBindPage() {
		return bindPage;
	}

	public void setBindPage(boolean bindPage) {
		this.bindPage = bindPage;
	}

	public boolean isBindDesktop() {
		return bindDesktop;
	}

	public void setBindDesktop(boolean bindDesktop) {
		this.bindDesktop = bindDesktop;
	}

	public boolean isBindSession() {
		return bindSession;
	}

	public void setBindSession(boolean bindSession) {
		this.bindSession = bindSession;
	}

	public boolean isBindApplication() {
		return bindApplication;
	}

	public void setBindApplication(boolean bindApplication) {
		this.bindApplication = bindApplication;
	}

	@NotifyChange("*")
	@Command
	public void cmd1(@ContextParam(ContextType.APPLICATION_SCOPE) Map<?, ?> applicationScope,
			@ContextParam(ContextType.SESSION_SCOPE) Map<?, ?> sessionScope,
			@ContextParam(ContextType.DESKTOP_SCOPE) Map<?, ?> desktopScope,
			@ContextParam(ContextType.PAGE_SCOPE) Map<?, ?> pageScope,
			@ContextParam(ContextType.SPACE_SCOPE) Map<?, ?> spaceScope,
			@ContextParam(ContextType.REQUEST_SCOPE) Map<?, ?> requestScope,
			@ContextParam(ContextType.COMPONENT_SCOPE) Map<?, ?> componentScope,

			@ContextParam(ContextType.EXECUTION) Execution execution,
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.SPACE_OWNER) IdSpace spaceOwner, 
			@ContextParam(ContextType.PAGE) Page page,
			@ContextParam(ContextType.DEKSTOP) Desktop desktop,
			@ContextParam(ContextType.SESSION) Session session,
			@ContextParam(ContextType.APPLICATION) WebApp application,

			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.BINDER) Binder binder) {
		this.applicationScope = (String) applicationScope.get("applicationScopeVar");
		this.sessionScope = (String) sessionScope.get("sessionScopeVar");
		this.desktopScope = (String) desktopScope.get("desktopScopeVar");
		this.pageScope = (String) pageScope.get("pageScopeVar");
		this.spaceScope = (String) spaceScope.get("componentScopeVar");
		this.componentScope = (String) componentScope.get("componentScopeVar");
		this.requestScope = (String) requestScope.get("requestScopeVar");

		this.bindExecution = execution.getAttribute("requestScopeVar")!=null;
		this.bindComponent = !this.componentScope.equals(component.getAttribute("componentScopeVar"));
		this.bindSpaceOwner = !this.spaceScope.equals(spaceOwner.getAttribute("componentScopeVar"));
		this.bindPage = !this.pageScope.equals(page.getAttribute("pageScopeVar"));
		this.bindDesktop = !this.desktopScope.equals(desktop.getAttribute("desktopScopeVar"));
		this.bindSession = !this.sessionScope.equals(session.getAttribute("sessionScopeVar"));
		this.bindApplication = !this.applicationScope.equals(application.getAttribute("applicationScopeVar"));

		this.bindContext = bindContext == null;
		this.bindBinder = binder == null;

		Executions.getCurrent().getDesktop().setAttribute("sessionScopeVar", "var1 by Desktop");
		Executions.getCurrent().getDesktop().setAttribute("applicationScopeVar", "var2 by Desktop");
		
		this.bindComponentId = component.getId();
		
		spaceOwner.setAttribute("componentScopeVar", "spaceScope-Y");
	}

	@NotifyChange("*")
	@Command
	public void cmd2(@ContextParam(ContextType.COMPONENT) Component component,
			@ScopeParam("applicationScopeVar") String applicationScope,
			@ScopeParam("sessionScopeVar") String sessionScope,
			@ContextParam(ContextType.SPACE_SCOPE) Map<?, ?> spaceScope) {
		this.applicationScope = applicationScope;
		this.sessionScope = sessionScope;
		this.bindComponentId = component.getId();
		this.spaceScope = (String) spaceScope.get("componentScopeVar");
	}

	@NotifyChange("*")
	@Command
	public void cmd3(@ContextParam(ContextType.COMPONENT) Component component,
			@ScopeParam(value = "applicationScopeVar", scopes = Scope.APPLICATION) String applicationScope,
			@ScopeParam(value = "sessionScopeVar", scopes = Scope.SESSION) String sessionScope) {
		this.applicationScope = applicationScope;
		this.sessionScope = sessionScope;
		this.bindComponentId = component.getId();
	}

	public String getBindComponentId() {
		return bindComponentId;
	}

	public void setBindComponentId(String bindComponentId) {
		this.bindComponentId = bindComponentId;
	}

	
}

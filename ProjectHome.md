ZK Bind is the new generation of Data Binding that supports MVVM design pattern with ZK Ajax Framework.

### Features ###

  * One way binding in either way:
> Save only:
```
  <textbox value="@bind(save=a.b.c)"/>
```
> Load only:
```
  <textbox value="@bind(load=a.b.c)"/>
```
  * Two way binding:
```
  <textbox value="@bind(a.b.c)"/>
```
  * Collection binding
```
  <listbox model="@bind(vm.persons)" selectedItem="@bind(vm.selected)">
    <template name="model" var="p">
      <listitem label="@bind(p.fullname)"/>
    </template>
  </listbox>
```
  * Form binding:
```
  <grid self="@form(id='fx', save=vm.currentTask before 'updateTask')"/>
```
  * Conditional binding: binding on different commands
```
  <grid self="@form(id='fx', save={vm.currentTask before 'updateTask', vm.newTask before 'addTask'})"/>
```
  * EL 2.2 powered:
```
  <image src="@bind(person.gender == 'boy' ? 'boy.png' : 'girl.png')"/>

  <button onClick="@bind(vm.add ? 'add' : 'update')" />

  <button disabled="@bind(empty vm.symbol)"/>
```
  * Java annotated property change and dependency tracking(in View-Model)
```
  @NotifyChange
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @NotifyChange
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @DependsOn({"firstname","lastname"})
  public String getFullname() {
    return this.firstname + " " + this.lastname;
  }
```
  * Bind on demend:
> binding when added component, Macro, and/or call createComponents() ...
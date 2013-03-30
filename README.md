<h2>Read me first</h2>

<p>The license of this project is LGPLv3 or later. See file src/main/resources/LICENSE for the full
text.</p>

<h2>What this is</h2>

<p>This is a 100% Java implementation of IETF's <a href="http://tools.ietf.org/html/rfc6570">RFC
6570</a> (URI templates). This RFC is used, among others, in JSON Schema hyperschema.</p>

<h2>Versions</h2>

<p>The project is still under development. The current version is <b>0.2</b>.

<h2>Status</h2>

<p>Tests are all extracted from the text of the RFC. All tests pass. This means even at this early
stage, you can use this library for your URI template needs.</p>

<p>See below for a sample code usage.</p>

<h2>Maven artifact</h2>

```xml
<dependency>
    <groupId>com.github.fge</groupId>
    <artifactId>uri-template</artifactId>
    <version>your-version-here</version>
</dependency>
```

<h2>Sample code usage</h2>

<p>First you need to build your map of values. As this is totally application dependent, this
application only offers facilities to create values according to their type (which this
implementation calls "scalar", "list" and "map").  Example:</p>

```java
final Map<String, VariableValue> vars = new HashMap<String, VariableValue>();

String name;
VariableValue value;

// Create a scalar value
name = "scalar";
value = new ScalarValue("hello");
vars.put(name, value);

// Create a list value
name = "list";
value = new ListValue(Arrays.asList("one", "two", "three"));
vars.put(name, value);

// Create a map value -- note: uses Guava's ImmutableMap
name = "map";
value = new MapValue(ImmutableMap.of("key1", "value1", "key2", "value2"));
vars.put(name, value);
```

<p>Then, you need to create a URI template. This is done using the `URITemplate` class:</p>

```java
final URITemplate template = new URITemplate("http://foo.bar/myPage{?map*}");

// Will print out "http://foo.bar/myPage?key1=value1&key2=value2"
System.out.println(template.expand(vars));
```

<p>See the RFC for more sample usages.</p>


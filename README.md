<h2>Read me first</h2>

<p>The license of this project is LGPLv3 or later. See file src/main/resources/LICENSE for the full
text.</p>

<h2>What this is</h2>

<p>This is a 100% Java implementation of IETF's <a href="http://tools.ietf.org/html/rfc6570">RFC
6570</a> (URI templates). This RFC is used, among others, in JSON Schema hyperschema.</p>

<h2>Versions</h2>

<p>The current version is <b>0.4</b>.</p>

<h2>Maven artifact</h2>

```xml
<dependency>
    <groupId>com.github.fge</groupId>
    <artifactId>uri-template</artifactId>
    <version>your-version-here</version>
</dependency>
```

<h2>Features</h2>

<p>This library has only one dependency: Guava.</p>

<p>Template expansion is feature complete and without errors. All samples from the RFC and the
existing <a href="https://github.com/dret/uritemplate-test">test suite from github</a> pass without
a problem.</p>

<p>Please note that percent encodings are lower case in this implementation.</p>

<p>One of the goals of this library is to be as light as possible. As such, it does _not_ provide
facilities to read variable values from, say, JSON (even though the test files are written in JSON,
Jackson is included as a test dependency only).</p>

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

// Create a map value
name = "map";
value = new MapValue(ImmutableMap.of("key1", "value1", "key2", "value2"));
vars.put(name, value);
```

<p>Then, you need to create a URI template. This is done using the `URITemplate` class.</p>

```java
// Throws URITemplateParseException if the template is invalid
final URITemplate template = new URITemplate("http://foo.bar/myPage{?map*}");

// Will print out "http://foo.bar/myPage?key1=value1&key2=value2"
// Throws URITemplateException if the expansion is invalid
System.out.println(template.expand(vars));
```

<p>See the RFC for more sample usages.</p>


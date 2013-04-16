<h2>Read me first</h2>

<p>The license of this project is LGPLv3 or later. See file src/main/resources/LICENSE for the full
text.</p>

<h2>What this is</h2>

<p>This is a 100% Java implementation of IETF's <a href="http://tools.ietf.org/html/rfc6570">RFC
6570</a> (URI templates). This RFC is used, among others, in JSON Schema hyperschema.</p>

<h2>Versions</h2>

<p>The project is still under development. The current version is <b>0.3</b>.

<h2>Status</h2>

<p>Existing tests for this implementation are based on two sources:</p>

<ul>
    <li>the RFC itself;</li>
    <li>the <a href="https://github.com/dret/uritemplate-test">uritemplate-test</a> testuite.</li>
</ul>

<p>All tests from the RFC itself pass. However, with regards to the second source, tests involving
empty lists and/or empty associative arrays do not pass -- that is, not all of them.</p>

<p>The reason is that some of these tests cover a gray area of the RFC; in some situations, empty
lists/associative array expansions are not clearly defined. Currently, the implementation acts in
such a way that it does not agree with the aforementioned source.</p>

<p>As a consequence, further clarification is needed (in the shape of an errata) with regards to
empty lists/associative arrays. When such an errata is available, this implementation will comply to
it.</p>

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


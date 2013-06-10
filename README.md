## Read me first

The license of this project is LGPLv3 or later. See file src/main/resources/LICENSE for the full
text.

## What this is

This is a 100% Java implementation of IETF's [RFC 6570](http://tools.ietf.org/html/rfc6570) (URI
templates). This RFC is used, among others, in JSON Schema hyperschema.

More generally, if you have to generate a lot of URIs (or URLs, since URLs are URIs) all having the
sample "place holders" for values and don't want to be bothered with encoding problems etc, this is
the library for you.

## Versions

The current version is **0.4**.

## Maven artifact

```xml
<dependency>
    <groupId>com.github.fge</groupId>
    <artifactId>uri-template</artifactId>
    <version>your-version-here</version>
</dependency>
```

## Features

Template expansion is feature complete and without errors. All samples from the RFC and the
existing [test suite from github](https://github.com/dret/uritemplate-test) pass without
a problem.

Please note that percent encodings are lower case in this implementation.

One of the goals of this library is to be as light as possible. As such, it does _not_ provide
facilities to read variable values from, say, JSON (even though the test files are written in JSON,
Jackson is included as a test dependency only).

## Sample code usage

First you need to build your map of values. As this is totally application dependent, this
application only offers facilities to create values according to their type (which this
implementation calls "scalar", "list" and "map").  Example:

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

Then, you need to create a URI template. This is done using the `URITemplate` class.

```java
// Throws URITemplateParseException if the template is invalid
final URITemplate template = new URITemplate("http://foo.bar/myPage{?map*}");

// Will print out "http://foo.bar/myPage?key1=value1&key2=value2"
// Throws URITemplateException if the expansion is invalid
System.out.println(template.expand(vars));
```

See the RFC for more sample usages.


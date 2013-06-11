## Read me first

The license of this project is LGPLv3 or later. See file src/main/resources/LICENSE for the full
text.

## What this is

This is a 100% Java implementation of IETF's [RFC 6570](http://tools.ietf.org/html/rfc6570) (URI
templates).

URI templates are very powerful and unfortunately seldomly known (for now, that is). They allow you
to operate substitutions anywhere in a URI, without having to encode your values first. The
classical recipe, often seen with Java, is to use the
[`URLEncoder`](http://docs.oracle.com/javase/7/docs/api/java/net/URLEncoder.html) class -- but this
is the wrong thing to do, see below.

## What are URI templates

URI templates are particularly useful if you wish to generate URIs to work with REST APIs (which are
more common as the time goes. Google, Facebook, Twitter, you name it: everybody uses such APIs these
days). As such, this library is particularly suited for REST clients, but also on the server side.

### Sample URI templates

Here are some possible templates:

```
# Substitution of a map of query parameters
http://foo.bar.com/some/request{?queryparams*}
# Simple substitutions
http://some.restsite.com/{user}/profile
# Fragment substitution
http://yet.another.site/path/to/somewhere#{+fragmentPart}
```

As an example, let us take the query parameters map expansion with the following key/value pairs:

* `hello` -> `world!`,
* `streetInGerman` -> `Straße`

Substituting this map into the first template will give:

```
http://foo.bar.com/some/request?hello=world%21&streetInGerman=Stra%c3%9fe
```

As you see, this produces a valid URI!

### Why `URLEncoder.encode()` doesn't work

There is a very common misconception with this method. It does NOT encode strings for use in URIs,
it encodes strings **for use in POST data**, that is for `application/x-www-form-urlencoded` data;
and in this encoding, spaces become `+`, not `%20`!

What is more, the set of characters it encodes as percent-encoded sequences are not the set of
characters to be encoded in URIs, and even in URIs, this character set differs according to whether
you are, for instance, in the path element or in fragment elements (you don't encode a `/` in a
fragment element, for instance).

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

## Sample code usage

First you need to build your map of values. As this is totally application dependent, this
application only offers facilities to create values according to their type (which this
implementation calls "scalar", "list" and "map").  Example:

```java
// Create a variable map, consisting of name/value pairs
final VariableMapBuilder builder = VariableMap.newBuilder();

// Add scalar values
builder.addScalarValue("scalar", "hello");
builder.addScalar("id", 38928932);

// Create a list value
builder.addListValue("list", "one", 2, "three");

// Create a map value
final MapValue mapValue = MapValue.newBuilder()
    .put("key1", "value1")
    .put("number", 3)
    .build();

builder.add("map", mapValue);

// Create the variable map
final VariableMap vars = builder.freeze();
```

Then, you need to create a URI template. This is done using the `URITemplate` class.

```java
// Throws URITemplateParseException if the template is invalid
final URITemplate template = new URITemplate("http://foo.bar/myPage{?map*}");

// Expansion result as a string
template.expand(vars);
// Expansion result as a URI
template.toURI(vars);
// Expansion result as a URL
template.toURL(vars);
```


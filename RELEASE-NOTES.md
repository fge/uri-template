## 0.9

* License fixes (APK compatibility).
* Gradle updates.
* Fix bug with prefix lengths and Unicode characters outside the BMP.
* Dependencies updates.

## 0.8

* Dual license LGPL 3.0/ASL 2.0; upgrade dependencies accordingly.
* Upgrade to gradle 1.10.
* Percent escapes are now uppercase and not lowercase (RFC 3986, section 2.1
  recommends this).
* Update guava dependency to 16.0.1 (for PercentEscaper).

## 0.7

* Update msg-simple dependency; drop ServiceLoader support.
* Definitely remove Maven support.

## 0.6

* Switch to Gradle for build.
* Some small code improvements.
* VariableMap improvements.

## 0.5

* Add msg-simple dependency.
* Improve error messages.
* Improve list/map values building and error reporting.
* Implement VariableMap.
* Javadoc.

## 0.4

* Complete rework of rendering algorithm; all corner case tests now pass.

## 0.3

* Correct percent-encoding implementation for arbitrary string values.

## 0.2

* Add uritemplate-tests codebase; many tests still failing.
* Some fundamental errors fixed.

## 0.1

* Initial version.

Francis Galiegue (244):
      Update msg-simple dependency; update code accordingly
      Remove all traces of maven build; update BUILD.md
      Fix javadoc build; remove ServiceLoader support; add plugins
      Update jackson-coreutils dependency (test)


android-webpub
==

[![Maven Central](https://img.shields.io/maven-central/v/org.thepalaceproject.webpub/org.thepalaceproject.webpub.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.thepalaceproject.webpub%22)
[![Maven Central (snapshot)](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Forg%2Fthepalaceproject%2Fwebpub%2Forg.thepalaceproject.webpub%2Fmaven-metadata.xml&style=flat-square)](https://central.sonatype.com/repository/maven-snapshots/org/thepalaceproject/webpub/)

![android-webpub](./src/site/resources/banner.jpg?raw=true)

## android-webpub

The `android-webpub` package provides a set of types and functions to parse
and serialize [WebPub](https://github.com/readium/webpub-manifest) manifests
and [OPDS 2.0](https://drafts.opds.io/opds-2.0.html) feeds.

## Features

* Parses [WebPub](https://github.com/readium/webpub-manifest) and derivatives such as [OPDS 2.0](https://drafts.opds.io/opds-2.0.html).
* Integrates [dixmont](https://github.com/io7m-com/dixmont) for security against deserialization attacks.
* Uses the best-in-class [Jackson](https://github.com/FasterXML/jackson) JSON parser internally.
* High-coverage automated test suite with hundreds of real-life feed samples.
* Apache 2.0 license.

## Building

```
$ mvn clean verify
```

## Standards Compliance

The type declarations were derived from the WebPub manifest specification and
the OPDS 2.0 specification, along with some extensions to the standards that
have not been integrated at the time of writing.

The resulting parser is somewhat permissive, and somewhat strict. We are
aiming for maximum compatibility with real-world feeds and manifests as opposed
to strict adherence to specifications (many of which are currently in _draft_
status anyway).

Specifically:

* If a particular JSON property is marked as `required` by the spec, then
  the property _will_ be required by this parser unless there is at least one
  real-world feed or manifest that does not conform to the specification.
  Therefore, the parser can be somewhat more permissive than the specifications
  allow in this regard.

* If a particular property is defined in the schema as having a particular
  type, then we do strictly adhere to the types. If a real-world feed or
  manifest fails to follow the declared type, then we reject the feed or
  manifest as invalid. Therefore, the parser is strict in its adherence to
  the specification in this regard.

Our observation seems to be that, if feed/manifest authors declare a
property, they tend to get the types right. However, feed/manifest authors
do occasionally make mistakes and leave out required properties. Presumably,
leaving out a property is an easier mistake to make, and nobody is doing
schema validation.

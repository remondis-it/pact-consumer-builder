# Pact Consumer Builder - How to contribute

First of all: Feel free to contribute to this project! We appreciate any help to develop this library and make it nice, stable and feature complete.

We try to check every new feature carefully before merging pull requests!

This project provides a formatter configurations for Eclipse and IntelliJ IDEA.

The formatter configurations of both supported IDEs were created to produce similar outputs. Although there are slightly different formattings, the outputs should be compliant to the Checkstyle rule set.

## Contributing Features

When contributing features, any suggestions to extend the README of this project are welcome :-)

### Testing features

When writing test for this project, please make sure to create a new package for your test. This avoids unnecessary dependencies to other test classes.

## Contributing bug fixes

When contributing bug fixes, please first write a test that provokes the bug. Please use the package `com.remondis.cdc.consumer.pactbuilder.regression` for that.


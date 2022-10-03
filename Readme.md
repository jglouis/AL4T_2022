# Samples and exercises for Software and Quality lecture

This repository contains material for the theoretical course.
It is intended for students and is built as a support for learning, **not a reference**.
Technical references for the language might be found at <https://docs.oracle.com/javase/specs/>.

This repository makes extensive usage of Gradle and, as such, should be used running Gradle tasks.
See <https://docs.gradle.org/current/userguide/userguide.html> for more details.
For organisation purposes the gradle root projects is separated into independent modules. Each module is given a short description in this Readme.
More details can be found in each module's own Readme.

# `basics` module

The `basics` module is an introduction to the Java programming language.


The `basics` module uses the `application` gradle plugin.
Running is done by running the `run` task, like so `./gradlew :basics:run`

## `solid` modules

This module illustrates basic SOLID principles violations with their corresponding solutions.

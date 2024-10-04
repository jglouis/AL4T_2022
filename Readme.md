# Samples and exercises for Software and Quality lecture

This repository contains material for the theoretical course.
It is intended for students and is built as a support for learning, **not as a reference**.
Technical references for the language might be found at <https://docs.oracle.com/javase/specs/>.

This repository makes extensive usage of Gradle and, as such, should be used running Gradle tasks.
See <https://docs.gradle.org/current/userguide/userguide.html> for more details.
For organisational purposes the gradle root projects is separated into independent modules. Each module is given a short description in this Readme.
More details can be found in each module's own Readme.

## `basics` module

The `basics` module is an introduction to the Java programming language.

The `basics` module uses the `application` gradle plugin.
Running is done by running the `run` task, like so `./gradlew :basics:run`

## `traffic` module

This module features some code found on GitHub. It serves as an exercises for detecting
and eliminating code smells.

## `mario` module

This module features an implementation of Super Mario Bros in Java found on Github.
This module is used for showcasing code maintainability issues and possible refactoring.

## `chess` module

An implementation of the game of chess with an interactive CLI.
This module is to practice writing unit tests.

## `solid` module

This module illustrates basic SOLID principles violations with their corresponding solutions.

## `carfactory` module

This module illustrates dependency injection with dagger.

## `designpatterns` module

A collection of design patterns samples.
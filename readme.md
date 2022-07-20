# Rigel - Testing Utility

![alt text](https://www.astronomytrek.com/wp-content/uploads/2016/02/rigel.jpg)

Rigel is a Kotlin testing utility that can handle async code is a nice way.

## Usage

Use Jitpack maven
`maven { url 'https://jitpack.io' }`

Import with

`androidTestImplementation 'com.github.opalcafe:rigel:0.0.2-indev1'`


Only works in AndroidTest because it uses coroutine dispatcher. 

## Dependancies

`implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'`

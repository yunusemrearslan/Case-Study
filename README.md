# Take Note!

![Webp net-resizeimage (1)](https://user-images.githubusercontent.com/48530342/100637172-9fb1ff00-3343-11eb-8be0-f569c8152158.jpg)

Take Note is a basic To-Do App which is written in Kotlin. It aims to cover Core Android Concepts and demonstrate how to use them effectively. As design architecture,
it uses MVVM Design Pattern to achieve more maintainable code. The main concepts that are covered in this application are:

* Android Navigation Component

* Room Database

* DataBinding

* LiveData / Lifecycle

* ViewModel

* Glide

## Details

This project uses "ktlint" for good and lint verified syntax. It checks the syntax with pre-defined rules before every commit.

For the unit tests, LiveDataUtilAndroidTest.kt file is used. The reason is that, LiveData is asynchronous by nature but for the test purposes it should behave synchronous. Thanks to this extension "getOrAwaitValue", unit tests are successfully executed.









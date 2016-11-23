Zoidberg
========

Overview
--------
Zoidberg is intended for embedding a JRuby console in a Java web application
which then could access arbitrary Java services (for testing purposes) using
the Ruby programming language.

As an example the
[yahoo-weather-java-api](https://github.com/fedy2/yahoo-weather-java-api)
has been exposed to the console.

![Zoidberg](https://raw.githubusercontent.com/gaborbata/zoidberg/master/resources/zoidberg-screenshot.png)

How to compile
--------------

    mvn clean package

Usage
-----
Java 7 or later is required to start Zoidberg.

* Start server: `mvn spring-boot:run` or `java -jar zoidberg-0.2-SNAPSHOT.jar`
* Access server: http://localhost:9999

Configuration
-------------
Configurations can be modified in `resources/config/application.properties`
or by addig them as startup parameters:

    server.port=9999

License
-------
Copyright (c) 2016 Gabor Bata

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

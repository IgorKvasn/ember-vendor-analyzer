# ember-vendor-analyzer
Analyzes the size of vendor.js in Ember-CLI build

## Usage

Notice: you must have a Java (JRE) installed on your computer.

1. download the JAR file from this repository
2. run `java -jar <name-of-the-jar>` with one parameter - the absolute path of your *vendor.js* file

You will get an output with module names ant their size, like this:

```
ember-data - size=527716 B / 515 kB
ember - size=409805 B / 400 kB
ember-simple-auth - size=102442 B / 100 kB
liquid-fire - size=55238 B / 54 kB
ember-mobile-inputs - size=20543 B / 20 kB
ember-resolver - size=17653 B / 17 kB
ember-ajax - size=15925 B / 16 kB
ember-inflector - size=13617 B / 13 kB
ember-load-initializers - size=9826 B / 10 kB
ember-getowner-polyfill - size=6162 B / 6 kB
ember-cli-app-version - size=2225 B / 2 kB
```

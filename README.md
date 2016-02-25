# KotlinYield
Introduce lazy yield in kotlin. The library uses instrumentation instead of thread context.

### Simple way to use library with maven:

```xml
<repositories>
    <repository>
        <id>kotlinyield</id>
        <url>https://github.com/suhininalex/KotlinYield/raw/master/mvn-repo</url>
    </repository>
</repositories>
```


```xml
<dependency>
    <groupId>net.suhininalex</groupId>
    <artifactId>kotlinyield</artifactId>
    <version>1.0</version>
</dependency>
```


```xml
 <plugin>
    <groupId>com.offbynull.coroutines</groupId>
    <artifactId>maven-plugin</artifactId>
    <version>1.1.1</version>
    <executions>
        <execution>
            <goals>
                <goal>instrument</goal>
                <goal>test-instrument</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Make shure you launch maven coroutines:instrument goal before run.**

### Sample

```kotlin
fun File.getRecursiveFiles(): Iterable<File> =
    yieldable {
        if (isFile)
            yeild(this@getRecursiveFiles)
        else if (isDirectory) {
            listFiles().forEach {
                yeild(it.getRecursiveFiles())
            }
        }
    }

...

File("/home/sukhinin").getRecursiveFiles()
    .stream().forEach {
    println(it)
}
```

### See also:

Used coroutines library: [Coroutines](https://github.com/suhininalex/coroutines) fork of [Coroutines](https://github.com/offbynull/coroutines)
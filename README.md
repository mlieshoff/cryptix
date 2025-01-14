[![](https://img.shields.io/badge/java-packagecloud.io-844fec.svg)](https://packagecloud.io/)

# cryptix 1.0.0
A Java Wrapper For Official Supercell Clash Royal Api

## Simplest Usage

## How to bind the packagecloud repository

```xml
    <repositories>
        <repository>
            <id>packagecloud-cryptix</id>
            <url>https://packagecloud.io/mlieshoff/cryptix/maven2</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```

## Add dependency

to Gradle:
```groovy
    implementation group: 'cryptix', name: 'cryptix', version: '1.0.0'
```

to Maven:
```xml
    <dependency>
        <groupId>com.psiclopy</groupId>
        <artifactId>cryptix</artifactId>
        <version>1.0.0</version>
    </dependency>
```

## Continuous Integration

https://github.com/mlieshoff/cryptix/actions

## Repository

https://packagecloud.io/mlieshoff/cryptix

## Logging

We are using SLF4j.


That's all, enjoy :)

## Library updates

Minor versions
```
mvn versions:update-parent versions:use-latest-releases versions:update-properties versions:commit -DallowMajorUpdates=false
```

Major versions
```
mvn versions:update-parent versions:use-latest-releases versions:update-properties versions:commit -DallowMajorUpdates=true
```

Update plugins
```
mvn versions:display-plugin-updates -U
```
## Contributing

1. Feel free to open Pull Requests with your ideas :)
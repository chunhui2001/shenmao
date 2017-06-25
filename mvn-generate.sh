# put archetype-catalog.xml to ~/.m2/repository/org/apache/maven/archetype/archetype-catalog/2.2
mvn -X archetype:generate -DgroupId=com.shenmao.springhibernate -DartifactId=springhibernate -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DarchetypeCatalog=local

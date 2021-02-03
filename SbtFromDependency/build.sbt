name := "SbtFromDependency"

val protobufJarMavenUrl = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.14.0/protobuf-java-3.14.0.jar"
libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.14.0" from protobufJarMavenUrl

val exampleJarUrl = "http://localhost:8080/fetchJar"
libraryDependencies += "example" % "dependency" % "0.0.1" from exampleJarUrl




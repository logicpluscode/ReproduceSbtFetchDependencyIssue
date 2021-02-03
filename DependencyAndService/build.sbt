//this sbt module will serve jar files
//from url http://localhost:8080/fetchJar
val springBootService = ( project in file("Service") )
  .settings(
    libraryDependencies +=  "org.springframework.boot" % "spring-boot-starter-parent" % "2.3.0.RELEASE" pomOnly(),
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % "2.3.0.RELEASE"
  )

//simple sbt module in Dependency directory
//it's source will be packaged as jar file using sbt pack
//and then will be served from url http://localhost:8080/fetchJar
val dependencyModule = ( project in file("Dependency") )
  .settings(
    organization := "example",
    name := "dependency",
    version := "0.0.1",

    crossPaths := false
  )
  .enablePlugins(PackPlugin)

val rootProject = ( project in file(".") )
  .aggregate( dependencyModule, springBootService )
  .settings(
    name := "dependency-and-service",
    //this will only pack source code of
    // Dependency module and
    // skips source of Service module
    Compile / pack := {
      (dependencyModule / Compile / pack).value
    },
    //this will only run source code of
    // Service module and
    // skips source of Service module
    Compile / run := {
      (springBootService / Compile / run).evaluated
    }
  )

//this task will copy jar of Dependency module
//in Service module. So, that it can be served from url http://localhost:8080/fetchJar
lazy val copyDependency = taskKey[Unit]("copy dependency")
copyDependency := {
  val jarsDir = new File("Service/jars/")
  jarsDir.mkdirs()
  println("copying Dependency/target/pack/lib/dependency-0.0.1.jar to Service/jars/dependency-0.0.1.jar")
  IO.copyFile(
    new File("Dependency/target/pack/lib/dependency-0.0.1.jar"),
    new File("Service/jars/dependency-0.0.1.jar")
  )
}

(run in Compile) := ((run in Compile).toTask("")  dependsOn copyDependency dependsOn (pack in Compile) ).value
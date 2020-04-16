name := """movie-multiplex-portal"""
organization := "com.cts"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += javaJdbc
libraryDependencies += javaJpa

val akkaVersion = "2.6.1"
val akkaHttpVersion = "10.1.11"

libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.4.13.Final"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.19"
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-str eam
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http
libraryDependencies += "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http-jackson
libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion
// https://mvnrepository.com/artifact/ma.glasnost.orika/orika-core
libraryDependencies += "ma.glasnost.orika" % "orika-core" % "1.5.4"



PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
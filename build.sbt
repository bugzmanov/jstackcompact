name := "jstackcompact"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint"
)

mainClass in assembly := Some("ru.bugzmanov.jstackcompact.CompactStackApp")
assemblyJarName in assembly := "jstackcompact.jar"
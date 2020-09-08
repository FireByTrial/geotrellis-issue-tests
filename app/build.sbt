name := "geotrellis-tests"
version := "1.0.0"
scalaVersion := Version.scala

val TestsCfgs = "test"

libraryDependencies ++= Seq(
  Dependencies.geotrellisSpark,
  Dependencies.geotrellisRaster,
  Dependencies.geotrellisRasterTestKit % TestsCfgs,
  Dependencies.geotrellisVector,
  Dependencies.geotrellisVectorTestKit % TestsCfgs,
  Dependencies.scalaTest % TestsCfgs
)

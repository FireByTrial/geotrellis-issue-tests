import sbt._

object Dependencies {
  val geotrellisSpark  = "org.locationtech.geotrellis" %% "geotrellis-spark" % Version.geotrellis
  val geotrellisRaster = "org.locationtech.geotrellis" %% "geotrellis-raster" % Version.geotrellis
  val geotrellisRasterTestKit = "org.locationtech.geotrellis" %% "geotrellis-raster-testkit" % Version.geotrellis
  val geotrellisVector = "org.locationtech.geotrellis" %% "geotrellis-vector" % Version.geotrellis
  val geotrellisVectorTestKit = "org.locationtech.geotrellis" %% "geotrellis-vector-testkit" % Version.geotrellis
  val geotrellisUtil   = "org.locationtech.geotrellis" %% "geotrellis-util" % Version.geotrellis
  val geotrellisproj4j = "org.locationtech.geotrellis" %% "geotrellis-proj4" % Version.geotrellis
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
}

package com.geotrellis.reproject

import org.scalatest._
import geotrellis.proj4.CRS
import geotrellis.raster.{CellSize, RasterExtent}
import geotrellis.raster.io.geotiff.MultibandGeoTiff
import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.raster.reproject.Reproject.Options
import geotrellis.raster.resample.{Bilinear, NearestNeighbor}
import geotrellis.raster.testkit.RasterMatchers

class ReprojectCalculator extends FunSuite
  with BeforeAndAfterAll
  with RasterMatchers
{
  def read(path:String):MultibandGeoTiff = GeoTiffReader.readMultiband(
    getClass.getResource(path).getPath
  )

  val sourceCrs = CRS.fromEpsgCode(32613)
  val targetCrs = CRS.fromEpsgCode(4326)
  val targetCellSize = CellSize(2.5,2.5)

  lazy val sourceTiff = read("/img/generic-source/5b_checkerboard.tif")

  test("resample should equal expected bounds and extent") {
    val source_extent = sourceTiff.extent
    val targetRasterExtent = RasterExtent(source_extent, targetCellSize)
    // the following assertions are likely redundant but probably still not a bad idea to check
    targetRasterExtent.cols shouldBe sourceTiff.rasterExtent.cols * (sourceTiff.rasterExtent.cellSize.width / targetCellSize.width)
    targetRasterExtent.rows shouldBe sourceTiff.rasterExtent.rows * (sourceTiff.rasterExtent.cellSize.height / targetCellSize.height)
    targetRasterExtent.cellheight shouldBe targetCellSize.height
    targetRasterExtent.cellwidth shouldBe targetCellSize.width

    val resampled = sourceTiff.raster.resample(targetRasterExtent, method = NearestNeighbor)
    resampled.rasterExtent shouldBe targetRasterExtent

    val reprojected = resampled.reproject(sourceCrs, targetCrs, Options(method = Bilinear))
    GeoTiffWriter.write(MultibandGeoTiff(reprojected, targetCrs), path="/tmp/reproject_test_output_GT341.tif")
    reprojected.rasterExtent.cols shouldBe 271
    reprojected.rasterExtent.rows shouldBe 122
    reprojected.rasterExtent.cellwidth shouldBe 3.149631228464766E-5 +- 0.0000001
    reprojected.rasterExtent.cellwidth shouldBe 3.14963122841609E-5 +- 0.0000001
    reprojected.extent.xmin shouldBe -104.59864 +- 0.00001
    reprojected.extent.ymin shouldBe 50.40546 +- 0.00001
    reprojected.extent.xmax shouldBe -104.5901 +- 0.00001
    reprojected.extent.ymax shouldBe 50.409301 +- 0.00001
    reprojected.extent.width shouldBe 0.0085355 +- 0.00001
    reprojected.extent.height shouldBe 0.00384255 +- 0.00001
  }
}

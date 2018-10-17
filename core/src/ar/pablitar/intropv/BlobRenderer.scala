package ar.pablitar.intropv

import ar.com.pablitar.libgdx.commons.rendering.Renderers
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import ar.com.pablitar.libgdx.commons.extensions.ShapeRendererExtensions._
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

object BlobRenderer {

  def blobCircle(sr: ShapeRenderer, color: Color) = {
    sr.circleWithBorder(4, Color.LIGHT_GRAY, ChainedBlob.SIZE / 2, color)(0, 0)
    sr.setColor(Color.BLACK)
    sr.rectLine(0, 0, 0, ChainedBlob.SIZE / 2, 2)
  }

  def render(blob: ChainedBlob, renderers: Renderers): Unit = {
    renderers.withShapes(ShapeType.Filled) { sr =>
      val oldTransform = sr.getTransformMatrix.cpy()
      sr.setTransformMatrix(sr.getTransformMatrix.mul(blob.matrix4))
      blob.child.foreach { child =>
        sr.setColor(Color.WHITE)
        sr.rectLine(new Vector2(), child.transform.getTranslation(new Vector2()), 2)
      }
      blobCircle(sr, if (blob.selected) Color.WHITE else blob.color)
      blob.child.foreach(render(_, renderers))
      sr.setTransformMatrix(oldTransform)
    }
  }
}

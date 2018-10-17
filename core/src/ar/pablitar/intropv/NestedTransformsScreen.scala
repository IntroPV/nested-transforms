package ar.pablitar.intropv

import ar.com.pablitar.libgdx.commons.rendering.Renderers
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import ar.com.pablitar.libgdx.commons.extensions.InputExtensions._

class NestedTransformsScreen() extends ScreenAdapter {
  val blob = ChainedBlob.create(new Vector2(640, 200), 3)
  val renderers = new Renderers()

  override def render(delta: Float): Unit = {
    Gdx.input.touchPositionOption().foreach{ position =>
      position.y = 720 - position.y
      Gdx.app.log("Selecting", "At position: " + position)
      blob.deselect()
      blob.reactToTouchPosition(position)
    }

    blob.update(delta)
    renderers.withRenderCycle(0.1f, 0.1f, 0.1f) {
      BlobRenderer.render(blob, renderers)
    }
  }
}

package ar.pablitar.intropv

import ar.com.pablitar.libgdx.commons.{AngleDirection, ColorUtils}
import com.badlogic.gdx.math._
import ar.com.pablitar.libgdx.commons.extensions.InputExtensions._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class ChainedBlob(position: Vector2, var child: Option[ChainedBlob] = None) {

  var selected = false
  val MOVEMENT_SPEED = 300
  val ROTATION_SPEED = 180

  def deselect() : Unit = {
    selected = false
    child.foreach(_.deselect())
  }


  def reactToTouchPosition(position: Vector2): Unit = {
    this.inverse.applyTo(position)
    if (ChainedBlob.CIRCLE.contains(position)) {
      this.selected = true
    } else {
      child.foreach(_.reactToTouchPosition(position))
    }
  }

  def update(delta: Float): Unit = {
    if(selected) {
      Gdx.input.arrowsDirectionOption.foreach { v =>
        this.transform.translate(v.scl(MOVEMENT_SPEED * delta))
      }

      if(Gdx.input.isKeyPressed(Keys.E)) {
        this.transform.rotate(ROTATION_SPEED * AngleDirection.Clockwise.sign * delta)
      }

      if(Gdx.input.isKeyPressed(Keys.Q)) {
        this.transform.rotate(ROTATION_SPEED * AngleDirection.CounterClockwise.sign * delta)
      }
    }

    child.foreach(_.update(delta))
  }

  val transform = new Affine2().setToTranslation(position)

  def matrix4 = new Matrix4().setAsAffine(transform)

  def inverse = new Affine2(transform).inv()
  val color = ColorUtils.fromHSV(MathUtils.random(360), 200, 200)
}

object ChainedBlob {
  val SIZE = 80
  val CIRCLE = new Circle(0, 0, SIZE / 2)

  def create(position: Vector2, levels: Int): ChainedBlob = {
    val parentBlob = new ChainedBlob(position)
    (1 until levels).foldLeft(parentBlob) { (blob, i) =>
      val newBlob = new ChainedBlob(new Vector2(0, SIZE))
      blob.child = Some(newBlob)
      newBlob
    }
    parentBlob
  }
}

package ar.pablitar.intropv

import com.badlogic.gdx.Game

class NestedTransforms extends Game {
  override def create(): Unit = {
    this.setScreen(new NestedTransformsScreen())
  }
}

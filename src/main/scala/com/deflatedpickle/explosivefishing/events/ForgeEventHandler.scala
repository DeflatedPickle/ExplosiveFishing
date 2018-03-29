package com.deflatedpickle.explosivefishing.events

import net.minecraftforge.event.world.ExplosionEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ForgeEventHandler {
  @SubscribeEvent
  def onExplosionEventStart(event: ExplosionEvent.Start): Unit = {
  }

  @SubscribeEvent
  def onExplosionEventDetonate(event: ExplosionEvent.Detonate): Unit = {
  }
}

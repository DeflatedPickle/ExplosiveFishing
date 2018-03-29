package com.deflatedpickle.explosivefishing.events

import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.world.ExplosionEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ForgeEventHandler {
  @SubscribeEvent
  def onExplosionEventStart(event: ExplosionEvent.Start): Unit = {
  }

  @SubscribeEvent
  def onExplosionEventDetonate(event: ExplosionEvent.Detonate): Unit = {
    val x = event.getExplosion.getPosition.x
    val y = event.getExplosion.getPosition.y
    val z = event.getExplosion.getPosition.z

    val position = new BlockPos(x, y, z)

    if (event.getWorld.getBlockState(position).getBlock == Blocks.WATER) {
    }
  }
}

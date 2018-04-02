package com.deflatedpickle.explosivefishing.events

import java.util.Random

import com.deflatedpickle.picklelib.water.WaterBody
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.storage.loot.{LootContext, LootTableList}
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
      val random = new Random()

      val waterBody = new WaterBody(event.getWorld, position)

      val fish = Math.min(waterBody.getSimpleVolume, event.getExplosion.size.toInt) * 3
      val amount = 2 + random.nextInt(fish)

      for (_ <- 0 to amount) {
        val loot = event.getWorld.getLootTableManager.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(new Random(), new LootContext.Builder(event.getWorld.getMinecraftServer.getWorld(0)).build())

        val itemEntity = new EntityItem(event.getWorld, x + random.nextInt(waterBody.getSimpleWidth), y + 1, z + random.nextInt(waterBody.getSimpleLength), loot.get(0))
        event.getWorld.spawnEntity(itemEntity)
      }
    }
  }
}

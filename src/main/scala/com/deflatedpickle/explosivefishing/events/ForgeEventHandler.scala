package com.deflatedpickle.explosivefishing.events

import java.util.Random

import com.deflatedpickle.explosivefishing.config.GeneralConfig
import com.deflatedpickle.picklelib.api.IFishable
import com.deflatedpickle.picklelib.world.BlockBody
import net.minecraft.block.material.Material
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

    if (event.getWorld.getBlockState(position).getMaterial == Material.WATER) {
      val block = event.getWorld.getBlockState(position).getBlock

      if (block == Blocks.WATER || block.isInstanceOf[IFishable] && block.asInstanceOf[IFishable].isFishable) {
        val random = new Random()

        val waterBody = new BlockBody(event.getWorld, position, block)

        val fish = Math.min(waterBody.getSimpleVolume, event.getExplosion.size.toInt) * GeneralConfig.lootPerBlock
        val amount = GeneralConfig.minimumLoot + random.nextInt(fish)

        for (_ <- 0 to amount) {
          val loot = event.getWorld.getLootTableManager.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(new Random(), new LootContext.Builder(event.getWorld.getMinecraftServer.getWorld(0)).build())

          val itemEntity = new EntityItem(event.getWorld, x + random.nextInt(event.getExplosion.size.toInt / 2), y + 1, z + random.nextInt(event.getExplosion.size.toInt / 2), loot.get(0))
          event.getWorld.spawnEntity(itemEntity)
        }
      }
    }
  }
}

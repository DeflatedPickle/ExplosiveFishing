package com.deflatedpickle.explosivefishing.events

import java.util.Random

import com.deflatedpickle.explosivefishing.config.GeneralConfig
import com.deflatedpickle.picklelib.api.IFishable
import com.deflatedpickle.picklelib.world.BlockBody
import net.minecraft.block.material.Material
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldServer
import net.minecraft.world.storage.loot.{LootContext, LootTableList}
import net.minecraftforge.event.world.ExplosionEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ForgeEventHandler {
  private var spawnCounter = 0
  private var configPool: ResourceLocation = _

  @SubscribeEvent
  def onExplosionEventDetonate(event: ExplosionEvent.Detonate): Unit = {
    configPool = GeneralConfig.lootPool match {
      case GeneralConfig.PoolEnum.GAMEPLAY_FISHING => LootTableList.GAMEPLAY_FISHING
      case GeneralConfig.PoolEnum.GAMEPLAY_FISHING_FISH => LootTableList.GAMEPLAY_FISHING_FISH
      case GeneralConfig.PoolEnum.GAMEPLAY_FISHING_JUNK => LootTableList.GAMEPLAY_FISHING_JUNK
      case GeneralConfig.PoolEnum.GAMEPLAY_FISHING_TREASURE => LootTableList.GAMEPLAY_FISHING_TREASURE
    }

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

        val explosionSize = event.getExplosion.size.toInt + 1

        spawnCounter = amount
        spawnItems(event, x, y, z, random, explosionSize)
      }
    }
  }

  def spawnItems(event: ExplosionEvent, x: Double, y: Double, z: Double, random: Random, explosionSize: Int): Unit = {
    if (spawnCounter >= 1) {
      val loot = event.getWorld.getLootTableManager.getLootTableFromLocation(configPool).generateLootForPools(new Random(), new LootContext.Builder(event.getWorld.asInstanceOf[WorldServer]).build())

      val itemEntity = new EntityItem(event.getWorld, x + random.nextInt(explosionSize) - (explosionSize / 2), y + random.nextInt(explosionSize) - (explosionSize / 2), z + random.nextInt(explosionSize) - (explosionSize / 2), loot.get(0))
      event.getWorld.spawnEntity(itemEntity)

      spawnCounter -= 1
      spawnItems(event, x, y, z, random, explosionSize)
    }
  }
}

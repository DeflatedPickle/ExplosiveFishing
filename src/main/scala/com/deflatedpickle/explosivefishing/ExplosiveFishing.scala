package com.deflatedpickle.explosivefishing

import com.deflatedpickle.explosivefishing.proxy.CommonProxy
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, modLanguage = "scala")
object ExplosiveFishing {
  @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
  var proxy: CommonProxy = _

  val log: Logger = LogManager.getLogger(Reference.NAME)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    log.info("Starting Init.")
    proxy.init(event)
    log.info("Finished Init.")
  }
}

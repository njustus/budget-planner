package common

import com.google.inject.{AbstractModule, Inject}
import com.typesafe.config.Config
import play.api.{Configuration, Environment, Logging}
import pureconfig._
import pureconfig.generic.auto._

class BudgetPlannerModule(environment: Environment, config: Configuration) extends AbstractModule with Logging {

  override def configure(): Unit = {
    setupBPConfig()
  }

  private def setupBPConfig(): Unit = {
    val bpConfig = ConfigSource.fromConfig(config.get[Config]("budget-planner"))
                      .loadOrThrow[bpconfig.BPConfig]

    bind(classOf[bpconfig.BPConfig]).toInstance(bpConfig)
    logger.info(s"configuration loaded: $bpConfig")
  }
}

package common

object bpconfig {

  case class BPConfig(authentication: AuthConfig, api: ApiConfig)

  case class AuthConfig(publicKey: String)

  case class ApiConfig(pageSize: Int)
}

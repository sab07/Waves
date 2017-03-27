package scorex.api.http

import com.wavesplatform.http.RouteSpec

class DebugRouteSpec extends RouteSpec("/debug/") {
  routePath("blocks/{howMany}") in pending
  routePath("state/") in pending
  routePath("state/{height}") in pending
  routePath("stateWaves") in pending
  routePath("info") in pending
  routePath("settings") in pending
}

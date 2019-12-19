package kata.game_of_three.rest_api.client.auth;

import kata.game_of_three.rest_api.client.Pair;

import java.util.Map;
import java.util.List;

public interface Authentication {
  /** Apply authentication settings to header and query params. */
  void applyToParams(List<Pair> queryParams, Map<String, String> headerParams);
}

package kata.game_of_three.rest_api.client;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2019-12-20T10:36:16.813+01:00")
public class Configuration {
  private static ApiClient defaultApiClient = new ApiClient();

  /**
   * Get the default API client, which would be used when creating API
   * instances without providing an API client.
   */
  public static ApiClient getDefaultApiClient() {
    return defaultApiClient;
   }

  /**
   * Set the default API client, which would be used when creating API
   * instances without providing an API client.
   */
  public static void setDefaultApiClient(ApiClient apiClient) {
    defaultApiClient = apiClient;
  }
}

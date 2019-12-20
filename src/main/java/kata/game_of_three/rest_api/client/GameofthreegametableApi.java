package kata.game_of_three.rest_api.client;

import kata.game_of_three.rest_api.client.ApiCallback;
import kata.game_of_three.rest_api.client.ApiClient;
import kata.game_of_three.rest_api.client.ApiException;
import kata.game_of_three.rest_api.client.ApiResponse;
import kata.game_of_three.rest_api.client.Configuration;
import kata.game_of_three.rest_api.client.Pair;
import kata.game_of_three.rest_api.client.ProgressRequestBody;
import kata.game_of_three.rest_api.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

import kata.game_of_three.Move;
import kata.game_of_three.PlayerInvitation;
import java.util.UUID;


import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class GameofthreegametableApi {
  private ApiClient apiClient;

  public GameofthreegametableApi() {
    this(Configuration.getDefaultApiClient());
  }

  public GameofthreegametableApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  
  /* Build call for acceptMove */
  private Call acceptMoveCall(Move body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
    Object localVarPostBody = body;
    

    // create path and map variables
    String localVarPath = "/game_of_three/game_table/accept_move".replaceAll("\\{format\\}","json");

    List<Pair> localVarQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
      
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    if(progressListener != null) {
      apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
      @Override
      public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
        }
      });
    }

    String[] localVarAuthNames = new String[] {  };
    return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
  }

  /**
   * 
   * 
   * @param body  (optional)
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public void acceptMove(Move body) throws ApiException {
    acceptMoveWithHttpInfo(body);
  }

  /**
   * 
   * 
   * @param body  (optional)
   * @return ApiResponse<Void>
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public ApiResponse<Void> acceptMoveWithHttpInfo(Move body) throws ApiException {
    Call call = acceptMoveCall(body, null, null);
    return apiClient.execute(call);
  }

  /**
   *  (asynchronously)
   * 
   * @param body  (optional)
   * @param callback The callback to be executed when the API call finishes
   * @return The request call
   * @throws ApiException If fail to process the API call, e.g. serializing the request body object
   */
  public Call acceptMoveAsync(Move body, final ApiCallback<Void> callback) throws ApiException {

    ProgressResponseBody.ProgressListener progressListener = null;
    ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

    if (callback != null) {
      progressListener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
          callback.onDownloadProgress(bytesRead, contentLength, done);
        }
      };

      progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
        @Override
        public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
          callback.onUploadProgress(bytesWritten, contentLength, done);
        }
      };
    }

    Call call = acceptMoveCall(body, progressListener, progressRequestListener);
    apiClient.executeAsync(call, callback);
    return call;
  }
  
  /* Build call for invitePlayer */
  private Call invitePlayerCall(PlayerInvitation body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
    Object localVarPostBody = body;
    

    // create path and map variables
    String localVarPath = "/game_of_three/game_table/player_invitation".replaceAll("\\{format\\}","json");

    List<Pair> localVarQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
                    "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {

    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    if(progressListener != null) {
      apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
      @Override
      public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
        }
      });
    }

    String[] localVarAuthNames = new String[] {  };
    return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
  }

  /**
   * 
   * 
   * @param body  (optional)
   * @return UUID
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public UUID invitePlayer(PlayerInvitation body) throws ApiException {
    ApiResponse<UUID> resp = invitePlayerWithHttpInfo(body);
    return resp.getData();
  }

  /**
   * 
   * 
   * @param body  (optional)
   * @return ApiResponse<UUID>
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
   */
  public ApiResponse<UUID> invitePlayerWithHttpInfo(PlayerInvitation body) throws ApiException {
    Call call = invitePlayerCall(body, null, null);
    Type localVarReturnType = new TypeToken<UUID>(){}.getType();
    return apiClient.execute(call, localVarReturnType);
  }

  /**
   *  (asynchronously)
   * 
   * @param body  (optional)
   * @param callback The callback to be executed when the API call finishes
   * @return The request call
   * @throws ApiException If fail to process the API call, e.g. serializing the request body object
   */
  public Call invitePlayerAsync(PlayerInvitation body, final ApiCallback<UUID> callback) throws ApiException {

    ProgressResponseBody.ProgressListener progressListener = null;
    ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

    if (callback != null) {
      progressListener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
          callback.onDownloadProgress(bytesRead, contentLength, done);
        }
      };

      progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
        @Override
        public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
          callback.onUploadProgress(bytesWritten, contentLength, done);
        }
      };
    }

    Call call = invitePlayerCall(body, progressListener, progressRequestListener);
    Type localVarReturnType = new TypeToken<UUID>(){}.getType();
    apiClient.executeAsync(call, localVarReturnType, callback);
    return call;
  }
  
}


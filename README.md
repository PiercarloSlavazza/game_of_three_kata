```
java -Dapis -DsupportingFiles -jar ..\..\kie\swagger-codegen\modules\swagger-codegen-cli\target\swagger-codegen-cli.jar generate -i http://127.0.0.1:8700/game_of_three_rest_api/swagger.json -l java --invoker-package kata.game_of_three.rest_api.client --api-package kata.game_of_three.rest_api.client --model-package kata.game_of_three.rest_api.client --library okhttp-gson --import-mappings 
```
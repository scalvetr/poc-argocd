# Deploy Sample application to local registry


## Deploy lo local registry
```shell
cd sample-app
./gradlew bootBuildImage --imageName=localhost:5001/sample-app:0.1

# push image to registry
docker push localhost:5001/sample-app:0.1

# test
docker run -p 8080:8080 localhost:5001/sample-app:0.1
```

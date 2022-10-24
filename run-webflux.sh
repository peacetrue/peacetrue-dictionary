#!/bin/bash

app-name="peacetrue-dictionary"
app-version="1.0.0-SNAPSHOT"

git clone -b "${app-version}" https://peacetrue.cn/peacetrue-application
cd peacetrue-application || exit
tee peacetrue-application-webflux/extend.gradle <<EOF
dependencies {
  // 服务实现
  implementation "com.github.peacetrue.dictionary:${app-name}-service-impl:${app-version}"
  // 控制器
  implementation "com.github.peacetrue.dictionary:${app-name}-controller:${app-version}"
}
EOF

./gradlew peacetrue-application-webflux:bootRun

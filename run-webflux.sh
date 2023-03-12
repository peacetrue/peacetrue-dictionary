#!/bin/bash

app_name="peacetrue-dictionary"
app_version="1.0.0-SNAPSHOT"

git clone -b "1.0.0" https://github.com/peacetrue/peacetrue-application
cd peacetrue-application || exit
tee peacetrue-application-webflux/extend.gradle <<EOF
dependencies {
  // 服务实现
  implementation "com.github.peacetrue.dictionary:${app_name}-impl-reactive:${app_version}"
  // 控制器
  implementation "com.github.peacetrue.dictionary:${app_name}-controller-reactive:${app_version}"
}
EOF

./gradlew peacetrue-application-webflux:bootRun

#!/bin/bash

app_name="peacetrue-dictionary"
app_version="1.0.0-SNAPSHOT"
target="$workingDir/peacetrue-application-webmvc"

tee "$target/extend/dictionary.gradle" <<EOF
dependencies {
  // 服务实现
  implementation "com.github.peacetrue.dictionary:${app_name}-service-impl-reactor:${app_version}"
  // 控制器
  implementation "com.github.peacetrue.dictionary:${app_name}-controller-reactor:${app_version}"
}
EOF

./gradlew peacetrue-application-webflux:bootRun

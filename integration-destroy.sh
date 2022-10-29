#!/bin/bash

# 销毁项目配置
sed -i '' "s|includeFlat('peacetrue-application-webmvc')||" 'settings.gradle'
sed -i '' "s|includeFlat('peacetrue-application-react-admin')||" 'settings.gradle'

#销毁后端
rm -rf "$workingDir/peacetrue-application-webmvc/extend/dictionary.gradle"

#销毁前端
rm -rf "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-types"
rm -rf "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-values"
echo "" > "$workingDir/peacetrue-application-react-admin/.env.local"

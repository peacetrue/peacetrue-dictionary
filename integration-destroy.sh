#!/bin/bash

# 销毁项目配置
sed -i  "/includeFlat/d" 'settings.gradle'

#销毁后端
rm -rf "$workingDir/peacetrue-application-webmvc/extend/dictionary.gradle"

#销毁前端
rm -rf "$workingDir/peacetrue-application-reactadmin/src/modules/dictionary-types"
rm -rf "$workingDir/peacetrue-application-reactadmin/src/modules/dictionary-values"
sed -i '/require/d' "$workingDir/peacetrue-application-reactadmin/src/modules/localModules.tsx"
echo "" > "$workingDir/peacetrue-application-reactadmin/.env.local"

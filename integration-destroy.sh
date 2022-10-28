#!/bin/bash

# 销毁项目配置
sed -i '' "s|includeFlat('peacetrue-application-webmvc')||" 'settings.gradle'
sed -i '' "s|includeFlat('peacetrue-application-react-admin')||" 'settings.gradle'

rm -rf "$workingDir/peacetrue-application-webmvc/extend/dictionary.gradle"
rm -rf "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-types"
rm -rf "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-values"
echo "" > "$workingDir/peacetrue-application-react-admin/.env.local"

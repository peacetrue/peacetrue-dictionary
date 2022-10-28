#!/bin/bash

# 初始化项目配置
echo "includeFlat('peacetrue-application-webmvc')" >> 'settings.gradle'
echo "includeFlat('peacetrue-application-react-admin')" >> 'settings.gradle'

# 初始化后端
ln -s "$workingDir/peacetrue-dictionary/dictionary.gradle" "$workingDir/peacetrue-application-webmvc/extend/dictionary.gradle"

# 初始化前端
lndir "$workingDir/peacetrue-dictionary/peacetrue-dictionary-react-admin/src/modules/dictionary-types" "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-types"
lndir "$workingDir/peacetrue-dictionary/peacetrue-dictionary-react-admin/src/modules/dictionary-values" "$workingDir/peacetrue-application-react-admin/src/modules/dictionary-values"
printf "REACT_APP_BASE_URL=http://localhost:8080 \nREACT_APP_MODULES=dictionary-types,dictionary-values" > "$workingDir/peacetrue-application-react-admin/.env.local"

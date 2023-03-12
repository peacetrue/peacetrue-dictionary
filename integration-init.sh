#!/bin/bash

# 初始化项目配置
echo "includeFlat('peacetrue-application-webmvc')" >> 'settings.gradle'
echo "includeFlat('peacetrue-application-reactadmin')" >> 'settings.gradle'

# 初始化后端
ln -s "$workingDir/peacetrue-dictionary/dictionary.gradle" "$workingDir/peacetrue-application-webmvc/extend/dictionary.gradle"

# 初始化前端
lndir "$workingDir/peacetrue-dictionary/peacetrue-dictionary-reactadmin/src/modules/dictionary-types" "$workingDir/peacetrue-application-reactadmin/src/modules/dictionary-types"
lndir "$workingDir/peacetrue-dictionary/peacetrue-dictionary-reactadmin/src/modules/dictionary-values" "$workingDir/peacetrue-application-reactadmin/src/modules/dictionary-values"
echo "REACT_APP_BASE_URL=http://localhost:8080" > "$workingDir/peacetrue-application-reactadmin/.env.local"
sed -i '/[/a\(require("./dictionary-types") as LocalModule).default,(require("./dictionary-values") as LocalModule).default,' "$workingDir/peacetrue-application-reactadmin/src/modules/localModules.tsx"
# printf "REACT_APP_BASE_URL=http://localhost:8080 \nREACT_APP_MODULES=dictionary-types,dictionary-values" > "$workingDir/peacetrue-application-reactadmin/.env.local"

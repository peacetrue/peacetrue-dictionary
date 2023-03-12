#!/bin/bash

workingDir="\$workingDir"
backend="peacetrue-application-webmvc"
frontend="peacetrue-application-react-admin"
module="peacetrue-dictionary"
type="dictionary-types"
value="dictionary-values"

tee "integration-init.sh" <<EOF
#!/bin/bash

# 初始化项目配置
echo "includeFlat('$backend')" >> 'settings.gradle'
echo "includeFlat('$frontend')" >> 'settings.gradle'

# 初始化后端
ln -s "$workingDir/$module/dictionary.gradle" "$workingDir/$backend/extend/dictionary.gradle"

# 初始化前端
lndir "$workingDir/$module/${module}-react-admin/src/modules/$type" "$workingDir/$frontend/src/modules/$type"
lndir "$workingDir/$module/${module}-react-admin/src/modules/$value" "$workingDir/$frontend/src/modules/$value"
printf "REACT_APP_BASE_URL=http://localhost:8080 \nREACT_APP_MODULES=$type,$value" > "$workingDir/$frontend/.env.local"
EOF


tee "integration-destroy.sh" <<EOF
#!/bin/bash

# 销毁项目配置
sed -i "s|includeFlat('$backend')||" 'settings.gradle'
sed -i "s|includeFlat('$frontend')||" 'settings.gradle'

#销毁后端
rm -rf "$workingDir/$backend/extend/dictionary.gradle"

#销毁前端
rm -rf "$workingDir/$frontend/src/modules/$type"
rm -rf "$workingDir/$frontend/src/modules/$value"
echo "" > "$workingDir/$frontend/.env.local"
EOF

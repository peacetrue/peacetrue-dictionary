#!/bin/bash

app-name="peacetrue-dictionary"
app-version="0.0.1-SNAPSHOT"

git clone -b "${app-version}" https://peacetrue.cn/peacetrue-application-frontend
cd peacetrue-application-frontend/peacetrue-application-react-admin || exit
pnpm install @peace/dictionary

tee src/modules/index.tsx <<EOF
import {DictionaryTypeResource, DictionaryValueResource} from "@peace/dictionary";

export const Resources = [DictionaryTypeResource, DictionaryValueResource]
EOF

pnpm start

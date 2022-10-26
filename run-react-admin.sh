#!/bin/bash

git clone -b "1.2.1" https://github.com/peacetrue/peacetrue-application-frontend
cd peacetrue-application-frontend/peacetrue-application-react-admin || exit
pnpm install dictionary-react-admin@0.0.0-1

# 设置数据字典 Resource
tee src/modules/index.tsx <<EOF
import {DictionaryTypeResource, DictionaryValueResource} from "dictionary-react-admin";
export const Resources = [DictionaryTypeResource, DictionaryValueResource]
EOF

# 设置数据字典中文方言
tee src/i18n/index.ts <<EOF
import {mergeTranslations} from "react-admin";
import {Messages} from "./Messages_zh";
import {DictionaryTypeMessages, DictionaryValueMessages} from "dictionary-react-admin";

export const getMessages = (locale: string) => mergeTranslations(Messages, DictionaryTypeMessages, DictionaryValueMessages);
EOF

pnpm start

import React from "react";
import {Resource} from "react-admin";

import {DictionaryTypeList} from './list';
import {DictionaryTypeCreate} from './create';
import {DictionaryTypeEdit} from './edit';
import {DictionaryTypeShow} from './show';

export const DictionaryType = {
    list: DictionaryTypeList,
    create: DictionaryTypeCreate,
    edit: DictionaryTypeEdit,
    show: DictionaryTypeShow
};
const DictionaryTypeResource = <Resource options={{label: '字典类型'}} name="dictionary-types" {...DictionaryType} />;
export default DictionaryTypeResource;

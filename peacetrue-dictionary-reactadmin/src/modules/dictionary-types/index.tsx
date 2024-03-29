import * as React from "react";
import {Resource} from "react-admin";

import {DictionaryTypeList} from './List';
import {DictionaryTypeCreate} from './Create';
import {DictionaryTypeEdit} from './Edit';
import {DictionaryTypeShow} from './Show';
import {DictionaryTypeMessages} from "./Messages";

export * from "./Messages"

export const DictionaryType = {
  list: DictionaryTypeList,
  create: DictionaryTypeCreate,
  edit: DictionaryTypeEdit,
  show: DictionaryTypeShow
};
export const DictionaryTypeResource = <Resource key="dictionary-types" name="dictionary-types" {...DictionaryType} />;
export const DictionaryTypeModule = {resource: DictionaryTypeResource, messages: DictionaryTypeMessages};
export default DictionaryTypeModule;

import * as React from "react";
import {Resource} from "react-admin";

import {DictionaryTypeList} from './List';
import {DictionaryTypeCreate} from './Create';
import {DictionaryTypeEdit} from './Edit';
import {DictionaryTypeShow} from './Show';
import {DictionaryTypeMessages} from "./Messages"

export const DictionaryType = {
  list: DictionaryTypeList,
  create: DictionaryTypeCreate,
  edit: DictionaryTypeEdit,
  show: DictionaryTypeShow
};
export const DictionaryTypeResource = <Resource key="dictionary-types" name="dictionary-types" {...DictionaryType} />;
export default DictionaryTypeResource;
export const messages = DictionaryTypeMessages;
export const resource = DictionaryTypeResource;

import * as React from "react";
import {Resource} from "react-admin";

import {DictionaryValueList} from './List';
import {DictionaryValueCreate} from './Create';
import {DictionaryValueEdit} from './Edit';
import {DictionaryValueShow} from './Show';
import BookIcon from '@material-ui/icons/Book';

export const DictionaryValue = {
  list: DictionaryValueList,
  create: DictionaryValueCreate,
  edit: DictionaryValueEdit,
  show: DictionaryValueShow
};
export const DictionaryValueResource = <Resource icon={BookIcon} name="dictionary-values" {...DictionaryValue} />;
export default DictionaryValueResource;
export * from "./Messages"
export * from "./ReferencePropsBuilder"

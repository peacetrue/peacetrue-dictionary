import {PeaceShow} from '@peace/react-admin';
import * as React from 'react';
import {ShowProps, SimpleShowLayout, TextField} from 'react-admin';
import {UserCreateModifyFields} from "@peace/user";

export const DictionaryTypeShow = (props: ShowProps) => {
  console.info('DictionaryTypeShow:', props);
  return (
    <PeaceShow {...props}>
      <SimpleShowLayout>
        <TextField source="code"/>
        <TextField source="name"/>
        <TextField source="remark"/>
        {UserCreateModifyFields}
      </SimpleShowLayout>
    </PeaceShow>
  );
};

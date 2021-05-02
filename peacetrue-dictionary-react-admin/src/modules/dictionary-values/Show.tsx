import * as React from 'react';
import {PeaceShow} from 'peacetrue-react-admin';
import {ReferenceField, ShowProps, SimpleShowLayout, TextField} from 'react-admin';
import {UserCreateModifyFields} from "peacetrue-user";

export const DictionaryValueShow = (props: ShowProps) => {
  console.info('DictionaryValueShow:', props);
  return (
    <PeaceShow {...props}>
      <SimpleShowLayout>
        <ReferenceField reference="dictionary-types" source="dictionaryTypeId" link="show">
          <TextField source="name"/>
        </ReferenceField>
        <TextField source="code"/>
        <TextField source="name"/>
        <TextField source="remark"/>
        {UserCreateModifyFields}
      </SimpleShowLayout>
    </PeaceShow>
  );
};

import {PeaceEdit} from 'peacetrue-react-admin';
import * as React from 'react';
import {EditProps, maxLength, required, SimpleForm, TextField, TextInput} from 'react-admin';
import {UserCreateModifyFields} from "peacetrue-user";

export const DictionaryTypeEdit = (props: EditProps) => {
  console.info('DictionaryTypeEdit:', props);
  return (
    <PeaceEdit {...props}>
      <SimpleForm>
        <TextField source="code"/>
        <TextInput source="name" validate={[required(), maxLength(32)]}/>
        <TextInput source="remark" validate={[maxLength(255)]} fullWidth multiline/>
        {UserCreateModifyFields}
      </SimpleForm>
    </PeaceEdit>
  );
};

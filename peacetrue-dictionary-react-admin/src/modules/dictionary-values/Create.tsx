import * as React from 'react';
import {
  Create,
  CreateProps,
  maxLength,
  ReferenceInput,
  required,
  SelectInput,
  SimpleForm,
  TextInput
} from 'react-admin';

export const DictionaryValueCreate = (props: CreateProps) => {
  console.info('DictionaryValueCreate:', props);
  return (
    <Create {...props}>
      <SimpleForm>
        <ReferenceInput reference="dictionary-types" source="dictionaryTypeId" link="show">
          <SelectInput source="name"/>
        </ReferenceInput>
        <TextInput source="code" validate={[required(), maxLength(32)]}/>
        <TextInput source="name" validate={[required(), maxLength(255)]}/>
        <TextInput source="remark" validate={[maxLength(255)]} fullWidth multiline/>
      </SimpleForm>
    </Create>
  );
};

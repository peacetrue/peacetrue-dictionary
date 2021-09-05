import {PeaceEdit} from '@peace/react-admin';
import * as React from 'react';
import {
  EditProps,
  maxLength,
  NumberInput,
  ReferenceField,
  required,
  SimpleForm,
  TextField,
  TextInput
} from 'react-admin';
import {UserCreateModifyFields} from "@peace/user";

export const DictionaryValueEdit = (props: EditProps) => {
  console.info('DictionaryValueEdit:', props);
  return (
    <PeaceEdit {...props}>
      <SimpleForm>
        <ReferenceField reference="dictionary-types" source="dictionaryTypeId" link="show">
          <TextField source="name"/>
        </ReferenceField>
        <TextField source="code"/>
        <TextInput source="name" validate={[required(), maxLength(32)]}/>
        <NumberInput source="serialNumber" validate={[required()]}/>
        <TextInput source="remark" validate={[maxLength(255)]} fullWidth multiline/>
        {UserCreateModifyFields}
      </SimpleForm>
    </PeaceEdit>
  );
};

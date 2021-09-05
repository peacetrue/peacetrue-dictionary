import * as React from 'react';
import {
  Datagrid,
  EditButton,
  Filter,
  ListProps,
  ReferenceField,
  ReferenceInput,
  SelectInput,
  TextField,
  TextInput
} from 'react-admin';
import DictionaryValueMessages from "./Messages";
import {ExporterBuilder, PeaceList} from "@peace/react-admin";
import {UserCreatedTimeFilter, UserCreateFields} from "@peace/user";

const Filters = (props: any) => (
  <Filter {...props}>
    <ReferenceInput reference="dictionary-types" source="dictionaryTypeId"
                    link="show" allowEmpty alwaysOn>
      <SelectInput source="name" resettable/>
    </ReferenceInput>
    <TextInput source="code" allowEmpty alwaysOn resettable/>
    <TextInput source="name" allowEmpty alwaysOn resettable/>
    {UserCreatedTimeFilter}
  </Filter>
);

export const DictionaryValueList = (props: ListProps) => {
  console.info('DictionaryValueList:', props);
  return (
    <PeaceList {...props}
               filters={<Filters/>}
               exporter={ExporterBuilder(DictionaryValueMessages.resources["dictionary-values"])}
               sort={undefined}
    >
      <Datagrid rowClick="show">
        <ReferenceField reference="dictionary-types" source="dictionaryTypeId" link="show">
          <TextField source="name"/>
        </ReferenceField>
        <TextField source="code"/>
        <TextField source="name"/>
        <TextField source="serialNumber"/>
        {UserCreateFields}
        <EditButton/>
      </Datagrid>
    </PeaceList>
  )
};

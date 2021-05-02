import * as React from 'react';
import {Datagrid, EditButton, Filter, ListProps, TextField, TextInput} from 'react-admin';
import DictionaryTypeMessages from "./Messages";
import {ExporterBuilder, PeaceList} from "peacetrue-react-admin";
import {UserCreatedTimeFilter, UserCreateFields} from "peacetrue-user";

const Filters = (props: any) => (
  <Filter {...props}>
    <TextInput source="code" allowEmpty alwaysOn resettable/>
    <TextInput source="name" allowEmpty alwaysOn resettable/>
    {UserCreatedTimeFilter}
  </Filter>
);

export const DictionaryTypeList = (props: ListProps) => {
  console.info('DictionaryTypeList:', props);
  return (
    <PeaceList {...props}
               filters={<Filters/>}
               exporter={ExporterBuilder(DictionaryTypeMessages.resources["dictionary-types"])}
    >
      <Datagrid rowClick="show">
        <TextField source="code"/>
        <TextField source="name"/>
        <TextField source="remark"/>
        {UserCreateFields}
        <EditButton/>
      </Datagrid>
    </PeaceList>
  )
};

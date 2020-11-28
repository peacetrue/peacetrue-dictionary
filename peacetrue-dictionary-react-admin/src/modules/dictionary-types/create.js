import React from 'react';
import {Create, required, SimpleForm, TextInput,} from 'react-admin';

export const DictionaryTypeCreate = (props) => {
    console.info('DictionaryTypeCreate:', props);
    return (
        <Create {...props} title={`新建${props.options.label}`}>
            <SimpleForm>
                <TextInput label={'编码'} source="code" validate={[required(),]}/>
                <TextInput label={'名称'} source="name" validate={[required(),]}/>
                <TextInput label={'备注'} source="remark" validate={[required(),]}/>
            </SimpleForm>
        </Create>
    );
};

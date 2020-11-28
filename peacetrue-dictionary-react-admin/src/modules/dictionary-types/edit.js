import React from 'react';
import {Edit, required, SimpleForm, TextInput} from 'react-admin';

export const DictionaryTypeEdit = (props) => {
    console.info('DictionaryTypeEdit:', props);
    return (
        <Edit {...props} title={`${props.options.label}#${props.id}`}>
            <SimpleForm>
                <TextInput label={'编码'} source="code" validate={required()}/>
                <TextInput label={'名称'} source="name" validate={required()}/>
                <TextInput label={'备注'} source="remark" validate={required()}/>
            </SimpleForm>
        </Edit>
    );
};

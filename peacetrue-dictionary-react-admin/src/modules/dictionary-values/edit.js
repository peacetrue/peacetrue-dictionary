import React from 'react';
import {Edit, required, SimpleForm, TextInput} from 'react-admin';

export const DictionaryValueEdit = (props) => {
    console.info('DictionaryValueEdit:', props);
    return (
        <Edit {...props} title={`${props.options.label}#${props.id}`}>
            <SimpleForm>
                <TextInput label={'字典类型'} source="dictionaryTypeId" validate={required()}/>
                <TextInput label={'字典类型'} source="dictionaryTypeCode" validate={required()}/>
                <TextInput label={'编码'} source="code" validate={required()}/>
                <TextInput label={'名称'} source="name" validate={required()}/>
                <TextInput label={'备注'} source="remark" validate={required()}/>
            </SimpleForm>
        </Edit>
    );
};

import React from 'react';
import {Create, required, SimpleForm, TextInput,} from 'react-admin';

export const DictionaryValueCreate = (props) => {
    console.info('DictionaryValueCreate:', props);
    return (
        <Create {...props} title={`新建${props.options.label}`}>
            <SimpleForm>
                <TextInput label={'字典类型'} source="dictionaryTypeId" validate={[required(),]}/>
                <TextInput label={'字典类型'} source="dictionaryTypeCode" validate={[required(),]}/>
                <TextInput label={'编码'} source="code" validate={[required(),]}/>
                <TextInput label={'名称'} source="name" validate={[required(),]}/>
                <TextInput label={'备注'} source="remark" validate={[required(),]}/>
            </SimpleForm>
        </Create>
    );
};

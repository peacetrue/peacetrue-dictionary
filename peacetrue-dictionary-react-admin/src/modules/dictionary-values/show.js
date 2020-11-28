import React from 'react';
import {DateField, Show, SimpleShowLayout, TextField} from 'react-admin';

export const DictionaryValueShow = (props) => {
    console.info('DictionaryValueShow:', props);
    return (
        <Show {...props} title={`${props.options.label}#${props.id}`}>
            <SimpleShowLayout>
                <TextField label={'字典类型'} source="dictionaryTypeId"/>
                <TextField label={'字典类型'} source="dictionaryTypeCode"/>
                <TextField label={'编码'} source="code"/>
                <TextField label={'名称'} source="name"/>
                <TextField label={'备注'} source="remark"/>
                <TextField label={'创建者主键'} source="creatorId"/>
                <DateField label={'创建时间'} source="createdTime" showTime/>
                <TextField label={'修改者主键'} source="modifierId"/>
                <DateField label={'最近修改时间'} source="modifiedTime" showTime/>
            </SimpleShowLayout>
        </Show>
    );
};

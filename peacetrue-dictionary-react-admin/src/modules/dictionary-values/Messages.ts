import {Messages} from "@peace/user";

export const DictionaryValueMessages = {
  resources: {
    "dictionary-values": {
      name: '字典值',
      fields: {
        id: '主键',
        dictionaryTypeId: '字典类型',
        dictionaryTypeCode: '字典类型编码',
        code: '编号',
        name: '名称',
        remark: '备注',
        'serialNumber': '序号',
        ...Messages
      },
    },
  }
}

export default DictionaryValueMessages;

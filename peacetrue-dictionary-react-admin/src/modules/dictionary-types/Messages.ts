import {Messages} from "@peace/user";

export const DictionaryTypeMessages = {
  resources: {
    "dictionary-types": {
      name: '字典类型',
      fields: {
        code: '编号',
        name: '名称',
        remark: '备注',
        'serialNumber': '序号',
        ...Messages
      },
    },
  }
}

export default DictionaryTypeMessages;

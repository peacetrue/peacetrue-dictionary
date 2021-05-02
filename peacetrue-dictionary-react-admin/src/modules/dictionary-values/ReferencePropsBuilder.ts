export const ReferencePropsBuilder = (source: string, dictionaryTypeCode: string) => ({
  source: `${source}Id`,
  reference: "dictionary-values",
  filter: {dictionaryTypeCode: dictionaryTypeCode},
  perPage: 200,
  sort: {field: 'serialNumber', order: 'ASC'}
});

export default ReferencePropsBuilder;

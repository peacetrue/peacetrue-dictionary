import DictionaryTypeModule from "./modules/dictionary-types";
import DictionaryValueModule from "./modules/dictionary-values";

export {
  DictionaryType, DictionaryTypeResource, DictionaryTypeMessages, DictionaryTypeModule
} from "./modules/dictionary-types"

export {
  DictionaryValue, DictionaryValueResource, DictionaryValueMessages, DictionaryValueModule
} from "./modules/dictionary-values"

export default [
  DictionaryTypeModule,
  DictionaryValueModule,
]

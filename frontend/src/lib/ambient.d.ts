type NestedPartial<T> = T extends Function
  ? T
  : T extends Array<any>
  ? Array<NestedPartial<T[number]>>
  : T extends object
  ? { [P in keyof T]?: NestedPartial<T[P]> }
  : T;

type PartialBy<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;
type NestedPartialBy<T, K extends PropertyKey = PropertyKey> = T extends Function
  ? T
  : T extends Array<any>
  ? Array<NestedPartialBy<T[number], K>>
  : T extends object
  ? PartialBy<{ [P in keyof T]: NestedPartialBy<T[P], K> }, K>
  : T;

type NestedRequiredBy<T, K extends PropertyKey = PropertyKey> = T extends Function
  ? T
  : T extends Array<any>
  ? Array<NestedRequiredBy<T[number], K>>
  : T extends object
  ? PartialBy<{ [P in keyof T]: NestedRequiredBy<T[P], K> }, Exclude<keyof T, K>> &
  Required<Pick<T, Extract<keyof T, K>>>
  : T;

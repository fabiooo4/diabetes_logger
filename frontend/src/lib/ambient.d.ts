type PartialBy<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;
type NestedPartialBy<T, K extends PropertyKey = PropertyKey> =
  T extends Function ? T :
  T extends Array<any> ? Array<NestedPartialBy<T[number], K>> :
  T extends object ? PartialBy<{ [P in keyof T]: NestedPartialBy<T[P], K> }, K> :
  T;

// Create the opposite of nestedpartialby where the keys provided are required and the rest are optional
// for example NestedRequiredBy<User, 'email' | 'password'> will make email and password required and the rest optional
type NestedRequiredBy<T, K extends PropertyKey = PropertyKey> =
  T extends Function ? T :
  T extends Array<any> ? Array<NestedRequiredBy<T[number], K>> :
  T extends object ? PartialBy<{ [P in keyof T]: NestedRequiredBy<T[P], K> }, Exclude<keyof T, K>> & Required<Pick<T, Extract<keyof T, K>>> :
  T;

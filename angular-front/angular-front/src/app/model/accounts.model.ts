export interface Account{
  id? : string,
  balance?: number,
  type? : string,
  createAt?:string,
  customerId?:number,

}
export enum RequestStatus{
  SUCCESS , ERROR
}
export interface AccountListState{
  accounts?: Account[],
  status?: RequestStatus,
  errorMessage? : String,

}

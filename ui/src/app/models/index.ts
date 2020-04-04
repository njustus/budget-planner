export interface Account {
  name: string
  _id: string
  totalAmount: number
}

export interface Budget {
  name: String
  description?: String
  accounts: Account[]
  investors: string[]
  owner?: string
  _id: string
}

export interface Payment {
  name: String
  description?: string
  amount: number
  date: Date
  owner?: string
  _accountId: string
  _id: string
}
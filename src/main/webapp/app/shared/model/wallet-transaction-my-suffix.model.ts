import dayjs from 'dayjs';
import { IWalletMySuffix } from 'app/shared/model/wallet-my-suffix.model';
import { WalletTransactionType } from 'app/shared/model/enumerations/wallet-transaction-type.model';
import { WalletTransactionStatus } from 'app/shared/model/enumerations/wallet-transaction-status.model';

export interface IWalletTransactionMySuffix {
  id?: number;
  amount?: number | null;
  type?: keyof typeof WalletTransactionType | null;
  status?: keyof typeof WalletTransactionStatus | null;
  createAt?: dayjs.Dayjs | null;
  wallet?: IWalletMySuffix | null;
}

export const defaultValue: Readonly<IWalletTransactionMySuffix> = {};

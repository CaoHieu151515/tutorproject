import dayjs from 'dayjs';
import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';

export interface IThirdPartyTransactionMySuffix {
  id?: number;
  thirdPartyId?: string;
  transactionDate?: dayjs.Dayjs;
  walletTransaction?: IWalletTransactionMySuffix | null;
}

export const defaultValue: Readonly<IThirdPartyTransactionMySuffix> = {};

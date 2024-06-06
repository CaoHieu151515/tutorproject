import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IWalletMySuffix {
  id?: number;
  amount?: number | null;
  appUser?: IAppUserMySuffix | null;
}

export const defaultValue: Readonly<IWalletMySuffix> = {};

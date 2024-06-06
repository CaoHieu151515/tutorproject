import { IIdentityCardMySuffix } from 'app/shared/model/identity-card-my-suffix.model';

export interface IMediaMySuffix {
  id?: number;
  url?: string | null;
  identityCard?: IIdentityCardMySuffix | null;
}

export const defaultValue: Readonly<IMediaMySuffix> = {};

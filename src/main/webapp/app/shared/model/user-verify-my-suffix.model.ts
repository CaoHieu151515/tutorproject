import { IIdentityCardMySuffix } from 'app/shared/model/identity-card-my-suffix.model';

export interface IUserVerifyMySuffix {
  id?: number;
  rating?: number | null;
  school?: string | null;
  studentID?: string | null;
  major?: string | null;
  graduationYear?: number | null;
  identityCard?: IIdentityCardMySuffix | null;
}

export const defaultValue: Readonly<IUserVerifyMySuffix> = {};

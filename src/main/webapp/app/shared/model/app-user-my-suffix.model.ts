import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { IUserVerifyMySuffix } from 'app/shared/model/user-verify-my-suffix.model';
import { IUser } from 'app/shared/model/user.model';
import { IRatingMySuffix } from 'app/shared/model/rating-my-suffix.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';

export interface IAppUserMySuffix {
  id?: number;
  beTutor?: boolean | null;
  gender?: keyof typeof GenderType | null;
  bankAccountNumber?: string | null;
  bankName?: string | null;
  walletAddress?: string | null;
  tutor?: ITutorMySuffix | null;
  userVerify?: IUserVerifyMySuffix | null;
  user?: IUser | null;
  rating?: IRatingMySuffix | null;
}

export const defaultValue: Readonly<IAppUserMySuffix> = {
  beTutor: false,
};

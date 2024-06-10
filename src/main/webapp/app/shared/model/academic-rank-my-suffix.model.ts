import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { IUserVerifyMySuffix } from 'app/shared/model/user-verify-my-suffix.model';
import { Rank } from 'app/shared/model/enumerations/rank.model';

export interface IAcademicRankMySuffix {
  id?: number;
  type?: keyof typeof Rank | null;
  media?: IMediaMySuffix | null;
  userVerify?: IUserVerifyMySuffix | null;
}

export const defaultValue: Readonly<IAcademicRankMySuffix> = {};

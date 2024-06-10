import dayjs from 'dayjs';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';

export interface IFollowMySuffix {
  id?: number;
  createDate?: dayjs.Dayjs | null;
  followerAppUser?: IAppUserMySuffix | null;
  followedTutor?: ITutorMySuffix | null;
}

export const defaultValue: Readonly<IFollowMySuffix> = {};

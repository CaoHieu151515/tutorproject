import dayjs from 'dayjs';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IRatingMySuffix {
  id?: number;
  rating?: number;
  comment?: string | null;
  hours?: number | null;
  date?: dayjs.Dayjs;
  tutor?: ITutorMySuffix | null;
  appUser?: IAppUserMySuffix | null;
}

export const defaultValue: Readonly<IRatingMySuffix> = {};

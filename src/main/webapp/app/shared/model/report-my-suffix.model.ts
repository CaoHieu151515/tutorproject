import dayjs from 'dayjs';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';

export interface IReportMySuffix {
  id?: number;
  reportDetails?: string | null;
  time?: dayjs.Dayjs | null;
  appUser?: IAppUserMySuffix | null;
  tutor?: ITutorMySuffix | null;
}

export const defaultValue: Readonly<IReportMySuffix> = {};

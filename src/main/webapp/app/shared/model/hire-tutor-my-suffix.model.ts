import dayjs from 'dayjs';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { HireStatus } from 'app/shared/model/enumerations/hire-status.model';

export interface IHireTutorMySuffix {
  id?: number;
  timeHire?: number | null;
  totalPrice?: number | null;
  status?: keyof typeof HireStatus | null;
  startAt?: dayjs.Dayjs | null;
  endAt?: dayjs.Dayjs | null;
  appUser?: IAppUserMySuffix | null;
  tutor?: ITutorMySuffix | null;
}

export const defaultValue: Readonly<IHireTutorMySuffix> = {};

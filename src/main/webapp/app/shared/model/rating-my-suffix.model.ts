import dayjs from 'dayjs';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';

export interface IRatingMySuffix {
  id?: number;
  rating?: number;
  comment?: string | null;
  hours?: number | null;
  date?: dayjs.Dayjs;
  tutor?: ITutorMySuffix | null;
}

export const defaultValue: Readonly<IRatingMySuffix> = {};

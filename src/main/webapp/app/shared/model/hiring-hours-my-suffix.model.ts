import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';

export interface IHiringHoursMySuffix {
  id?: number;
  hour?: number;
  tutor?: ITutorMySuffix | null;
}

export const defaultValue: Readonly<IHiringHoursMySuffix> = {};

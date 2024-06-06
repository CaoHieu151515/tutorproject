import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { Teach } from 'app/shared/model/enumerations/teach.model';

export interface ITutorTeachMySuffix {
  id?: number;
  subject?: keyof typeof Teach | null;
  tutorDetails?: ITutorDetailsMySuffix | null;
}

export const defaultValue: Readonly<ITutorTeachMySuffix> = {};

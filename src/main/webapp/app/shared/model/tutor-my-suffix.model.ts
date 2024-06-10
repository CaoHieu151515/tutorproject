import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { Devide } from 'app/shared/model/enumerations/devide.model';
import { TuStatus } from 'app/shared/model/enumerations/tu-status.model';

export interface ITutorMySuffix {
  id?: number;
  recommend?: boolean | null;
  price?: number | null;
  tuDevice?: keyof typeof Devide | null;
  status?: keyof typeof TuStatus | null;
  followerCount?: number | null;
  averageRating?: number | null;
  tutorDetails?: ITutorDetailsMySuffix | null;
}

export const defaultValue: Readonly<ITutorMySuffix> = {
  recommend: false,
};

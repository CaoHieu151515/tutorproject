import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';

export interface ITutorImageMySuffix {
  id?: number;
  media?: IMediaMySuffix | null;
  tutorDetails?: ITutorDetailsMySuffix | null;
}

export const defaultValue: Readonly<ITutorImageMySuffix> = {};

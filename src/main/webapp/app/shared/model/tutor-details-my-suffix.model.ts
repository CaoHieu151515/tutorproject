import { ITutorVideoMySuffix } from 'app/shared/model/tutor-video-my-suffix.model';

export interface ITutorDetailsMySuffix {
  id?: number;
  information?: string | null;
  tutorVideo?: ITutorVideoMySuffix | null;
}

export const defaultValue: Readonly<ITutorDetailsMySuffix> = {};

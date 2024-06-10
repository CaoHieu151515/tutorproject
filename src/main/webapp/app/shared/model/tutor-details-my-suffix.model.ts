import { ITutorVideoMySuffix } from 'app/shared/model/tutor-video-my-suffix.model';
import { Contact } from 'app/shared/model/enumerations/contact.model';

export interface ITutorDetailsMySuffix {
  id?: number;
  contact?: keyof typeof Contact | null;
  information?: string | null;
  tutorVideo?: ITutorVideoMySuffix | null;
}

export const defaultValue: Readonly<ITutorDetailsMySuffix> = {};

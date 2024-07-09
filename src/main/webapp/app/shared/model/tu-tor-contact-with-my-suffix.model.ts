import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { Contact } from 'app/shared/model/enumerations/contact.model';

export interface ITuTorContactWithMySuffix {
  id?: number;
  urlContact?: string | null;
  type?: keyof typeof Contact | null;
  tutorDetails?: ITutorDetailsMySuffix | null;
}

export const defaultValue: Readonly<ITuTorContactWithMySuffix> = {};

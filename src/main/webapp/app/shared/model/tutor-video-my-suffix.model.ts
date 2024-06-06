import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';

export interface ITutorVideoMySuffix {
  id?: number;
  media?: IMediaMySuffix | null;
}

export const defaultValue: Readonly<ITutorVideoMySuffix> = {};

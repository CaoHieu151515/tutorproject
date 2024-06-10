import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TutorVideoMySuffix from './tutor-video-my-suffix';
import TutorVideoMySuffixDetail from './tutor-video-my-suffix-detail';
import TutorVideoMySuffixUpdate from './tutor-video-my-suffix-update';
import TutorVideoMySuffixDeleteDialog from './tutor-video-my-suffix-delete-dialog';

const TutorVideoMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TutorVideoMySuffix />} />
    <Route path="new" element={<TutorVideoMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TutorVideoMySuffixDetail />} />
      <Route path="edit" element={<TutorVideoMySuffixUpdate />} />
      <Route path="delete" element={<TutorVideoMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TutorVideoMySuffixRoutes;

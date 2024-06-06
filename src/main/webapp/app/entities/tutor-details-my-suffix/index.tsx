import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TutorDetailsMySuffix from './tutor-details-my-suffix';
import TutorDetailsMySuffixDetail from './tutor-details-my-suffix-detail';
import TutorDetailsMySuffixUpdate from './tutor-details-my-suffix-update';
import TutorDetailsMySuffixDeleteDialog from './tutor-details-my-suffix-delete-dialog';

const TutorDetailsMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TutorDetailsMySuffix />} />
    <Route path="new" element={<TutorDetailsMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TutorDetailsMySuffixDetail />} />
      <Route path="edit" element={<TutorDetailsMySuffixUpdate />} />
      <Route path="delete" element={<TutorDetailsMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TutorDetailsMySuffixRoutes;

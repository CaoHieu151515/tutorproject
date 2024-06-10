import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TutorImageMySuffix from './tutor-image-my-suffix';
import TutorImageMySuffixDetail from './tutor-image-my-suffix-detail';
import TutorImageMySuffixUpdate from './tutor-image-my-suffix-update';
import TutorImageMySuffixDeleteDialog from './tutor-image-my-suffix-delete-dialog';

const TutorImageMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TutorImageMySuffix />} />
    <Route path="new" element={<TutorImageMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TutorImageMySuffixDetail />} />
      <Route path="edit" element={<TutorImageMySuffixUpdate />} />
      <Route path="delete" element={<TutorImageMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TutorImageMySuffixRoutes;

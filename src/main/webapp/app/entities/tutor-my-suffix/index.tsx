import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TutorMySuffix from './tutor-my-suffix';
import TutorMySuffixDetail from './tutor-my-suffix-detail';
import TutorMySuffixUpdate from './tutor-my-suffix-update';
import TutorMySuffixDeleteDialog from './tutor-my-suffix-delete-dialog';

const TutorMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TutorMySuffix />} />
    <Route path="new" element={<TutorMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TutorMySuffixDetail />} />
      <Route path="edit" element={<TutorMySuffixUpdate />} />
      <Route path="delete" element={<TutorMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TutorMySuffixRoutes;

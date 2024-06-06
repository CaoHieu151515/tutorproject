import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TutorTeachMySuffix from './tutor-teach-my-suffix';
import TutorTeachMySuffixDetail from './tutor-teach-my-suffix-detail';
import TutorTeachMySuffixUpdate from './tutor-teach-my-suffix-update';
import TutorTeachMySuffixDeleteDialog from './tutor-teach-my-suffix-delete-dialog';

const TutorTeachMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TutorTeachMySuffix />} />
    <Route path="new" element={<TutorTeachMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TutorTeachMySuffixDetail />} />
      <Route path="edit" element={<TutorTeachMySuffixUpdate />} />
      <Route path="delete" element={<TutorTeachMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TutorTeachMySuffixRoutes;

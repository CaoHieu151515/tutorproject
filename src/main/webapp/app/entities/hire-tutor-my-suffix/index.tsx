import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HireTutorMySuffix from './hire-tutor-my-suffix';
import HireTutorMySuffixDetail from './hire-tutor-my-suffix-detail';
import HireTutorMySuffixUpdate from './hire-tutor-my-suffix-update';
import HireTutorMySuffixDeleteDialog from './hire-tutor-my-suffix-delete-dialog';

const HireTutorMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HireTutorMySuffix />} />
    <Route path="new" element={<HireTutorMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<HireTutorMySuffixDetail />} />
      <Route path="edit" element={<HireTutorMySuffixUpdate />} />
      <Route path="delete" element={<HireTutorMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HireTutorMySuffixRoutes;

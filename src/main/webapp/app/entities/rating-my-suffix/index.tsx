import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RatingMySuffix from './rating-my-suffix';
import RatingMySuffixDetail from './rating-my-suffix-detail';
import RatingMySuffixUpdate from './rating-my-suffix-update';
import RatingMySuffixDeleteDialog from './rating-my-suffix-delete-dialog';

const RatingMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RatingMySuffix />} />
    <Route path="new" element={<RatingMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RatingMySuffixDetail />} />
      <Route path="edit" element={<RatingMySuffixUpdate />} />
      <Route path="delete" element={<RatingMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RatingMySuffixRoutes;

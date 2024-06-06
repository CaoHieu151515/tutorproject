import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FollowMySuffix from './follow-my-suffix';
import FollowMySuffixDetail from './follow-my-suffix-detail';
import FollowMySuffixUpdate from './follow-my-suffix-update';
import FollowMySuffixDeleteDialog from './follow-my-suffix-delete-dialog';

const FollowMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FollowMySuffix />} />
    <Route path="new" element={<FollowMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<FollowMySuffixDetail />} />
      <Route path="edit" element={<FollowMySuffixUpdate />} />
      <Route path="delete" element={<FollowMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FollowMySuffixRoutes;

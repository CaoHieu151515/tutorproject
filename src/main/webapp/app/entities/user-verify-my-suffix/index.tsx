import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserVerifyMySuffix from './user-verify-my-suffix';
import UserVerifyMySuffixDetail from './user-verify-my-suffix-detail';
import UserVerifyMySuffixUpdate from './user-verify-my-suffix-update';
import UserVerifyMySuffixDeleteDialog from './user-verify-my-suffix-delete-dialog';

const UserVerifyMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserVerifyMySuffix />} />
    <Route path="new" element={<UserVerifyMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<UserVerifyMySuffixDetail />} />
      <Route path="edit" element={<UserVerifyMySuffixUpdate />} />
      <Route path="delete" element={<UserVerifyMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserVerifyMySuffixRoutes;

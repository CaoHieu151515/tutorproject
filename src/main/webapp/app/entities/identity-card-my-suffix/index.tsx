import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import IdentityCardMySuffix from './identity-card-my-suffix';
import IdentityCardMySuffixDetail from './identity-card-my-suffix-detail';
import IdentityCardMySuffixUpdate from './identity-card-my-suffix-update';
import IdentityCardMySuffixDeleteDialog from './identity-card-my-suffix-delete-dialog';

const IdentityCardMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<IdentityCardMySuffix />} />
    <Route path="new" element={<IdentityCardMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<IdentityCardMySuffixDetail />} />
      <Route path="edit" element={<IdentityCardMySuffixUpdate />} />
      <Route path="delete" element={<IdentityCardMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IdentityCardMySuffixRoutes;

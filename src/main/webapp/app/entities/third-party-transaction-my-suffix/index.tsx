import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ThirdPartyTransactionMySuffix from './third-party-transaction-my-suffix';
import ThirdPartyTransactionMySuffixDetail from './third-party-transaction-my-suffix-detail';
import ThirdPartyTransactionMySuffixUpdate from './third-party-transaction-my-suffix-update';
import ThirdPartyTransactionMySuffixDeleteDialog from './third-party-transaction-my-suffix-delete-dialog';

const ThirdPartyTransactionMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ThirdPartyTransactionMySuffix />} />
    <Route path="new" element={<ThirdPartyTransactionMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ThirdPartyTransactionMySuffixDetail />} />
      <Route path="edit" element={<ThirdPartyTransactionMySuffixUpdate />} />
      <Route path="delete" element={<ThirdPartyTransactionMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThirdPartyTransactionMySuffixRoutes;

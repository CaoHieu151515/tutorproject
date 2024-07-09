import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TuTorContactWithMySuffix from './tu-tor-contact-with-my-suffix';
import TuTorContactWithMySuffixDetail from './tu-tor-contact-with-my-suffix-detail';
import TuTorContactWithMySuffixUpdate from './tu-tor-contact-with-my-suffix-update';
import TuTorContactWithMySuffixDeleteDialog from './tu-tor-contact-with-my-suffix-delete-dialog';

const TuTorContactWithMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TuTorContactWithMySuffix />} />
    <Route path="new" element={<TuTorContactWithMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<TuTorContactWithMySuffixDetail />} />
      <Route path="edit" element={<TuTorContactWithMySuffixUpdate />} />
      <Route path="delete" element={<TuTorContactWithMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TuTorContactWithMySuffixRoutes;

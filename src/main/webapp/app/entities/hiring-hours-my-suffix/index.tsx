import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HiringHoursMySuffix from './hiring-hours-my-suffix';
import HiringHoursMySuffixDetail from './hiring-hours-my-suffix-detail';
import HiringHoursMySuffixUpdate from './hiring-hours-my-suffix-update';
import HiringHoursMySuffixDeleteDialog from './hiring-hours-my-suffix-delete-dialog';

const HiringHoursMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HiringHoursMySuffix />} />
    <Route path="new" element={<HiringHoursMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<HiringHoursMySuffixDetail />} />
      <Route path="edit" element={<HiringHoursMySuffixUpdate />} />
      <Route path="delete" element={<HiringHoursMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HiringHoursMySuffixRoutes;

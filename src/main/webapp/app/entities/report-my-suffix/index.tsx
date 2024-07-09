import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReportMySuffix from './report-my-suffix';
import ReportMySuffixDetail from './report-my-suffix-detail';
import ReportMySuffixUpdate from './report-my-suffix-update';
import ReportMySuffixDeleteDialog from './report-my-suffix-delete-dialog';

const ReportMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReportMySuffix />} />
    <Route path="new" element={<ReportMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ReportMySuffixDetail />} />
      <Route path="edit" element={<ReportMySuffixUpdate />} />
      <Route path="delete" element={<ReportMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReportMySuffixRoutes;

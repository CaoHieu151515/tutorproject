import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AcademicRankMySuffix from './academic-rank-my-suffix';
import AcademicRankMySuffixDetail from './academic-rank-my-suffix-detail';
import AcademicRankMySuffixUpdate from './academic-rank-my-suffix-update';
import AcademicRankMySuffixDeleteDialog from './academic-rank-my-suffix-delete-dialog';

const AcademicRankMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AcademicRankMySuffix />} />
    <Route path="new" element={<AcademicRankMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AcademicRankMySuffixDetail />} />
      <Route path="edit" element={<AcademicRankMySuffixUpdate />} />
      <Route path="delete" element={<AcademicRankMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AcademicRankMySuffixRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AcademicRankMySuffix from './academic-rank-my-suffix';
import AppUserMySuffix from './app-user-my-suffix';
import FollowMySuffix from './follow-my-suffix';
import HireTutorMySuffix from './hire-tutor-my-suffix';
import HiringHoursMySuffix from './hiring-hours-my-suffix';
import IdentityCardMySuffix from './identity-card-my-suffix';
import MediaMySuffix from './media-my-suffix';
import RatingMySuffix from './rating-my-suffix';
import TutorMySuffix from './tutor-my-suffix';
import TutorDetailsMySuffix from './tutor-details-my-suffix';
import TutorImageMySuffix from './tutor-image-my-suffix';
import TutorTeachMySuffix from './tutor-teach-my-suffix';
import TutorVideoMySuffix from './tutor-video-my-suffix';
import UserVerifyMySuffix from './user-verify-my-suffix';
import WalletMySuffix from './wallet-my-suffix';
import WalletTransactionMySuffix from './wallet-transaction-my-suffix';
import TuTorContactWithMySuffix from './tu-tor-contact-with-my-suffix';
import ReportMySuffix from './report-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="academic-rank-my-suffix/*" element={<AcademicRankMySuffix />} />
        <Route path="app-user-my-suffix/*" element={<AppUserMySuffix />} />
        <Route path="follow-my-suffix/*" element={<FollowMySuffix />} />
        <Route path="hire-tutor-my-suffix/*" element={<HireTutorMySuffix />} />
        <Route path="hiring-hours-my-suffix/*" element={<HiringHoursMySuffix />} />
        <Route path="identity-card-my-suffix/*" element={<IdentityCardMySuffix />} />
        <Route path="media-my-suffix/*" element={<MediaMySuffix />} />
        <Route path="rating-my-suffix/*" element={<RatingMySuffix />} />
        <Route path="tutor-my-suffix/*" element={<TutorMySuffix />} />
        <Route path="tutor-details-my-suffix/*" element={<TutorDetailsMySuffix />} />
        <Route path="tutor-image-my-suffix/*" element={<TutorImageMySuffix />} />
        <Route path="tutor-teach-my-suffix/*" element={<TutorTeachMySuffix />} />
        <Route path="tutor-video-my-suffix/*" element={<TutorVideoMySuffix />} />
        <Route path="user-verify-my-suffix/*" element={<UserVerifyMySuffix />} />
        <Route path="wallet-my-suffix/*" element={<WalletMySuffix />} />
        <Route path="wallet-transaction-my-suffix/*" element={<WalletTransactionMySuffix />} />
        <Route path="tu-tor-contact-with-my-suffix/*" element={<TuTorContactWithMySuffix />} />
        <Route path="report-my-suffix/*" element={<ReportMySuffix />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

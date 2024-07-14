import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/academic-rank-my-suffix">
        <Translate contentKey="global.menu.entities.academicRankMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-user-my-suffix">
        <Translate contentKey="global.menu.entities.appUserMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/follow-my-suffix">
        <Translate contentKey="global.menu.entities.followMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hire-tutor-my-suffix">
        <Translate contentKey="global.menu.entities.hireTutorMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hiring-hours-my-suffix">
        <Translate contentKey="global.menu.entities.hiringHoursMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/identity-card-my-suffix">
        <Translate contentKey="global.menu.entities.identityCardMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/media-my-suffix">
        <Translate contentKey="global.menu.entities.mediaMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rating-my-suffix">
        <Translate contentKey="global.menu.entities.ratingMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tutor-my-suffix">
        <Translate contentKey="global.menu.entities.tutorMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tutor-details-my-suffix">
        <Translate contentKey="global.menu.entities.tutorDetailsMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tutor-image-my-suffix">
        <Translate contentKey="global.menu.entities.tutorImageMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tutor-teach-my-suffix">
        <Translate contentKey="global.menu.entities.tutorTeachMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tutor-video-my-suffix">
        <Translate contentKey="global.menu.entities.tutorVideoMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-verify-my-suffix">
        <Translate contentKey="global.menu.entities.userVerifyMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/wallet-my-suffix">
        <Translate contentKey="global.menu.entities.walletMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/wallet-transaction-my-suffix">
        <Translate contentKey="global.menu.entities.walletTransactionMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tu-tor-contact-with-my-suffix">
        <Translate contentKey="global.menu.entities.tuTorContactWithMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/report-my-suffix">
        <Translate contentKey="global.menu.entities.reportMySuffix" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/third-party-transaction-my-suffix">
        <Translate contentKey="global.menu.entities.thirdPartyTransactionMySuffix" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

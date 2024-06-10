import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user-my-suffix.reducer';

export const AppUserMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">
          <Translate contentKey="projectApp.appUser.detail.title">AppUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="beTutor">
              <Translate contentKey="projectApp.appUser.beTutor">Be Tutor</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.beTutor ? 'true' : 'false'}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="projectApp.appUser.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.gender}</dd>
          <dt>
            <span id="bankAccountNumber">
              <Translate contentKey="projectApp.appUser.bankAccountNumber">Bank Account Number</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.bankAccountNumber}</dd>
          <dt>
            <span id="bankName">
              <Translate contentKey="projectApp.appUser.bankName">Bank Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.bankName}</dd>
          <dt>
            <span id="walletAddress">
              <Translate contentKey="projectApp.appUser.walletAddress">Wallet Address</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.walletAddress}</dd>
          <dt>
            <Translate contentKey="projectApp.appUser.tutor">Tutor</Translate>
          </dt>
          <dd>{appUserEntity.tutor ? appUserEntity.tutor.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.appUser.userVerify">User Verify</Translate>
          </dt>
          <dd>{appUserEntity.userVerify ? appUserEntity.userVerify.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.appUser.user">User</Translate>
          </dt>
          <dd>{appUserEntity.user ? appUserEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.appUser.rating">Rating</Translate>
          </dt>
          <dd>{appUserEntity.rating ? appUserEntity.rating.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-user-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user-my-suffix/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserMySuffixDetail;

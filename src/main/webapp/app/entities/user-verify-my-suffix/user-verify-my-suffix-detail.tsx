import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-verify-my-suffix.reducer';

export const UserVerifyMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userVerifyEntity = useAppSelector(state => state.userVerify.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userVerifyDetailsHeading">
          <Translate contentKey="projectApp.userVerify.detail.title">UserVerify</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.id}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="projectApp.userVerify.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.rating}</dd>
          <dt>
            <span id="school">
              <Translate contentKey="projectApp.userVerify.school">School</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.school}</dd>
          <dt>
            <span id="studentID">
              <Translate contentKey="projectApp.userVerify.studentID">Student ID</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.studentID}</dd>
          <dt>
            <span id="major">
              <Translate contentKey="projectApp.userVerify.major">Major</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.major}</dd>
          <dt>
            <span id="graduationYear">
              <Translate contentKey="projectApp.userVerify.graduationYear">Graduation Year</Translate>
            </span>
          </dt>
          <dd>{userVerifyEntity.graduationYear}</dd>
          <dt>
            <Translate contentKey="projectApp.userVerify.identityCard">Identity Card</Translate>
          </dt>
          <dd>{userVerifyEntity.identityCard ? userVerifyEntity.identityCard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-verify-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-verify-my-suffix/${userVerifyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserVerifyMySuffixDetail;

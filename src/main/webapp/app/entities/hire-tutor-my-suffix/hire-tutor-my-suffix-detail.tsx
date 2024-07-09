import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hire-tutor-my-suffix.reducer';

export const HireTutorMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hireTutorEntity = useAppSelector(state => state.hireTutor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hireTutorDetailsHeading">
          <Translate contentKey="projectApp.hireTutor.detail.title">HireTutor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hireTutorEntity.id}</dd>
          <dt>
            <span id="timeHire">
              <Translate contentKey="projectApp.hireTutor.timeHire">Time Hire</Translate>
            </span>
          </dt>
          <dd>{hireTutorEntity.timeHire}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="projectApp.hireTutor.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{hireTutorEntity.totalPrice}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="projectApp.hireTutor.status">Status</Translate>
            </span>
          </dt>
          <dd>{hireTutorEntity.status}</dd>
          <dt>
            <span id="startAt">
              <Translate contentKey="projectApp.hireTutor.startAt">Start At</Translate>
            </span>
          </dt>
          <dd>
            {hireTutorEntity.startAt ? <TextFormat value={hireTutorEntity.startAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endAt">
              <Translate contentKey="projectApp.hireTutor.endAt">End At</Translate>
            </span>
          </dt>
          <dd>{hireTutorEntity.endAt ? <TextFormat value={hireTutorEntity.endAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="projectApp.hireTutor.appUser">App User</Translate>
          </dt>
          <dd>{hireTutorEntity.appUser ? hireTutorEntity.appUser.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.hireTutor.tutor">Tutor</Translate>
          </dt>
          <dd>{hireTutorEntity.tutor ? hireTutorEntity.tutor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hire-tutor-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hire-tutor-my-suffix/${hireTutorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HireTutorMySuffixDetail;

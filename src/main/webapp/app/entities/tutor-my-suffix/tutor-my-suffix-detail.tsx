import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tutor-my-suffix.reducer';

export const TutorMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tutorEntity = useAppSelector(state => state.tutor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tutorDetailsHeading">
          <Translate contentKey="projectApp.tutor.detail.title">Tutor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.id}</dd>
          <dt>
            <span id="recommend">
              <Translate contentKey="projectApp.tutor.recommend">Recommend</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.recommend ? 'true' : 'false'}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="projectApp.tutor.price">Price</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.price}</dd>
          <dt>
            <span id="tuDevice">
              <Translate contentKey="projectApp.tutor.tuDevice">Tu Device</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.tuDevice}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="projectApp.tutor.status">Status</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.status}</dd>
          <dt>
            <span id="followerCount">
              <Translate contentKey="projectApp.tutor.followerCount">Follower Count</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.followerCount}</dd>
          <dt>
            <span id="averageRating">
              <Translate contentKey="projectApp.tutor.averageRating">Average Rating</Translate>
            </span>
          </dt>
          <dd>{tutorEntity.averageRating}</dd>
          <dt>
            <Translate contentKey="projectApp.tutor.tutorDetails">Tutor Details</Translate>
          </dt>
          <dd>{tutorEntity.tutorDetails ? tutorEntity.tutorDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tutor-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tutor-my-suffix/${tutorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TutorMySuffixDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tutor-teach-my-suffix.reducer';

export const TutorTeachMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tutorTeachEntity = useAppSelector(state => state.tutorTeach.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tutorTeachDetailsHeading">
          <Translate contentKey="projectApp.tutorTeach.detail.title">TutorTeach</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tutorTeachEntity.id}</dd>
          <dt>
            <span id="subject">
              <Translate contentKey="projectApp.tutorTeach.subject">Subject</Translate>
            </span>
          </dt>
          <dd>{tutorTeachEntity.subject}</dd>
          <dt>
            <Translate contentKey="projectApp.tutorTeach.tutorDetails">Tutor Details</Translate>
          </dt>
          <dd>{tutorTeachEntity.tutorDetails ? tutorTeachEntity.tutorDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tutor-teach-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tutor-teach-my-suffix/${tutorTeachEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TutorTeachMySuffixDetail;

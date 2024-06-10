import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tutor-details-my-suffix.reducer';

export const TutorDetailsMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tutorDetailsEntity = useAppSelector(state => state.tutorDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tutorDetailsDetailsHeading">
          <Translate contentKey="projectApp.tutorDetails.detail.title">TutorDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tutorDetailsEntity.id}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="projectApp.tutorDetails.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{tutorDetailsEntity.contact}</dd>
          <dt>
            <span id="information">
              <Translate contentKey="projectApp.tutorDetails.information">Information</Translate>
            </span>
          </dt>
          <dd>{tutorDetailsEntity.information}</dd>
          <dt>
            <Translate contentKey="projectApp.tutorDetails.tutorVideo">Tutor Video</Translate>
          </dt>
          <dd>{tutorDetailsEntity.tutorVideo ? tutorDetailsEntity.tutorVideo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tutor-details-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tutor-details-my-suffix/${tutorDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TutorDetailsMySuffixDetail;

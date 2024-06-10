import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tutor-image-my-suffix.reducer';

export const TutorImageMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tutorImageEntity = useAppSelector(state => state.tutorImage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tutorImageDetailsHeading">
          <Translate contentKey="projectApp.tutorImage.detail.title">TutorImage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tutorImageEntity.id}</dd>
          <dt>
            <Translate contentKey="projectApp.tutorImage.media">Media</Translate>
          </dt>
          <dd>{tutorImageEntity.media ? tutorImageEntity.media.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.tutorImage.tutorDetails">Tutor Details</Translate>
          </dt>
          <dd>{tutorImageEntity.tutorDetails ? tutorImageEntity.tutorDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tutor-image-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tutor-image-my-suffix/${tutorImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TutorImageMySuffixDetail;

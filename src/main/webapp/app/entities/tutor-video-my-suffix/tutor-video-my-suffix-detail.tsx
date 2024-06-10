import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tutor-video-my-suffix.reducer';

export const TutorVideoMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tutorVideoEntity = useAppSelector(state => state.tutorVideo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tutorVideoDetailsHeading">
          <Translate contentKey="projectApp.tutorVideo.detail.title">TutorVideo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tutorVideoEntity.id}</dd>
          <dt>
            <Translate contentKey="projectApp.tutorVideo.media">Media</Translate>
          </dt>
          <dd>{tutorVideoEntity.media ? tutorVideoEntity.media.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tutor-video-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tutor-video-my-suffix/${tutorVideoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TutorVideoMySuffixDetail;

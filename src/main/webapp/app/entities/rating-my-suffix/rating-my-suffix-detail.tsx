import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating-my-suffix.reducer';

export const RatingMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingEntity = useAppSelector(state => state.rating.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingDetailsHeading">
          <Translate contentKey="projectApp.rating.detail.title">Rating</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.id}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="projectApp.rating.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.rating}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="projectApp.rating.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.comment}</dd>
          <dt>
            <span id="hours">
              <Translate contentKey="projectApp.rating.hours">Hours</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.hours}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="projectApp.rating.date">Date</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.date ? <TextFormat value={ratingEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="projectApp.rating.tutor">Tutor</Translate>
          </dt>
          <dd>{ratingEntity.tutor ? ratingEntity.tutor.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.rating.appUser">App User</Translate>
          </dt>
          <dd>{ratingEntity.appUser ? ratingEntity.appUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating-my-suffix/${ratingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingMySuffixDetail;

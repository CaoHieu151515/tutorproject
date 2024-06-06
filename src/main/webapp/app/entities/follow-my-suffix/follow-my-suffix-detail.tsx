import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './follow-my-suffix.reducer';

export const FollowMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const followEntity = useAppSelector(state => state.follow.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="followDetailsHeading">
          <Translate contentKey="projectApp.follow.detail.title">Follow</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{followEntity.id}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="projectApp.follow.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {followEntity.createDate ? <TextFormat value={followEntity.createDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="projectApp.follow.followerAppUser">Follower App User</Translate>
          </dt>
          <dd>{followEntity.followerAppUser ? followEntity.followerAppUser.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.follow.followedTutor">Followed Tutor</Translate>
          </dt>
          <dd>{followEntity.followedTutor ? followEntity.followedTutor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/follow-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/follow-my-suffix/${followEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FollowMySuffixDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './academic-rank-my-suffix.reducer';

export const AcademicRankMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const academicRankEntity = useAppSelector(state => state.academicRank.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="academicRankDetailsHeading">
          <Translate contentKey="projectApp.academicRank.detail.title">AcademicRank</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{academicRankEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="projectApp.academicRank.type">Type</Translate>
            </span>
          </dt>
          <dd>{academicRankEntity.type}</dd>
          <dt>
            <Translate contentKey="projectApp.academicRank.media">Media</Translate>
          </dt>
          <dd>{academicRankEntity.media ? academicRankEntity.media.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.academicRank.userVerify">User Verify</Translate>
          </dt>
          <dd>{academicRankEntity.userVerify ? academicRankEntity.userVerify.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/academic-rank-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/academic-rank-my-suffix/${academicRankEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AcademicRankMySuffixDetail;

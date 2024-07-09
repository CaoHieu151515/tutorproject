import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './report-my-suffix.reducer';

export const ReportMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reportEntity = useAppSelector(state => state.report.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reportDetailsHeading">
          <Translate contentKey="projectApp.report.detail.title">Report</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reportEntity.id}</dd>
          <dt>
            <span id="reportDetails">
              <Translate contentKey="projectApp.report.reportDetails">Report Details</Translate>
            </span>
          </dt>
          <dd>{reportEntity.reportDetails}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="projectApp.report.time">Time</Translate>
            </span>
          </dt>
          <dd>{reportEntity.time ? <TextFormat value={reportEntity.time} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="projectApp.report.appUser">App User</Translate>
          </dt>
          <dd>{reportEntity.appUser ? reportEntity.appUser.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.report.tutor">Tutor</Translate>
          </dt>
          <dd>{reportEntity.tutor ? reportEntity.tutor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/report-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/report-my-suffix/${reportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReportMySuffixDetail;

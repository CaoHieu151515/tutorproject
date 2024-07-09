import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { getEntities as getAppUsers } from 'app/entities/app-user-my-suffix/app-user-my-suffix.reducer';
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { getEntities as getTutors } from 'app/entities/tutor-my-suffix/tutor-my-suffix.reducer';
import { IReportMySuffix } from 'app/shared/model/report-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './report-my-suffix.reducer';

export const ReportMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const tutors = useAppSelector(state => state.tutor.entities);
  const reportEntity = useAppSelector(state => state.report.entity);
  const loading = useAppSelector(state => state.report.loading);
  const updating = useAppSelector(state => state.report.updating);
  const updateSuccess = useAppSelector(state => state.report.updateSuccess);

  const handleClose = () => {
    navigate('/report-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppUsers({}));
    dispatch(getTutors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...reportEntity,
      ...values,
      appUser: appUsers.find(it => it.id.toString() === values.appUser?.toString()),
      tutor: tutors.find(it => it.id.toString() === values.tutor?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...reportEntity,
          appUser: reportEntity?.appUser?.id,
          tutor: reportEntity?.tutor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.report.home.createOrEditLabel" data-cy="ReportCreateUpdateHeading">
            <Translate contentKey="projectApp.report.home.createOrEditLabel">Create or edit a Report</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="report-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.report.reportDetails')}
                id="report-my-suffix-reportDetails"
                name="reportDetails"
                data-cy="reportDetails"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.report.time')}
                id="report-my-suffix-time"
                name="time"
                data-cy="time"
                type="date"
              />
              <ValidatedField
                id="report-my-suffix-appUser"
                name="appUser"
                data-cy="appUser"
                label={translate('projectApp.report.appUser')}
                type="select"
              >
                <option value="" key="0" />
                {appUsers
                  ? appUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="report-my-suffix-tutor"
                name="tutor"
                data-cy="tutor"
                label={translate('projectApp.report.tutor')}
                type="select"
              >
                <option value="" key="0" />
                {tutors
                  ? tutors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/report-my-suffix" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReportMySuffixUpdate;

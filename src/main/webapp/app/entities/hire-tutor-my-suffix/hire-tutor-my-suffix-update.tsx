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
import { IHireTutorMySuffix } from 'app/shared/model/hire-tutor-my-suffix.model';
import { HireStatus } from 'app/shared/model/enumerations/hire-status.model';
import { getEntity, updateEntity, createEntity, reset } from './hire-tutor-my-suffix.reducer';

export const HireTutorMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const tutors = useAppSelector(state => state.tutor.entities);
  const hireTutorEntity = useAppSelector(state => state.hireTutor.entity);
  const loading = useAppSelector(state => state.hireTutor.loading);
  const updating = useAppSelector(state => state.hireTutor.updating);
  const updateSuccess = useAppSelector(state => state.hireTutor.updateSuccess);
  const hireStatusValues = Object.keys(HireStatus);

  const handleClose = () => {
    navigate('/hire-tutor-my-suffix');
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
    if (values.timeHire !== undefined && typeof values.timeHire !== 'number') {
      values.timeHire = Number(values.timeHire);
    }
    if (values.totalPrice !== undefined && typeof values.totalPrice !== 'number') {
      values.totalPrice = Number(values.totalPrice);
    }

    const entity = {
      ...hireTutorEntity,
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
          status: 'DURING',
          ...hireTutorEntity,
          appUser: hireTutorEntity?.appUser?.id,
          tutor: hireTutorEntity?.tutor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.hireTutor.home.createOrEditLabel" data-cy="HireTutorCreateUpdateHeading">
            <Translate contentKey="projectApp.hireTutor.home.createOrEditLabel">Create or edit a HireTutor</Translate>
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
                  id="hire-tutor-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.hireTutor.timeHire')}
                id="hire-tutor-my-suffix-timeHire"
                name="timeHire"
                data-cy="timeHire"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.hireTutor.totalPrice')}
                id="hire-tutor-my-suffix-totalPrice"
                name="totalPrice"
                data-cy="totalPrice"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.hireTutor.status')}
                id="hire-tutor-my-suffix-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {hireStatusValues.map(hireStatus => (
                  <option value={hireStatus} key={hireStatus}>
                    {translate('projectApp.HireStatus.' + hireStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.hireTutor.startAt')}
                id="hire-tutor-my-suffix-startAt"
                name="startAt"
                data-cy="startAt"
                type="date"
              />
              <ValidatedField
                label={translate('projectApp.hireTutor.endAt')}
                id="hire-tutor-my-suffix-endAt"
                name="endAt"
                data-cy="endAt"
                type="date"
              />
              <ValidatedField
                id="hire-tutor-my-suffix-appUser"
                name="appUser"
                data-cy="appUser"
                label={translate('projectApp.hireTutor.appUser')}
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
                id="hire-tutor-my-suffix-tutor"
                name="tutor"
                data-cy="tutor"
                label={translate('projectApp.hireTutor.tutor')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hire-tutor-my-suffix" replace color="info">
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

export default HireTutorMySuffixUpdate;

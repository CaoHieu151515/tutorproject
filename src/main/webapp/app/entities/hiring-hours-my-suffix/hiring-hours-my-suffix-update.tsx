import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { getEntities as getTutors } from 'app/entities/tutor-my-suffix/tutor-my-suffix.reducer';
import { IHiringHoursMySuffix } from 'app/shared/model/hiring-hours-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './hiring-hours-my-suffix.reducer';

export const HiringHoursMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutors = useAppSelector(state => state.tutor.entities);
  const hiringHoursEntity = useAppSelector(state => state.hiringHours.entity);
  const loading = useAppSelector(state => state.hiringHours.loading);
  const updating = useAppSelector(state => state.hiringHours.updating);
  const updateSuccess = useAppSelector(state => state.hiringHours.updateSuccess);

  const handleClose = () => {
    navigate('/hiring-hours-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.hour !== undefined && typeof values.hour !== 'number') {
      values.hour = Number(values.hour);
    }

    const entity = {
      ...hiringHoursEntity,
      ...values,
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
          ...hiringHoursEntity,
          tutor: hiringHoursEntity?.tutor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.hiringHours.home.createOrEditLabel" data-cy="HiringHoursCreateUpdateHeading">
            <Translate contentKey="projectApp.hiringHours.home.createOrEditLabel">Create or edit a HiringHours</Translate>
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
                  id="hiring-hours-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.hiringHours.hour')}
                id="hiring-hours-my-suffix-hour"
                name="hour"
                data-cy="hour"
                type="text"
              />
              <ValidatedField
                id="hiring-hours-my-suffix-tutor"
                name="tutor"
                data-cy="tutor"
                label={translate('projectApp.hiringHours.tutor')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hiring-hours-my-suffix" replace color="info">
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

export default HiringHoursMySuffixUpdate;

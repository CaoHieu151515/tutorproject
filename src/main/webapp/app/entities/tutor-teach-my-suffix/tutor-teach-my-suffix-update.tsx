import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { getEntities as getTutorDetails } from 'app/entities/tutor-details-my-suffix/tutor-details-my-suffix.reducer';
import { ITutorTeachMySuffix } from 'app/shared/model/tutor-teach-my-suffix.model';
import { Teach } from 'app/shared/model/enumerations/teach.model';
import { getEntity, updateEntity, createEntity, reset } from './tutor-teach-my-suffix.reducer';

export const TutorTeachMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutorDetails = useAppSelector(state => state.tutorDetails.entities);
  const tutorTeachEntity = useAppSelector(state => state.tutorTeach.entity);
  const loading = useAppSelector(state => state.tutorTeach.loading);
  const updating = useAppSelector(state => state.tutorTeach.updating);
  const updateSuccess = useAppSelector(state => state.tutorTeach.updateSuccess);
  const teachValues = Object.keys(Teach);

  const handleClose = () => {
    navigate('/tutor-teach-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTutorDetails({}));
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
      ...tutorTeachEntity,
      ...values,
      tutorDetails: tutorDetails.find(it => it.id.toString() === values.tutorDetails?.toString()),
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
          subject: 'MATH_10',
          ...tutorTeachEntity,
          tutorDetails: tutorTeachEntity?.tutorDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.tutorTeach.home.createOrEditLabel" data-cy="TutorTeachCreateUpdateHeading">
            <Translate contentKey="projectApp.tutorTeach.home.createOrEditLabel">Create or edit a TutorTeach</Translate>
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
                  id="tutor-teach-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.tutorTeach.subject')}
                id="tutor-teach-my-suffix-subject"
                name="subject"
                data-cy="subject"
                type="select"
              >
                {teachValues.map(teach => (
                  <option value={teach} key={teach}>
                    {translate('projectApp.Teach.' + teach)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="tutor-teach-my-suffix-tutorDetails"
                name="tutorDetails"
                data-cy="tutorDetails"
                label={translate('projectApp.tutorTeach.tutorDetails')}
                type="select"
              >
                <option value="" key="0" />
                {tutorDetails
                  ? tutorDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tutor-teach-my-suffix" replace color="info">
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

export default TutorTeachMySuffixUpdate;

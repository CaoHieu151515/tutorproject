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
import { ITutorMySuffix } from 'app/shared/model/tutor-my-suffix.model';
import { Devide } from 'app/shared/model/enumerations/devide.model';
import { TuStatus } from 'app/shared/model/enumerations/tu-status.model';
import { getEntity, updateEntity, createEntity, reset } from './tutor-my-suffix.reducer';

export const TutorMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutorDetails = useAppSelector(state => state.tutorDetails.entities);
  const tutorEntity = useAppSelector(state => state.tutor.entity);
  const loading = useAppSelector(state => state.tutor.loading);
  const updating = useAppSelector(state => state.tutor.updating);
  const updateSuccess = useAppSelector(state => state.tutor.updateSuccess);
  const devideValues = Object.keys(Devide);
  const tuStatusValues = Object.keys(TuStatus);

  const handleClose = () => {
    navigate('/tutor-my-suffix');
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.followerCount !== undefined && typeof values.followerCount !== 'number') {
      values.followerCount = Number(values.followerCount);
    }
    if (values.averageRating !== undefined && typeof values.averageRating !== 'number') {
      values.averageRating = Number(values.averageRating);
    }

    const entity = {
      ...tutorEntity,
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
          tuDevice: 'MICRO',
          status: 'READY',
          ...tutorEntity,
          tutorDetails: tutorEntity?.tutorDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.tutor.home.createOrEditLabel" data-cy="TutorCreateUpdateHeading">
            <Translate contentKey="projectApp.tutor.home.createOrEditLabel">Create or edit a Tutor</Translate>
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
                  id="tutor-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.tutor.recommend')}
                id="tutor-my-suffix-recommend"
                name="recommend"
                data-cy="recommend"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('projectApp.tutor.price')}
                id="tutor-my-suffix-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.tutor.tuDevice')}
                id="tutor-my-suffix-tuDevice"
                name="tuDevice"
                data-cy="tuDevice"
                type="select"
              >
                {devideValues.map(devide => (
                  <option value={devide} key={devide}>
                    {translate('projectApp.Devide.' + devide)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.tutor.status')}
                id="tutor-my-suffix-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {tuStatusValues.map(tuStatus => (
                  <option value={tuStatus} key={tuStatus}>
                    {translate('projectApp.TuStatus.' + tuStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.tutor.followerCount')}
                id="tutor-my-suffix-followerCount"
                name="followerCount"
                data-cy="followerCount"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.tutor.averageRating')}
                id="tutor-my-suffix-averageRating"
                name="averageRating"
                data-cy="averageRating"
                type="text"
              />
              <ValidatedField
                id="tutor-my-suffix-tutorDetails"
                name="tutorDetails"
                data-cy="tutorDetails"
                label={translate('projectApp.tutor.tutorDetails')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tutor-my-suffix" replace color="info">
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

export default TutorMySuffixUpdate;

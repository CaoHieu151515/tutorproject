import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITutorVideoMySuffix } from 'app/shared/model/tutor-video-my-suffix.model';
import { getEntities as getTutorVideos } from 'app/entities/tutor-video-my-suffix/tutor-video-my-suffix.reducer';
import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { Contact } from 'app/shared/model/enumerations/contact.model';
import { getEntity, updateEntity, createEntity, reset } from './tutor-details-my-suffix.reducer';

export const TutorDetailsMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutorVideos = useAppSelector(state => state.tutorVideo.entities);
  const tutorDetailsEntity = useAppSelector(state => state.tutorDetails.entity);
  const loading = useAppSelector(state => state.tutorDetails.loading);
  const updating = useAppSelector(state => state.tutorDetails.updating);
  const updateSuccess = useAppSelector(state => state.tutorDetails.updateSuccess);
  const contactValues = Object.keys(Contact);

  const handleClose = () => {
    navigate('/tutor-details-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTutorVideos({}));
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
      ...tutorDetailsEntity,
      ...values,
      tutorVideo: tutorVideos.find(it => it.id.toString() === values.tutorVideo?.toString()),
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
          ...tutorDetailsEntity,
          tutorVideo: tutorDetailsEntity?.tutorVideo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.tutorDetails.home.createOrEditLabel" data-cy="TutorDetailsCreateUpdateHeading">
            <Translate contentKey="projectApp.tutorDetails.home.createOrEditLabel">Create or edit a TutorDetails</Translate>
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
                  id="tutor-details-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.tutorDetails.information')}
                id="tutor-details-my-suffix-information"
                name="information"
                data-cy="information"
                type="text"
              />
              <ValidatedField
                id="tutor-details-my-suffix-tutorVideo"
                name="tutorVideo"
                data-cy="tutorVideo"
                label={translate('projectApp.tutorDetails.tutorVideo')}
                type="select"
              >
                <option value="" key="0" />
                {tutorVideos
                  ? tutorVideos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tutor-details-my-suffix" replace color="info">
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

export default TutorDetailsMySuffixUpdate;

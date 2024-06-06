import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { getEntities as getMedia } from 'app/entities/media-my-suffix/media-my-suffix.reducer';
import { ITutorDetailsMySuffix } from 'app/shared/model/tutor-details-my-suffix.model';
import { getEntities as getTutorDetails } from 'app/entities/tutor-details-my-suffix/tutor-details-my-suffix.reducer';
import { ITutorImageMySuffix } from 'app/shared/model/tutor-image-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './tutor-image-my-suffix.reducer';

export const TutorImageMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const tutorDetails = useAppSelector(state => state.tutorDetails.entities);
  const tutorImageEntity = useAppSelector(state => state.tutorImage.entity);
  const loading = useAppSelector(state => state.tutorImage.loading);
  const updating = useAppSelector(state => state.tutorImage.updating);
  const updateSuccess = useAppSelector(state => state.tutorImage.updateSuccess);

  const handleClose = () => {
    navigate('/tutor-image-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
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
      ...tutorImageEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media?.toString()),
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
          ...tutorImageEntity,
          media: tutorImageEntity?.media?.id,
          tutorDetails: tutorImageEntity?.tutorDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.tutorImage.home.createOrEditLabel" data-cy="TutorImageCreateUpdateHeading">
            <Translate contentKey="projectApp.tutorImage.home.createOrEditLabel">Create or edit a TutorImage</Translate>
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
                  id="tutor-image-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="tutor-image-my-suffix-media"
                name="media"
                data-cy="media"
                label={translate('projectApp.tutorImage.media')}
                type="select"
              >
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tutor-image-my-suffix-tutorDetails"
                name="tutorDetails"
                data-cy="tutorDetails"
                label={translate('projectApp.tutorImage.tutorDetails')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tutor-image-my-suffix" replace color="info">
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

export default TutorImageMySuffixUpdate;

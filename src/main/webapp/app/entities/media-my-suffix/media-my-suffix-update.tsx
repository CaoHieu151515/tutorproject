import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIdentityCardMySuffix } from 'app/shared/model/identity-card-my-suffix.model';
import { getEntities as getIdentityCards } from 'app/entities/identity-card-my-suffix/identity-card-my-suffix.reducer';
import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './media-my-suffix.reducer';

export const MediaMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const identityCards = useAppSelector(state => state.identityCard.entities);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const loading = useAppSelector(state => state.media.loading);
  const updating = useAppSelector(state => state.media.updating);
  const updateSuccess = useAppSelector(state => state.media.updateSuccess);

  const handleClose = () => {
    navigate('/media-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIdentityCards({}));
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
      ...mediaEntity,
      ...values,
      identityCard: identityCards.find(it => it.id.toString() === values.identityCard?.toString()),
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
          ...mediaEntity,
          identityCard: mediaEntity?.identityCard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.media.home.createOrEditLabel" data-cy="MediaCreateUpdateHeading">
            <Translate contentKey="projectApp.media.home.createOrEditLabel">Create or edit a Media</Translate>
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
                  id="media-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('projectApp.media.url')} id="media-my-suffix-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                id="media-my-suffix-identityCard"
                name="identityCard"
                data-cy="identityCard"
                label={translate('projectApp.media.identityCard')}
                type="select"
              >
                <option value="" key="0" />
                {identityCards
                  ? identityCards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/media-my-suffix" replace color="info">
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

export default MediaMySuffixUpdate;

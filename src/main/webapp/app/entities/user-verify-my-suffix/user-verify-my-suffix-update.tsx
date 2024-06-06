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
import { IUserVerifyMySuffix } from 'app/shared/model/user-verify-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './user-verify-my-suffix.reducer';

export const UserVerifyMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const identityCards = useAppSelector(state => state.identityCard.entities);
  const userVerifyEntity = useAppSelector(state => state.userVerify.entity);
  const loading = useAppSelector(state => state.userVerify.loading);
  const updating = useAppSelector(state => state.userVerify.updating);
  const updateSuccess = useAppSelector(state => state.userVerify.updateSuccess);

  const handleClose = () => {
    navigate('/user-verify-my-suffix');
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
    if (values.rating !== undefined && typeof values.rating !== 'number') {
      values.rating = Number(values.rating);
    }
    if (values.graduationYear !== undefined && typeof values.graduationYear !== 'number') {
      values.graduationYear = Number(values.graduationYear);
    }

    const entity = {
      ...userVerifyEntity,
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
          ...userVerifyEntity,
          identityCard: userVerifyEntity?.identityCard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.userVerify.home.createOrEditLabel" data-cy="UserVerifyCreateUpdateHeading">
            <Translate contentKey="projectApp.userVerify.home.createOrEditLabel">Create or edit a UserVerify</Translate>
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
                  id="user-verify-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.userVerify.rating')}
                id="user-verify-my-suffix-rating"
                name="rating"
                data-cy="rating"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.userVerify.school')}
                id="user-verify-my-suffix-school"
                name="school"
                data-cy="school"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.userVerify.studentID')}
                id="user-verify-my-suffix-studentID"
                name="studentID"
                data-cy="studentID"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.userVerify.major')}
                id="user-verify-my-suffix-major"
                name="major"
                data-cy="major"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.userVerify.graduationYear')}
                id="user-verify-my-suffix-graduationYear"
                name="graduationYear"
                data-cy="graduationYear"
                type="text"
              />
              <ValidatedField
                id="user-verify-my-suffix-identityCard"
                name="identityCard"
                data-cy="identityCard"
                label={translate('projectApp.userVerify.identityCard')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-verify-my-suffix" replace color="info">
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

export default UserVerifyMySuffixUpdate;

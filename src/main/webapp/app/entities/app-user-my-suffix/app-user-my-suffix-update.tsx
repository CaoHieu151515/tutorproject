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
import { IUserVerifyMySuffix } from 'app/shared/model/user-verify-my-suffix.model';
import { getEntities as getUserVerifies } from 'app/entities/user-verify-my-suffix/user-verify-my-suffix.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { getEntity, updateEntity, createEntity, reset } from './app-user-my-suffix.reducer';

export const AppUserMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutors = useAppSelector(state => state.tutor.entities);
  const userVerifies = useAppSelector(state => state.userVerify.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);
  const genderTypeValues = Object.keys(GenderType);

  const handleClose = () => {
    navigate('/app-user-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTutors({}));
    dispatch(getUserVerifies({}));
    dispatch(getUsers({}));
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
      ...appUserEntity,
      ...values,
      tutor: tutors.find(it => it.id.toString() === values.tutor?.toString()),
      userVerify: userVerifies.find(it => it.id.toString() === values.userVerify?.toString()),
      user: users.find(it => it.id.toString() === values.user?.toString()),
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
          gender: 'MALE',
          ...appUserEntity,
          tutor: appUserEntity?.tutor?.id,
          userVerify: appUserEntity?.userVerify?.id,
          user: appUserEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            <Translate contentKey="projectApp.appUser.home.createOrEditLabel">Create or edit a AppUser</Translate>
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
                  id="app-user-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.appUser.beTutor')}
                id="app-user-my-suffix-beTutor"
                name="beTutor"
                data-cy="beTutor"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('projectApp.appUser.gender')}
                id="app-user-my-suffix-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                {genderTypeValues.map(genderType => (
                  <option value={genderType} key={genderType}>
                    {translate('projectApp.GenderType.' + genderType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.appUser.bankAccountNumber')}
                id="app-user-my-suffix-bankAccountNumber"
                name="bankAccountNumber"
                data-cy="bankAccountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.appUser.bankName')}
                id="app-user-my-suffix-bankName"
                name="bankName"
                data-cy="bankName"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.appUser.walletAddress')}
                id="app-user-my-suffix-walletAddress"
                name="walletAddress"
                data-cy="walletAddress"
                type="text"
              />
              <ValidatedField
                id="app-user-my-suffix-tutor"
                name="tutor"
                data-cy="tutor"
                label={translate('projectApp.appUser.tutor')}
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
              <ValidatedField
                id="app-user-my-suffix-userVerify"
                name="userVerify"
                data-cy="userVerify"
                label={translate('projectApp.appUser.userVerify')}
                type="select"
              >
                <option value="" key="0" />
                {userVerifies
                  ? userVerifies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="app-user-my-suffix-user"
                name="user"
                data-cy="user"
                label={translate('projectApp.appUser.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user-my-suffix" replace color="info">
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

export default AppUserMySuffixUpdate;

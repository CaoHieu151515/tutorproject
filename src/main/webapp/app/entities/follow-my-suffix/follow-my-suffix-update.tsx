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
import { IFollowMySuffix } from 'app/shared/model/follow-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './follow-my-suffix.reducer';

export const FollowMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const tutors = useAppSelector(state => state.tutor.entities);
  const followEntity = useAppSelector(state => state.follow.entity);
  const loading = useAppSelector(state => state.follow.loading);
  const updating = useAppSelector(state => state.follow.updating);
  const updateSuccess = useAppSelector(state => state.follow.updateSuccess);

  const handleClose = () => {
    navigate('/follow-my-suffix');
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
      ...followEntity,
      ...values,
      followerAppUser: appUsers.find(it => it.id.toString() === values.followerAppUser?.toString()),
      followedTutor: tutors.find(it => it.id.toString() === values.followedTutor?.toString()),
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
          ...followEntity,
          followerAppUser: followEntity?.followerAppUser?.id,
          followedTutor: followEntity?.followedTutor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.follow.home.createOrEditLabel" data-cy="FollowCreateUpdateHeading">
            <Translate contentKey="projectApp.follow.home.createOrEditLabel">Create or edit a Follow</Translate>
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
                  id="follow-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.follow.createDate')}
                id="follow-my-suffix-createDate"
                name="createDate"
                data-cy="createDate"
                type="date"
              />
              <ValidatedField
                id="follow-my-suffix-followerAppUser"
                name="followerAppUser"
                data-cy="followerAppUser"
                label={translate('projectApp.follow.followerAppUser')}
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
                id="follow-my-suffix-followedTutor"
                name="followedTutor"
                data-cy="followedTutor"
                label={translate('projectApp.follow.followedTutor')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/follow-my-suffix" replace color="info">
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

export default FollowMySuffixUpdate;

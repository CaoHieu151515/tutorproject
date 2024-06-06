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
import { IUserVerifyMySuffix } from 'app/shared/model/user-verify-my-suffix.model';
import { getEntities as getUserVerifies } from 'app/entities/user-verify-my-suffix/user-verify-my-suffix.reducer';
import { IAcademicRankMySuffix } from 'app/shared/model/academic-rank-my-suffix.model';
import { Rank } from 'app/shared/model/enumerations/rank.model';
import { getEntity, updateEntity, createEntity, reset } from './academic-rank-my-suffix.reducer';

export const AcademicRankMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const userVerifies = useAppSelector(state => state.userVerify.entities);
  const academicRankEntity = useAppSelector(state => state.academicRank.entity);
  const loading = useAppSelector(state => state.academicRank.loading);
  const updating = useAppSelector(state => state.academicRank.updating);
  const updateSuccess = useAppSelector(state => state.academicRank.updateSuccess);
  const rankValues = Object.keys(Rank);

  const handleClose = () => {
    navigate('/academic-rank-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
    dispatch(getUserVerifies({}));
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
      ...academicRankEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media?.toString()),
      userVerify: userVerifies.find(it => it.id.toString() === values.userVerify?.toString()),
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
          type: 'BACHELOR',
          ...academicRankEntity,
          media: academicRankEntity?.media?.id,
          userVerify: academicRankEntity?.userVerify?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.academicRank.home.createOrEditLabel" data-cy="AcademicRankCreateUpdateHeading">
            <Translate contentKey="projectApp.academicRank.home.createOrEditLabel">Create or edit a AcademicRank</Translate>
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
                  id="academic-rank-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.academicRank.type')}
                id="academic-rank-my-suffix-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {rankValues.map(rank => (
                  <option value={rank} key={rank}>
                    {translate('projectApp.Rank.' + rank)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="academic-rank-my-suffix-media"
                name="media"
                data-cy="media"
                label={translate('projectApp.academicRank.media')}
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
                id="academic-rank-my-suffix-userVerify"
                name="userVerify"
                data-cy="userVerify"
                label={translate('projectApp.academicRank.userVerify')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/academic-rank-my-suffix" replace color="info">
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

export default AcademicRankMySuffixUpdate;

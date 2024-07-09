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
import { ITuTorContactWithMySuffix } from 'app/shared/model/tu-tor-contact-with-my-suffix.model';
import { Contact } from 'app/shared/model/enumerations/contact.model';
import { getEntity, updateEntity, createEntity, reset } from './tu-tor-contact-with-my-suffix.reducer';

export const TuTorContactWithMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tutorDetails = useAppSelector(state => state.tutorDetails.entities);
  const tuTorContactWithEntity = useAppSelector(state => state.tuTorContactWith.entity);
  const loading = useAppSelector(state => state.tuTorContactWith.loading);
  const updating = useAppSelector(state => state.tuTorContactWith.updating);
  const updateSuccess = useAppSelector(state => state.tuTorContactWith.updateSuccess);
  const contactValues = Object.keys(Contact);

  const handleClose = () => {
    navigate('/tu-tor-contact-with-my-suffix');
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
      ...tuTorContactWithEntity,
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
          type: 'MEET',
          ...tuTorContactWithEntity,
          tutorDetails: tuTorContactWithEntity?.tutorDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.tuTorContactWith.home.createOrEditLabel" data-cy="TuTorContactWithCreateUpdateHeading">
            <Translate contentKey="projectApp.tuTorContactWith.home.createOrEditLabel">Create or edit a TuTorContactWith</Translate>
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
                  id="tu-tor-contact-with-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.tuTorContactWith.urlContact')}
                id="tu-tor-contact-with-my-suffix-urlContact"
                name="urlContact"
                data-cy="urlContact"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.tuTorContactWith.type')}
                id="tu-tor-contact-with-my-suffix-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {contactValues.map(contact => (
                  <option value={contact} key={contact}>
                    {translate('projectApp.Contact.' + contact)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="tu-tor-contact-with-my-suffix-tutorDetails"
                name="tutorDetails"
                data-cy="tutorDetails"
                label={translate('projectApp.tuTorContactWith.tutorDetails')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/tu-tor-contact-with-my-suffix"
                replace
                color="info"
              >
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

export default TuTorContactWithMySuffixUpdate;

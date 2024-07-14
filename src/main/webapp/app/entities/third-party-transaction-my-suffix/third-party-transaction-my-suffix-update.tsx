import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';
import { getEntities as getWalletTransactions } from 'app/entities/wallet-transaction-my-suffix/wallet-transaction-my-suffix.reducer';
import { IThirdPartyTransactionMySuffix } from 'app/shared/model/third-party-transaction-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './third-party-transaction-my-suffix.reducer';

export const ThirdPartyTransactionMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const walletTransactions = useAppSelector(state => state.walletTransaction.entities);
  const thirdPartyTransactionEntity = useAppSelector(state => state.thirdPartyTransaction.entity);
  const loading = useAppSelector(state => state.thirdPartyTransaction.loading);
  const updating = useAppSelector(state => state.thirdPartyTransaction.updating);
  const updateSuccess = useAppSelector(state => state.thirdPartyTransaction.updateSuccess);

  const handleClose = () => {
    navigate('/third-party-transaction-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWalletTransactions({}));
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
      ...thirdPartyTransactionEntity,
      ...values,
      walletTransaction: walletTransactions.find(it => it.id.toString() === values.walletTransaction?.toString()),
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
          ...thirdPartyTransactionEntity,
          walletTransaction: thirdPartyTransactionEntity?.walletTransaction?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.thirdPartyTransaction.home.createOrEditLabel" data-cy="ThirdPartyTransactionCreateUpdateHeading">
            <Translate contentKey="projectApp.thirdPartyTransaction.home.createOrEditLabel">
              Create or edit a ThirdPartyTransaction
            </Translate>
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
                  id="third-party-transaction-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.thirdPartyTransaction.thirdPartyId')}
                id="third-party-transaction-my-suffix-thirdPartyId"
                name="thirdPartyId"
                data-cy="thirdPartyId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('projectApp.thirdPartyTransaction.transactionDate')}
                id="third-party-transaction-my-suffix-transactionDate"
                name="transactionDate"
                data-cy="transactionDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="third-party-transaction-my-suffix-walletTransaction"
                name="walletTransaction"
                data-cy="walletTransaction"
                label={translate('projectApp.thirdPartyTransaction.walletTransaction')}
                type="select"
              >
                <option value="" key="0" />
                {walletTransactions
                  ? walletTransactions.map(otherEntity => (
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
                to="/third-party-transaction-my-suffix"
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

export default ThirdPartyTransactionMySuffixUpdate;

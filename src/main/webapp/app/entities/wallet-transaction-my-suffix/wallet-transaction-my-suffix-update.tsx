import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWalletMySuffix } from 'app/shared/model/wallet-my-suffix.model';
import { getEntities as getWallets } from 'app/entities/wallet-my-suffix/wallet-my-suffix.reducer';
import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';
import { WalletTransactionType } from 'app/shared/model/enumerations/wallet-transaction-type.model';
import { WalletTransactionStatus } from 'app/shared/model/enumerations/wallet-transaction-status.model';
import { getEntity, updateEntity, createEntity, reset } from './wallet-transaction-my-suffix.reducer';

export const WalletTransactionMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wallets = useAppSelector(state => state.wallet.entities);
  const walletTransactionEntity = useAppSelector(state => state.walletTransaction.entity);
  const loading = useAppSelector(state => state.walletTransaction.loading);
  const updating = useAppSelector(state => state.walletTransaction.updating);
  const updateSuccess = useAppSelector(state => state.walletTransaction.updateSuccess);
  const walletTransactionTypeValues = Object.keys(WalletTransactionType);
  const walletTransactionStatusValues = Object.keys(WalletTransactionStatus);

  const handleClose = () => {
    navigate('/wallet-transaction-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWallets({}));
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
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }

    const entity = {
      ...walletTransactionEntity,
      ...values,
      wallet: wallets.find(it => it.id.toString() === values.wallet?.toString()),
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
          type: 'DEPOSIT',
          status: 'SUCCEED',
          ...walletTransactionEntity,
          wallet: walletTransactionEntity?.wallet?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="projectApp.walletTransaction.home.createOrEditLabel" data-cy="WalletTransactionCreateUpdateHeading">
            <Translate contentKey="projectApp.walletTransaction.home.createOrEditLabel">Create or edit a WalletTransaction</Translate>
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
                  id="wallet-transaction-my-suffix-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('projectApp.walletTransaction.amount')}
                id="wallet-transaction-my-suffix-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('projectApp.walletTransaction.type')}
                id="wallet-transaction-my-suffix-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {walletTransactionTypeValues.map(walletTransactionType => (
                  <option value={walletTransactionType} key={walletTransactionType}>
                    {translate('projectApp.WalletTransactionType.' + walletTransactionType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.walletTransaction.status')}
                id="wallet-transaction-my-suffix-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {walletTransactionStatusValues.map(walletTransactionStatus => (
                  <option value={walletTransactionStatus} key={walletTransactionStatus}>
                    {translate('projectApp.WalletTransactionStatus.' + walletTransactionStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('projectApp.walletTransaction.createAt')}
                id="wallet-transaction-my-suffix-createAt"
                name="createAt"
                data-cy="createAt"
                type="date"
              />
              <ValidatedField
                id="wallet-transaction-my-suffix-wallet"
                name="wallet"
                data-cy="wallet"
                label={translate('projectApp.walletTransaction.wallet')}
                type="select"
              >
                <option value="" key="0" />
                {wallets
                  ? wallets.map(otherEntity => (
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
                to="/wallet-transaction-my-suffix"
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

export default WalletTransactionMySuffixUpdate;

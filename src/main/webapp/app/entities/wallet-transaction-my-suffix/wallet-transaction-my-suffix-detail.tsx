import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wallet-transaction-my-suffix.reducer';

export const WalletTransactionMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const walletTransactionEntity = useAppSelector(state => state.walletTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="walletTransactionDetailsHeading">
          <Translate contentKey="projectApp.walletTransaction.detail.title">WalletTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{walletTransactionEntity.id}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="projectApp.walletTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{walletTransactionEntity.amount}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="projectApp.walletTransaction.type">Type</Translate>
            </span>
          </dt>
          <dd>{walletTransactionEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="projectApp.walletTransaction.status">Status</Translate>
            </span>
          </dt>
          <dd>{walletTransactionEntity.status}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="projectApp.walletTransaction.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>
            {walletTransactionEntity.createAt ? (
              <TextFormat value={walletTransactionEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="projectApp.walletTransaction.hireTutor">Hire Tutor</Translate>
          </dt>
          <dd>{walletTransactionEntity.hireTutor ? walletTransactionEntity.hireTutor.id : ''}</dd>
          <dt>
            <Translate contentKey="projectApp.walletTransaction.wallet">Wallet</Translate>
          </dt>
          <dd>{walletTransactionEntity.wallet ? walletTransactionEntity.wallet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/wallet-transaction-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallet-transaction-my-suffix/${walletTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WalletTransactionMySuffixDetail;

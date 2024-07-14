import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './third-party-transaction-my-suffix.reducer';

export const ThirdPartyTransactionMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thirdPartyTransactionEntity = useAppSelector(state => state.thirdPartyTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thirdPartyTransactionDetailsHeading">
          <Translate contentKey="projectApp.thirdPartyTransaction.detail.title">ThirdPartyTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{thirdPartyTransactionEntity.id}</dd>
          <dt>
            <span id="thirdPartyId">
              <Translate contentKey="projectApp.thirdPartyTransaction.thirdPartyId">Third Party Id</Translate>
            </span>
          </dt>
          <dd>{thirdPartyTransactionEntity.thirdPartyId}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="projectApp.thirdPartyTransaction.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>
            {thirdPartyTransactionEntity.transactionDate ? (
              <TextFormat value={thirdPartyTransactionEntity.transactionDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="projectApp.thirdPartyTransaction.walletTransaction">Wallet Transaction</Translate>
          </dt>
          <dd>{thirdPartyTransactionEntity.walletTransaction ? thirdPartyTransactionEntity.walletTransaction.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/third-party-transaction-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/third-party-transaction-my-suffix/${thirdPartyTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThirdPartyTransactionMySuffixDetail;

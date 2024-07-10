import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './wallet-transaction-my-suffix.reducer';

export const WalletTransactionMySuffix = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const walletTransactionList = useAppSelector(state => state.walletTransaction.entities);
  const loading = useAppSelector(state => state.walletTransaction.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="wallet-transaction-my-suffix-heading" data-cy="WalletTransactionHeading">
        <Translate contentKey="projectApp.walletTransaction.home.title">Wallet Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="projectApp.walletTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/wallet-transaction-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="projectApp.walletTransaction.home.createLabel">Create new Wallet Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {walletTransactionList && walletTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="projectApp.walletTransaction.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="projectApp.walletTransaction.amount">Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="projectApp.walletTransaction.type">Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="projectApp.walletTransaction.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('createAt')}>
                  <Translate contentKey="projectApp.walletTransaction.createAt">Create At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createAt')} />
                </th>
                <th>
                  <Translate contentKey="projectApp.walletTransaction.hireTutor">Hire Tutor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="projectApp.walletTransaction.wallet">Wallet</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {walletTransactionList.map((walletTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wallet-transaction-my-suffix/${walletTransaction.id}`} color="link" size="sm">
                      {walletTransaction.id}
                    </Button>
                  </td>
                  <td>{walletTransaction.amount}</td>
                  <td>
                    <Translate contentKey={`projectApp.WalletTransactionType.${walletTransaction.type}`} />
                  </td>
                  <td>
                    <Translate contentKey={`projectApp.WalletTransactionStatus.${walletTransaction.status}`} />
                  </td>
                  <td>
                    {walletTransaction.createAt ? (
                      <TextFormat type="date" value={walletTransaction.createAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {walletTransaction.hireTutor ? (
                      <Link to={`/hire-tutor-my-suffix/${walletTransaction.hireTutor.id}`}>{walletTransaction.hireTutor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {walletTransaction.wallet ? (
                      <Link to={`/wallet-my-suffix/${walletTransaction.wallet.id}`}>{walletTransaction.wallet.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/wallet-transaction-my-suffix/${walletTransaction.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/wallet-transaction-my-suffix/${walletTransaction.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/wallet-transaction-my-suffix/${walletTransaction.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="projectApp.walletTransaction.home.notFound">No Wallet Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WalletTransactionMySuffix;
